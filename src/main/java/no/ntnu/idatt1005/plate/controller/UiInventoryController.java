package no.ntnu.idatt1005.plate.controller;

import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Ingredient;
import no.ntnu.idatt1005.plate.model.json.JsonReader;
import no.ntnu.idatt1005.plate.model.IngredientListCell;

/**
 * Controller class for the inventory view
 */
public class UiInventoryController {

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
   * The list view for displaying ingredients in the inventory.
   */
  @FXML
  private ListView<Ingredient> ingredientListView;

  /**
   * Text field or inputting what to search for.
   */
  @FXML
  private TextField searchField;

  /**
   * The search button for performing search in inventory.
   */
  @FXML
  private Button searchButton;

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

  private boolean sortNameAsc = true;
  private boolean sortAllergensAsc = true;
  private boolean sortCategoryAsc = true;


  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);

    if (ingredientListView != null) {
      this.displayIngredients();
    } else {
      System.out.println("ingredientListView is null");
    }

    if (searchButton != null) {
      searchButton.setOnAction(event -> searchIngredients(searchField.getText()));
    } else {
      System.out.println("searchButton is null");
    }

    if (nameLabel != null && allergensLabel != null && categoryLabel != null) {
      this.initializeSortLabelHandlers();
    } else {
      System.out.println("One or more of the sort labels are null");
    }

  }

  /**
   * Define the event handlers for when the sort labels are clicked.
   */
  private void initializeSortLabelHandlers() {
    nameLabel.setOnMouseClicked(event -> sortByName());
    categoryLabel.setOnMouseClicked(event -> sortByCategory());
  }

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
   * Display all ingredients in the inventory.
   */
  private void displayIngredients() {
    List<Ingredient> allIngredients = JsonReader.getInventoryIngredients();
    ObservableList<Ingredient> observableIngredients = FXCollections.observableArrayList(allIngredients);
    ObservableList<String> ingredientNames = FXCollections.observableArrayList();
    ingredientListView.setItems(observableIngredients);
    ingredientListView.setCellFactory(param -> new IngredientListCell());
  }

  /**
   * Implement search in order to update display of ingredients.
   *
   * @param input into the search prompt.
   */
  private void searchIngredients(String input) {
    List<Ingredient> allIngredients = JsonReader.getInventoryIngredients();
    ObservableList<Ingredient> observableIngredients = FXCollections.observableArrayList();

    for (Ingredient ingredient : allIngredients) {
      if (ingredient.getName().contains(input)) {
        observableIngredients.add(ingredient);
      }
    }
    ingredientListView.setItems(observableIngredients);
    ingredientListView.setCellFactory(param -> new IngredientListCell());
  }

  /**
   * Method to sort ingredients by their name in either ascending or descending order.
   */
  private void sortByName() {
    if (sortNameAsc) {
      ingredientListView.getItems().sort(Comparator.comparing(Ingredient::getName));
    } else {
      ingredientListView.getItems().sort(Comparator.comparing(Ingredient::getName).reversed());
    }
    sortNameAsc = !sortNameAsc;
  }


  /**
   * Method to sort ingredients by their category in either ascending or descending order.
   */
  private void sortByCategory() {
    if (sortCategoryAsc) {
      ingredientListView.getItems().sort(Comparator.comparing(Ingredient::getCategory));
    } else {
      ingredientListView.getItems().sort(Comparator.comparing(Ingredient::getCategory).reversed());
    }
    sortCategoryAsc = !sortCategoryAsc;
  }

}
