package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.NumberStringConverter;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.global.PopupManager;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.controller.inventory.IngredientListCell;
import no.ntnu.idatt1005.plate.model.Inventory;

/**
 * Controller class for the inventory view
 */
public class UiInventoryController {

  /**
   * The list view for displaying ingredients in the inventory.
   */
  @FXML
  private ListView<Integer> ingredientListView;

  /**
   * The main controller for the application.
   */
  @FXML
  private MainController mainController;

  /**
   * The toolbar controller for this view.
   */
  @FXML
  private ToolbarController toolbarController;


  /**
   * Text field or inputting what to search for.
   */
  @FXML
  private TextField searchField;

  /**
   * Label for the name of the ingredient.
   */
  @FXML
  private Label nameLabel;

  /**
   * Label for the allergens of the ingredient.
   */
  @FXML
  private Label allergensLabel;

  /**
   * Label for the category of the ingredient.
   */
  @FXML
  private Label categoryLabel;

  /**
   * Text field for adding a new ingredient.
   */
  @FXML
  private TextField addIngredientNameField;


  /**
   * TextField for inserting the quantity to add to the selected ingredient.
   */
  @FXML
  private TextField quantityFieldUpdate;

  /**
   * Button for executing update of selected ingredient
   */
  @FXML
  private Button updateIngredientButton;

  /**
   * Button for removing the selected ingredient from the inventory.
   */
  @FXML
  private Button removeSelectedButton;

  /**
   * Boolean for determining whether to sort by ingredient name in ascending or descending order.
   */
  private boolean sortNameAsc = true;

  /**
   * Boolean for determining whether to sort by ingredient category in ascending or descending
   * order.
   */
  private boolean sortCategoryAsc = true;


