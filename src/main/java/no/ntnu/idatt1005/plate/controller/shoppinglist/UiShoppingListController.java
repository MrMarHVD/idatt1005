package no.ntnu.idatt1005.plate.controller.shoppinglist;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import no.ntnu.idatt1005.plate.controller.global.MainController;

import no.ntnu.idatt1005.plate.controller.global.MainController;

import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;

/**
 * Controller class for the shopping list view.
 */
public class UiShoppingListController {

  @FXML
  private ListView<HBox> listView;

  private final List<Integer> selectedItems = new ArrayList<>();


  @FXML
  private TextField itemNameField;

  @FXML
  private TextField itemAmountField;

  @FXML
  private Button clearListButton;
  @FXML
  private Button buyItemsButton;

  @FXML
  private MainController mainController;

  @FXML
  private ToolbarController toolbarController;


  @FXML
  private Label details_label;

  private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

  /**
   * Initialize the controller.
   */
  @FXML
  public void initialize() {
    if (mainController != null) {
      this.setMainController(mainController);
    }
    updateItems();
    if (listView != null) {
      listView.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 13;");
    }

    details_label.setTextFill(Color.web("#bb5555"));
    details_label.setStyle("-fx-font-size: 16;");

    buyItemsButton.setStyle("-fx-background-color: #cedece;");
    clearListButton.setStyle("-fx-background-color: #decece;");

  }

  /**
   * makes a string title case. Used for comparing strings in the database.
   *
   * @param str the string to be title cased.
   * @return the title cased string.
   */
  private String titleCase(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }

  /**
   * add selected items to the shopping list, or update the quantity if the item already exists.
   */
  public void addItems() {
    try {

      String itemName = titleCase(itemNameField.getText());
      String itemAmount = itemAmountField.getText();
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM ingredient WHERE name = '" + itemName + "'");
      int ingredientId = rs.getInt("ingredient_id");

      if (ingredientId == 0) {
        if (itemName.isEmpty()) {
          details_label.setText("No item name entered");
          return;
        } else if (itemName.length() > 16) {
          itemName = itemName.substring(0, 16) + "...";
        }
        details_label.setText("No ingredient named:\n " + itemName + "     ");
        return;
      }
      details_label.setText("");
      ResultSet rs2 = MainController.sqlConnector.executeSqlSelect(
          "SELECT * FROM shopping_list_items WHERE ingredient_id = " + ingredientId);
      if (rs2.next()) {
        MainController.sqlConnector.executeSqlUpdate(
            "UPDATE shopping_list_items SET quantity = quantity + " + Float.parseFloat(itemAmount)
                + " WHERE ingredient_id = " + ingredientId);
        System.out.println("Updated " + itemName + " " + itemAmount);
        updateItems();
        return;
      }

      MainController.sqlConnector.executeSqlUpdate(
          "INSERT INTO shopping_list_items (ingredient_id, quantity) VALUES (" + ingredientId + ", "
              + Float.parseFloat(itemAmount) + ")");
      System.out.println("Added " + itemName + " " + itemAmount);
      updateItems();

    } catch (Exception e) {
      e.getMessage();
    }

  }

  /**
   * Update items in the shopping list.
   */
  public void updateItems() {
    try {
      listView.getItems().clear();
      checkBoxes.clear();
      boolean nextRowGray = false;

      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient.ingredient_id, name, quantity, unit FROM shopping_list_items JOIN ingredient ON shopping_list_items.ingredient_id = ingredient.ingredient_id");
      while (rs.next()) {
        if (Float.parseFloat(rs.getString("quantity")) <= 0) {
          MainController.sqlConnector.executeSqlUpdate(
              "DELETE FROM shopping_list_items WHERE quantity <= 0");
          continue;
        }
        int ingredientId = rs.getInt("ingredient_id");
        String name = rs.getString("name");
        String quantity = rs.getString("quantity");
        String unit = rs.getString("unit");
        String item = String.format("%-30s %-5s %-15s", name, quantity, unit);

        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(e -> {
          if (checkBox.isSelected()) {
            selectedItems.add(ingredientId);
            System.out.println(selectedItems);
          } else if (selectedItems.contains(ingredientId)) {
            selectedItems.remove(Integer.valueOf(ingredientId));
            System.out.println(selectedItems);
          }
        });
        checkBoxes.add(checkBox);

        Label label = new Label(item);
        HBox hBox = new HBox();
        if (nextRowGray) {
          hBox.setStyle("-fx-background-color: #e0e0e033;");
        } else {
          hBox.setStyle("-fx-background-color: #ffffff00;");
        }
        nextRowGray = !nextRowGray;
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().add(label);
        hBox.getChildren().add(checkBox);

        listView.getItems().add(hBox);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  @FXML
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null) {
      toolbarController.setMainController(mainController);
    }
  }

  /**
   * Add selected items from shopping list to inventory, and remove them from the shopping list.
   */
  @FXML
  private void buyItems() {
    try {
      if (selectedItems.isEmpty()) {
        details_label.setText("No items selected");
        return;
      }

      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity FROM shopping_list_items WHERE ingredient_id IN ("
              + selectedItems.toString().substring(1, selectedItems.toString().length() - 1)
              + ")");
      while (rs.next()) {
        System.out.println(rs.getInt("ingredient_id"));
        int ingredientId = rs.getInt("ingredient_id");
        float qty = rs.getFloat("quantity");

        ResultSet rs2 = MainController.sqlConnector.executeSqlSelect(
            "SELECT * FROM inventory_ingredient WHERE ingredient_id = " + ingredientId);
        if (rs2.next()) {
          MainController.sqlConnector.executeSqlUpdate(
              "UPDATE inventory_ingredient SET quantity = quantity + " + qty
                  + " WHERE ingredient_id = "
                  + ingredientId);
        } else {
          MainController.sqlConnector.executeSqlUpdate(
              "INSERT INTO inventory_ingredient(ingredient_id, quantity ) VALUES(" + ingredientId
                  + ", "
                  + qty + ")");
        }
        MainController.sqlConnector.executeSqlUpdate(
            "DELETE FROM shopping_list_items WHERE ingredient_id = " + ingredientId);
      }
      updateItems();
      selectedItems.clear();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Removes selected items from the shopping list.
   */
  @FXML
  private void clearList() {
    try {
      if (selectedItems.isEmpty()) {
        details_label.setText("No items selected");
        return;
      }
      MainController.sqlConnector.executeSqlUpdate(
          "DELETE FROM shopping_list_items WHERE ingredient_id IN ("
              + selectedItems.toString().substring(1, selectedItems.toString().length() - 1)
              + ")");
      updateItems();
      selectedItems.clear();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  /**
   * Select all items in the shopping list.
   */
  public void selectAllItems() {
    for (CheckBox checkBox : checkBoxes) {
      checkBox.setSelected(true);
    }
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM shopping_list_items");
      while (rs.next()) {
        selectedItems.add(rs.getInt("ingredient_id"));
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Unselect all items in the shopping list.
   */
  public void unselectAllItems() {
    for (CheckBox checkBox : checkBoxes) {
      checkBox.setSelected(false);
    }
    selectedItems.clear();
  }

}
