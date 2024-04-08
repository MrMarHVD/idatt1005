package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import no.ntnu.idatt1005.plate.controller.cookbook.GridPaneGenerator;
import no.ntnu.idatt1005.plate.controller.cookbook.RecipeListCell;
import no.ntnu.idatt1005.plate.controller.inventory.IngredientListCell;

import no.ntnu.idatt1005.plate.controller.global.MainController;

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

  String recipeName;

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


  @FXML
  private TextArea instructionsArea;

  /**
   * Initialize the controller.
   */
  @FXML
  public void initialize() {

    this.setMainController(mainController);
    ingredientsListView.setCellFactory(param -> new IngredientListCell());
    showInstructions(1, false);
    displayIngredients();
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
   * Set the recipe name.
   *
   * @param name the name of the recipe.
   */
  public void setRecipeName(String name) { this.recipeName = name; }

  /**
   * Queries all ingredients in the recipe and displays them.
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
    ingredientsListView.setItems(observableIngredients);
    ingredientsListView.setCellFactory(param -> new RecipeListCell());
  }

  /**
   * Method to show a recipes instructions
   * @param recipeId
   * @param empty
   */
  public void showInstructions(Integer recipeId, boolean empty) {
    if (recipeId != null && !empty) {
      try {
        ResultSet recipeInstructions = MainController.sqlConnector.executeSqlSelect(
                "SELECT instruction, name FROM recipe WHERE recipe_id = " + recipeId + ";"
        );
        System.out.println("showing instructions");

        if (recipeInstructions.next()) {
          String instruction = recipeInstructions.getString("instruction");
          instructionsArea.setText(instruction != null ? instruction : "None");

          System.out.println("showing instructions");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }



}
