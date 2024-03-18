package no.ntnu.idatt1005.plate.controller.shoppinglist;

import static java.lang.Integer.parseInt;

import java.sql.ResultSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;


/**
 * Controller class for the shopping list view.
 */
public class UiShoppingListController {

  @FXML
  private ListView<String> listView;

  @FXML
  private TextField itemNameField;

  @FXML
  private TextField itemAmountField;

  private ObservableList<String> items;

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

  /**
   * Initialize the controller.
   */
  @FXML
  public void initialize() {
    if (mainController != null) {
      this.setMainController(mainController);
    }
    items = FXCollections.observableArrayList();

    updateItems();
    if (listView != null) {
      listView.setItems(items);
      listView.setStyle("-fx-font-family: 'Consolas';");
    }

    buyItemsButton.setStyle("-fx-background-color: #cedece;");
    clearListButton.setStyle("-fx-background-color: #decece;");

  }

  /**
   * makes a string title case.
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
   * add items to the shopping list, or update the quantity if the item already exists.
   */
  public void addItems() {
    try {

      String itemName = titleCase(itemNameField.getText());
      String itemAmount = itemAmountField.getText();
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM ingredient WHERE name = '" + itemName + "'");
      int ingredientId = rs.getInt("ingredient_id");

      if (ingredientId == 0) {
        details_label.setText("No ingredient named " + itemName + "     ");
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
      items.clear();

      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT name, quantity, unit FROM shopping_list_items JOIN ingredient ON shopping_list_items.ingredient_id = ingredient.ingredient_id");
      while (rs.next()) {
        if (Float.parseFloat(rs.getString("quantity")) <= 0) {
          MainController.sqlConnector.executeSqlUpdate(
              "DELETE FROM shopping_list_items WHERE quantity <= 0");
          continue;
        }
        String name = rs.getString("name");
        String quantity = rs.getString("quantity");
        String unit = rs.getString("unit");
        String item = String.format("%-30s %-5s %s", name, quantity, unit);
        items.add(item);
        listView.setItems(items);
      }
    } catch (Exception e) {
      e.getMessage();
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
   * Add items from shopping list to inventory
   */
  @FXML
  private void buyItems() {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity FROM shopping_list_items");
      while (rs.next()) {
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
      }
      MainController.sqlConnector.executeSqlUpdate("DELETE FROM shopping_list_items");
      updateItems();
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Clear the shopping list.
   */
  @FXML
  private void clearList() {
    try {
      MainController.sqlConnector.executeSqlUpdate("DELETE FROM shopping_list_items");
      updateItems();
    } catch (Exception e) {
      e.getMessage();
    }

  }

}
