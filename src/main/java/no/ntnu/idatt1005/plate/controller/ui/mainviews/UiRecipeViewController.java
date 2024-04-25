package no.ntnu.idatt1005.plate.controller.ui.mainviews;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui.cookbook.RecipeIngredientListCell;
import no.ntnu.idatt1005.plate.controller.ui.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.controller.utility.Formatter;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.model.Inventory;
import no.ntnu.idatt1005.plate.model.Recipe;

/**
 * Controller class for the recipe view.
 *
 * @version 1.0
 */
public class UiRecipeViewController {

  /**
   * The name of the recipe being shown in this recipe view.
   */
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
   */
  @FXML
  private TextField instructions;

  /**
   * The ListView for displaying ingredients in the recipe.
   */
  @FXML
  private ListView<Integer> ingredientsListView;

  /**
   * The GridPane for displaying recommended recipes.
   */
  @FXML
  private GridPane recommendedRecipes;

  /**
   * The text area for displaying (or editing) the instructions for this recipe.
   */
  @FXML
  private TextArea instructionsArea;

  /**
   * The label for the name of the recipe.
   */
  @FXML
  private Label recipeNameLabel;

  /**
   * Button for deleting the selected recipe from the database.
   */
  @FXML
  private Button deleteRecipeButton;

  /**
   * Text field for searching for ingredients to add to the recipe.
   */
  @FXML
  private TextField ingredientTextField;

  /**
   * Button for adding an ingredient to the recipe.
   */
  @FXML
  private Button removeIngredientButton;

  /**
   * Button for adding an ingredient to the recipe.
   */
  @FXML
  private Button addIngredientButton;

  /**
   * Text field for inputting the quantity of the ingredient to add.
   */
  @FXML
  private TextField quantityTextField;

  /**
   * Label for displaying the unit of the ingredient which has been selected.
   */
  @FXML
  private Label unitLabel;

  /**
   * ComboBox for selecting an ingredient to add to the recipe.
   */
  @FXML
  private ComboBox<String> selectIngredientComboBox;

  /**
   * Button for creating a new, blank recipe.
   */
  @FXML
  private Button newRecipeButton;

  /**
   * Text field for adding the recipe name of a new recipe to be created.
   */
  @FXML
  private TextField recipeNameTextField;

  /**
   * Text field for inputting how many portions to display.
   */
  @FXML
  private TextField portionTextField;

  /**
   * Buttons for saving, or discarding changes made to current recipe.
   */
  @FXML
  private Button saveChangesButton;

  /**
   * Button for discarding changes made to the instructions.
   */
  @FXML
  private Button discardChangesButton;

  /**
   * Initialize the controller.
   */
  @FXML
  private void initialize() {
    this.setMainController(mainController);
    ingredientsListView.setCellFactory(param -> new RecipeIngredientListCell(this));
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

  /**
   * Set the name of this recipe.
   *
   * @param name the new name.
   */
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
      ingredientsListView.setCellFactory(param -> new RecipeIngredientListCell(this));
    } catch (SQLException e) {
      PopupManager.displayErrorFull("Error",
          "Error retrieving ingredients", "Error retrieving ingredients.");
    }
  }

  /**
   * Get the text field for inputting the number of portions.
   *
   * @return the TextField object.
   */
  public TextField getPortionTextField() {
    return portionTextField;
  }

  public String getRecipeName() {
    return recipeName;
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
      String instructions = Recipe.getInstructions(recipeName);
      instructionsArea.setText(instructions != null ? instructions : "No instructions provided.");

    } catch (Exception e) {
      PopupManager.displayError("Error", "Could not fetch instructions.");
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
      if (this.recipeNameTextField.getText().isEmpty()) {
        PopupManager.displayErrorFull("Recipe name cannot be empty", "Please enter a recipe name",
            "Recipe name cannot be empty, please enter a recipe name");
        return;
      }
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
      if (ingredient == null || ingredient.isEmpty()) {
        PopupManager.displayErrorFull("Error", "No ingredient selected",
            "No ingredient selected.");
        return;
      }
      if (this.quantityTextField.getText().isEmpty()) {
        PopupManager.displayErrorFull("Error", "No quantity entered",
            "No quantity entered.");
        return;
      }

      float quantity = Float.parseFloat(this.quantityTextField.getText());
      Recipe.addIngredientToRecipe(this.recipeName, ingredient, quantity);
      this.displayIngredients();
    });

    // Define action listener for the remove ingredient button
    this.removeIngredientButton.setOnAction(event -> {
      try {
        int ingredientId = this.ingredientsListView.getSelectionModel().getSelectedItem();
        Recipe.removeIngredientFromRecipe(this.recipeName, ingredientId);
        this.displayIngredients();
      } catch (Exception e) {
        PopupManager.displayErrorFull("Error", "No ingredient selected",
            "No ingredient selected.");
      }
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
      ArrayList<String> results = Inventory.searchIngredients(newValue);
      for (int i = 0; i < results.size(); i++) {
        this.selectIngredientComboBox.getItems().add(results.get(i));
      }
    });

    // Add listener to ComboBox such that the unit is updated upon selection.
    this.selectIngredientComboBox.getSelectionModel().selectedItemProperty().addListener((
        observable, oldValue, newValue) -> {
      String unit = Recipe.getIngredientUnit(newValue);
      this.unitLabel.setText(unit);
    });

    this.portionTextField.setTextFormatter(Formatter.getFloatFormatter());
    this.portionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      this.displayIngredients();
    });
  }

  /**
   * Initialise the ComboBox such that all ingredients are shown upon entering the recipe view.
   */
  private void initializeComboBox() {
    ArrayList<String> results = Inventory.searchIngredients("");
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
