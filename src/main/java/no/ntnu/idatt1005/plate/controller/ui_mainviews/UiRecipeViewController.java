package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.cookbook.RecipeListCell;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.global.PopupManager;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import javafx.fxml.FXML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
   * Initialize the controller.
   */
  @FXML
  private void initialize() {

    this.setMainController(mainController);
    ingredientsListView.setCellFactory(param -> new RecipeListCell());

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
  public void displayIngredients() {
    if (recipeName == null) {
      PopupManager.displayErrorFull("Error", "Recipe name is null", "Recipe name is null.");
    }
    try {
      ResultSet ingredients = mainController.sqlConnector.executeSqlSelect(
          "SELECT recipe_ingredients.ingredient_id " +
              "FROM recipe_ingredients " +
              "JOIN recipe ON recipe_ingredients.recipe_id = recipe.recipe_id " +
              "WHERE recipe.name = '" + recipeName + "';"

      );

      List<Integer> ingredientList = new ArrayList<>();
      while (ingredients.next()) {
        ingredientList.add(ingredients.getInt("ingredient_id"));
      }

      ObservableList<Integer> observableList = FXCollections.observableArrayList(ingredientList);
      ingredientsListView.setItems(observableList);
      ingredientsListView.setCellFactory(param -> new RecipeListCell());
    } catch (SQLException e) {
      e.printStackTrace();
      PopupManager.displayErrorFull("Error", "Error retrieving ingredients", "Error retrieving ingredients.");
    }
  }

  /**
   * Method to show a recipes instructions in the text area.
   */
  public void displayInstructions() {
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




}
