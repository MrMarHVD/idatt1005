package no.ntnu.idatt1005.plate.controller.ui.mainviews;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Inventory;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.model.ShoppingList;

/**
 * Controller class for the shopping list view.
 */
public class UiShoppingListController {

  /**
   * The ListView object for the shopping list.
   */
  @FXML
  private ListView<HBox> listView;

  /**
   * The list of selected items.
   */
  private final List<Integer> selectedItems = new ArrayList<>();

  /**
   * The text field for the item name.
   */
  @FXML
  private TextField itemNameField;

  /**
   * The text field for the item amount.
   */
  @FXML
  private TextField itemAmountField;

  /**
   * The buttons for the shopping list.
   */
  @FXML
  private Button clearListButton;
  @FXML
  private Button buyItemsButton;

  /**
   * The main controller for this class.
   */
  @FXML
  private MainController mainController;

  /**
   * The toolbar controller for this class.
   */
  @FXML
  private ToolbarController toolbarController;

  /**
   * The combobox for selecting ingredients.
   */
  @FXML
  private ComboBox<String> selectIngredientComboBox;

  /**
   * The label for details.
   */
  @FXML
  private Label detailsLabel;

  /**
   * The list of checkboxes in the shopping list.
   */
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

    detailsLabel.setTextFill(Color.web("#bb5555"));
    detailsLabel.setStyle("-fx-font-size: 16;");

    ArrayList<String> ingredients = Inventory.searchIngredients("");
    for (int i = 0; i < ingredients.size(); i++) {
      this.selectIngredientComboBox.getItems().add(ingredients.get(i));
    }


    this.itemNameField.textProperty().addListener((observable, oldValue, newValue) -> {
      this.selectIngredientComboBox.getItems().clear();
      ArrayList<String> results = Inventory.searchIngredients(newValue);
      for (int i = 0; i < results.size(); i++) {
        this.selectIngredientComboBox.getItems().add(results.get(i));
      }
    });

  }

  /**
   * Add selected items to the shopping list, or update the quantity if the item already exists.
   */
  public void addSelectedItems() {
    try {

      String itemName = selectIngredientComboBox.getSelectionModel().getSelectedItem();
      String itemAmount = itemAmountField.getText();
      if (itemName == null || itemName.isEmpty() || itemAmount == null || itemAmount.isEmpty()) {
        PopupManager.displayErrorFull("Error", "Invalid input",
            "Please enter a valid item name and amount.");
        detailsLabel.setText("Invalid input");
        return;
      }
      ResultSet rs = Inventory.selectIngredientIdFromName(itemName);
      int ingredientId = rs.getInt("ingredient_id");

      detailsLabel.setText("");
      ResultSet rs2 = ShoppingList.selectShoppingListItemFromId(ingredientId);
      if (rs2.next()) {
        ShoppingList.updateShoppingListQuantity(ingredientId, Float.parseFloat(itemAmount));
        updateItems();
        return;
      }

      ShoppingList.insertIntoShoppingList(ingredientId, Float.parseFloat(itemAmount));
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

      ResultSet rs = ShoppingList.selectAllMatchingIds();
      while (rs.next()) {
        if (Float.parseFloat(rs.getString("quantity")) <= 0) {
          ShoppingList.deleteLessThanZero();
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
          } else if (selectedItems.contains(ingredientId)) {
            selectedItems.remove(Integer.valueOf(ingredientId));
          }
        });
        checkBoxes.add(checkBox);

        Label label = new Label(item);
        HBox hbox = new HBox();
        if (nextRowGray) {
          hbox.setStyle("-fx-background-color: #e0e0e033;");
        } else {
          hbox.setStyle("-fx-background-color: #ffffff00;");
        }
        nextRowGray = !nextRowGray;
        hbox.setAlignment(Pos.CENTER);

        hbox.getChildren().add(label);
        hbox.getChildren().add(checkBox);

        listView.getItems().add(hbox);
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
        PopupManager.displayErrorFull("Error", "No items selected",
            "Please select items to add to inventory.");
        detailsLabel.setText("No items selected");
        return;
      }

      ShoppingList.buyItems(selectedItems);
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
        PopupManager.displayErrorFull("Error", "No items selected",
            "Please select items to remove.");
        detailsLabel.setText("No items selected");
        return;
      }
      ShoppingList.deleteItems(selectedItems);
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
      ResultSet rs = ShoppingList.selectAllItems();
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