  /**
   * Initialize the controller.
   */
  @FXML
  public void initialize() {
    this.setMainController(mainController);
    ingredientListView.setCellFactory(param -> new IngredientListCell());
    this.displayIngredients();


    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
      searchIngredients(newValue);
    });

    this.initializeSortLabelHandlers();

    this.initializeButtonHandlers();


  }

  /**
   * Define the event handlers for when the sort labels are clicked.
   */
  private void initializeSortLabelHandlers() {
    nameLabel.setOnMouseClicked(event -> sortByName());
    categoryLabel.setOnMouseClicked(event -> sortByCategory());
  }

  /**
   * Define the event handlers for when the add and remove buttons are clicked.
   */
  private void initializeButtonHandlers() {

    // Define a unary operator to prevent the user from inputting invalid characters.
    UnaryOperator<Change> floatFilter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty() || newText.matches("\\d*\\.?\\d*")) {
        return change;
      }
      return null;
    };
    quantityFieldUpdate.setTextFormatter(new TextFormatter<>(floatFilter));

    // Set handler for the update button
    updateIngredientButton.setOnMouseClicked(event -> {
      //Integer selectedIngredientId = ingredientListView.getSelectionModel().getSelectedItem();
      float quantity = Float.parseFloat(quantityFieldUpdate.getText());
      //if (quantity == (float) quantity)
      this.updateIngredient(quantity);
    });
    // Set handler to remove the selected button.
    removeSelectedButton.setOnMouseClicked(event -> removeSelectedIngredient());

  }

  /**
   * Initialize the listener which monitors which fields in the listview are selected.
   */


  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null)
      toolbarController.setMainController(mainController);

  }

  /**
   * Get the main controller for this class.
   *
   * @return the main controller.
   */
  public MainController getMainController() {
    return this.mainController;
  }

  /**
   * Queries all ingredients in the inventory and displays them.
   */
  private void displayIngredients() {
    //ist<Ingredient> allIngredients = JsonReader.getInventoryIngredients();
    //ObservableList<Ingredient> observableIngredients = FXCollections.observableArrayList(allIngredients);
    //ingredientListView.setItems(observableIngredients);
    //ingredientListView.setCellFactory(param -> new IngredientListCell());
    List<Integer> fullInventory = new ArrayList<Integer>();

    try {
      ResultSet inventoryIngredients = Inventory.selectAllInventoryIngredients();
      while (inventoryIngredients.next()) {
        fullInventory.add(inventoryIngredients.getInt("ingredient_id"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    ObservableList<Integer> observableIngredients = FXCollections.observableArrayList(fullInventory);
    ingredientListView.setItems(observableIngredients);
    ingredientListView.setCellFactory(param -> new IngredientListCell());
  }

  /**
   * Implement search in order to update display of ingredients.
   *
   * @param input into the search prompt.
   */
  private void searchIngredients(String input) {
    List<Integer> fullInventory = new ArrayList<Integer>();
    try {
      ResultSet searchResults = Inventory.searchIngredients(input);
      while (searchResults.next()) {
        fullInventory.add(searchResults.getInt("ingredient_id"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    ObservableList<Integer> observableIngredients = FXCollections.observableArrayList(fullInventory);
    ingredientListView.setItems(observableIngredients);
  }

  /**
   * Method to sort ingredients by their name in either ascending or descending order.
   */
  private void sortByName() {
    List<Integer> fullInventory = new ArrayList<Integer>();

    try {
      ResultSet inventoryIngredients = Inventory.sortByName();
      while (inventoryIngredients.next()) {
        fullInventory.add(inventoryIngredients.getInt("ingredient_id"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    ObservableList<Integer> observableIngredients = FXCollections.observableArrayList(fullInventory);
    ingredientListView.setItems(observableIngredients);
    ingredientListView.setCellFactory(param -> new IngredientListCell());
  }


  /**
   * Method to sort ingredients by their category in either ascending or descending order.
   */
  private void sortByCategory() {
    List<Integer> fullInventory = new ArrayList<Integer>();

    try {
      ResultSet inventoryIngredients = Inventory.sortByCategory();
      while (inventoryIngredients.next()) {
        fullInventory.add(inventoryIngredients.getInt("ingredient_id"));
      }
    } catch (Exception e) {
      PopupManager.displayErrorFull("Error", "Failed to sort", e.getMessage());
      e.printStackTrace();
    }
    ObservableList<Integer> observableIngredients = FXCollections.observableArrayList(fullInventory);
    ingredientListView.setItems(observableIngredients);
    ingredientListView.setCellFactory(param -> new IngredientListCell());

  }

  /**
   * Respond to the delete button being pressed.
   */
  @FXML
  private void removeSelectedIngredient() {
    if (ingredientListView == null) {
      System.out.println("ingredientListView is null");
    } else {
        Integer selectedIngredientId = ingredientListView.getSelectionModel().getSelectedItem();
        this.deleteIngredient(selectedIngredientId);
        this.displayIngredients();
    }
  }

  /**
   * Delete an ingredient from the inventory.
   *
   * @param ingredientId the ID of the ingredient to be deleted.
   */
  private void deleteIngredient(int ingredientId) {
    try {
      Inventory.deleteIngredient(ingredientId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is called when the quantity of an existing ingredient is to be updated.
   *
   * @param quantity quantity entered by the user
   */
  @FXML
  private void updateIngredient(float quantity) {
    // Check if there has been an input into the ingredient name field.
    String ingredientName = this.addIngredientNameField.getText();

    // If the ingredient does not exist in the inventory, but is in the database, add it.
    if (!Inventory.ingredientExistsInInventory(ingredientName)
        && Inventory.ingredientExists(ingredientName)) {
      Inventory.addNewIngredient(ingredientName, quantity);

      // if the ingredient exists in the inventory, update its quantity.
    } else if(Inventory.ingredientExistsInInventory(ingredientName) ) {
      //PopupManager.displayError("Error", "No ingredient selected");
      Inventory.updateIngredient(ingredientName, quantity);

      // Else, an ingredient is selected via the list view, update that one.
    } else if(ingredientListView.getSelectionModel().getSelectedItem() != null) {
      int ingredientId = ingredientListView.getSelectionModel().getSelectedItem();
      ingredientName = Inventory.selectIngredient(ingredientId);
      System.out.println(ingredientName);
      Inventory.updateIngredient(ingredientName, quantity);

      // If no valid ingredient is in the field, and no ingredient is selected, display error.
    } else {
      PopupManager.displayError("Error", "No valid ingredient is selected.");
    }

    this.displayIngredients();
  }

}
