package no.ntnu.idatt1005.plate.controller.ui.mainviews;

import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.ui.cookbook.RecipeListCell;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.utility.Formatter;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.controller.ui.toolbar.ToolbarController;
import javafx.fxml.FXML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1005.plate.model.Recipe;

/**
 * Controller class for the recipe view
 */
public class UiRecipeViewController {

  private String recipeName;

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
   * The instructions for the recipe's preparation.
   * TODO: Implement the code to fill the instructions field.
   */
  @FXML
  private TextField instructions;

  @FXML
  private ListView<Integer> ingredientsListView;

  /**
   * The GridPane for displaying recommended recipes.
   * TODO: Either implement the code to fill the recommended recipes or drop this feature.
   */
  @FXML
  private GridPane recommendedRecipes;

  /**
   * The list view for displaying ingredients in the recipe
   * and their relevant properties.
   * TODO: Add a custom cell factory for the list view.
   */

  @FXML
  private TextArea instructionsArea;

  /**
   * The label for the name of the recipe.
   */
  @FXML
  private Label recipeNameLabel;

  @FXML
  private Button addRecipeButton;

  @FXML
  private Button deleteRecipeButton;

  @FXML
  private TextField ingredientTextField;

  @FXML
  private Button removeIngredientButton;

  @FXML
  private Button addIngredientButton;

  @FXML
  private TextField quantityTextField;

  @FXML
  private Label quantityLabel;

  @FXML
  private ComboBox<String> selectIngredientComboBox;

  @FXML
  private Button newRecipeButton;

  @FXML
  private TextField recipeNameTextField;

  @FXML
  private TextField portionTextField;


  /**
   * Buttons for saving, or discarding changes made to current recipe.
   */
  @FXML
  private Button saveChangesButton;

  @FXML
  private Button discardChangesButton;

  /**
   * Initialize the controller.
   */
  @FXML
  private void initialize() {

    this.setMainController(mainController);
    ingredientsListView.setCellFactory(param -> new RecipeListCell(this));

  }

  /**
   * Initialise the display of the recipe view.
   */
  public void initializeDisplay() {
    this.displayIngredients();
    this.displayInstructions();
    this.initializeComboBox();
    this.displayName();
    this.initializeButtonHandlers();
    this.initializeSelectionHandlers();
  }

  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null) {
      toolbarController.setMainController(mainController);
    }
  }

  public void setRecipeName(String name) {
    this.recipeName = name;
  }

  /**
   * Queries all ingredients in the recipe and displays them.
   */
  private void displayIngredients() {
    if (recipeName == null) {
      PopupManager.displayErrorFull("Error", "Recipe name is null", "Recipe name is null.");
    }
    try {
      ResultSet ingredients = Recipe.selectIngredientsInRecipe(recipeName);

      List<Integer> ingredientList = new ArrayList<>();
      while (ingredients.next()) {
        ingredientList.add(ingredients.getInt("ingredient_id"));
      }

      ObservableList<Integer> observableList = FXCollections.observableArrayList(ingredientList);
      ingredientsListView.setItems(observableList);
      ingredientsListView.setCellFactory(param -> new RecipeListCell(this));
    } catch (SQLException e) {
      e.printStackTrace();
      PopupManager.displayErrorFull("Error",
          "Error retrieving ingredients", "Error retrieving ingredients.");
    }
  }

  public TextField getPortionTextField() {
    return portionTextField;
  }

  /**
   * Method to show a recipes instructions in the text area.
   */
  private void displayInstructions() {
    if (recipeName == null) {
      instructionsArea.setText("recipe name is null"); // Clear the text area if recipeId is null.
      return;
    }

    try {
      // Query to fetch the recipe's instructions using the recipeId.
      ResultSet recipeInstructions = mainController.sqlConnector.executeSqlSelect(
          "SELECT instruction " +
              "FROM recipe " +
              "WHERE name = '" + recipeName + "';"
      );

      if (recipeInstructions.next()) {
        String instructions = recipeInstructions.getString("instruction");
        instructionsArea.setText(instructions != null ? instructions : "No instructions provided.");
      } else {
        instructionsArea.setText("Recipe not found.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      instructionsArea.setText("Error retrieving instructions.");
    }
  }

  /**
   * Initialise button handlers for the recipe.
   */
  private void initializeButtonHandlers() {

    // Define action listener for the save changes button
    this.saveChangesButton.setOnAction(event -> {
      Recipe.updateInstructions(this.recipeName, this.instructionsArea.getText());
    });

    // Define action listener for the discard changes button
    this.discardChangesButton.setOnAction(event -> {
      this.instructionsArea.setText(Recipe.getInstructions(this.recipeName));
    });

    // Define action listener for button to create new recipe. Create, and go to the new recipe.
    this.newRecipeButton.setOnAction(event -> {
      Recipe.createRecipe(this.recipeNameTextField.getText());
      if (this.mainController != null) {
        this.mainController.goToRecipe(this.recipeNameTextField.getText());
      }
    });

    // Define action listener for the delete recipe button
    this.deleteRecipeButton.setOnAction(event -> {
      Recipe.deleteRecipe(this.recipeName);
      this.mainController.goToCookbook();
    });

    // Define action listener for the add ingredient button
    this.addIngredientButton.setOnAction(event -> {
      String ingredient = this.selectIngredientComboBox.getSelectionModel().getSelectedItem();
      float quantity = Float.parseFloat(this.quantityTextField.getText());
      Recipe.addIngredientToRecipe(this.recipeName, ingredient, quantity);
      this.displayIngredients();
    });

    // Define action listener for the remove ingredient button
    this.removeIngredientButton.setOnAction(event -> {
      int ingredientId = this.ingredientsListView.getSelectionModel().getSelectedItem();
      Recipe.removeIngredientFromRecipe(this.recipeName, ingredientId);
      this.displayIngredients();

    });

  }

  /**
   * Initialize action handlers for ComboBoxes and text fields.
   */
  private void initializeSelectionHandlers() {

    // Set text formatter for the quantity text field to prevent invalid input.
    this.quantityTextField.setTextFormatter(Formatter.getFloatFormatter());

    this.ingredientTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      this.selectIngredientComboBox.getItems().clear();
      ArrayList<String> results = Recipe.searchIngredients(newValue);
      for (int i = 0; i < results.size(); i++) {
        this.selectIngredientComboBox.getItems().add(results.get(i));
      }
    });

    // Add listener to ComboBox such that the unit is updated upon selection.
    this.selectIngredientComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      String unit = Recipe.getIngredientUnit(newValue);
      this.quantityLabel.setText(unit);
    });

    this.portionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      this.displayIngredients();
    });
  }

  /**
   * Initialise the ComboBox such that all ingredients are shown upon entering the recipe view.
   */
  private void initializeComboBox() {
    ArrayList<String> results = Recipe.searchIngredients("");
    for (int i = 0; i < results.size(); i++) {
      this.selectIngredientComboBox.getItems().add(results.get(i));
    }
  }

  /**
   * Display the name of the recipe in the top label.
   */
  private void displayName() {
    this.recipeNameLabel.setText(this.recipeName);
  }
}
