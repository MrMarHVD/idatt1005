package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.global.PopupManager;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.controller.inventory.IngredientListCell;

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
   * Text field for the quantity of the new ingredient to be added
   */
  @FXML
  private TextField quantityFieldAdd;

  /**
   * TextField for inserting the quantity to add to the selected ingredient.
   */
  @FXML
  private TextField quantityFieldUpdate;

  /**
   * Button for adding a new ingredient to the inventory.
   */
  @FXML
  private Button addIngredientButton;

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

    // Set handler for the update button
    updateIngredientButton.setOnMouseClicked(event -> {
      Integer selectedIngredientId = ingredientListView.getSelectionModel().getSelectedItem();
      if (selectedIngredientId != null) {
        try {
          ResultSet rs = MainController.sqlConnector.executeSqlSelect(
              "SELECT name FROM ingredient WHERE ingredient_id = " + selectedIngredientId
          );
          if (rs.next()) {
            String ingredientName = rs.getString("name");

            // Update the ingredient selected with the new quantity.
            this.updateIngredient(ingredientName, Float.parseFloat(quantityFieldUpdate.getText()));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    removeSelectedButton.setOnMouseClicked(event -> removeSelectedIngredient());
    addIngredientButton.setOnMouseClicked(event -> addNewIngredient(addIngredientNameField.getText(),
        Float.parseFloat(quantityFieldAdd.getText())));
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
      ResultSet inventoryIngredients = MainController.sqlConnector.executeSqlSelect(
          "SELECT i.ingredient_id "
              + "FROM ingredient i "
              + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id;");
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
      ResultSet searchResults = MainController.sqlConnector.executeSqlSelect(""
          + "SELECT i.ingredient_id "
          + "FROM ingredient i "
          + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
          + "WHERE i.name LIKE '%" + input + "%';");
      while (searchResults.next()) {
        fullInventory.add(searchResults.getInt("ingredient_id"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    ObservableList<Integer> observableIngredients = FXCollections.observableArrayList(fullInventory);
    ingredientListView.setItems(observableIngredients);
    ingredientListView.setCellFactory(param -> new IngredientListCell());
  }

  /**
   * Method to sort ingredients by their name in either ascending or descending order.
   */
  private void sortByName() {
    List<Integer> fullInventory = new ArrayList<Integer>();

    try {
      ResultSet inventoryIngredients = MainController.sqlConnector.executeSqlSelect(""
          + "SELECT i.ingredient_id "
          + "FROM ingredient i "
          + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
          + "ORDER BY i.name DESC;");
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
      ResultSet inventoryIngredients = MainController.sqlConnector.executeSqlSelect(""
          + "SELECT i.ingredient_id "
          + "FROM ingredient i "
          + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
          + "INNER JOIN category c ON i.category_id = c.id "
          + "ORDER BY c.name DESC;");
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
      MainController.sqlConnector.executeSqlUpdate(""
          + "DELETE FROM inventory_ingredient "
          + "WHERE ingredient_id = " + ingredientId + ";");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is called when an existing ingredient is to be updated.
   *
   * @param name name of the ingredient
   * @param quantity quantity entered by the user
   */
  @FXML
  private void updateIngredient(String name, float quantity) {
    try {
      // Get the ingredient_id of the ingredient with the given name
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM ingredient WHERE name = '" + name + "'"
      );

      if (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");

        // Check if the ingredient exists in the inventory_ingredient table
        ResultSet rsInventory = MainController.sqlConnector.executeSqlSelect(
            "SELECT ingredient_id FROM inventory_ingredient WHERE ingredient_id = " + ingredientId
        );

        if (rsInventory.next()) {
          // If the ingredient exists, update its quantity
          MainController.sqlConnector.executeSqlUpdate(
              "UPDATE inventory_ingredient SET quantity = quantity + " + quantity + " WHERE ingredient_id = " + ingredientId
          );
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void addNewIngredient(String name, float quantity) {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT i.ingredient_id "
              + "FROM ingredient i "
              + "WHERE i.name = '" + name + "'");



      if (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");

        // Check if there already exists a corresponding ingredient in inventory
        ResultSet rsInventory = MainController.sqlConnector.executeSqlSelect(
            "SELECT ii.ingredient_id "
                + "FROM inventory_ingredient ii "
                + "WHERE ii.ingredient_id = " + ingredientId
        );

        // If a corresponding ingredient exists, throw exception.
        if (rsInventory.next()) {throw new IllegalArgumentException(""
            + "Cannot add a new ingredient if it already exists in inventory. Please "
            + "update existing ingredient instead."); }

        MainController.sqlConnector.executeSqlUpdate(
            "INSERT INTO inventory_ingredient (ingredient_id, quantity) "
                + "VALUES (" + ingredientId + ", " + quantity + "); "
        );
      } else {
        System.out.println("Test");
      }

  } catch (Exception e) {
      PopupManager.displayError("Error", "Failed to add ingredient", e.getMessage());
    e.printStackTrace();
  }
  }
}
