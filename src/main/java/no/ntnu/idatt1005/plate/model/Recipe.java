package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.data.SqlConnector;

/**
 * This is a class for managing SQL-queries relating to the recipe view.
 */
public class Recipe {

  /**
   * The SQL connector for this class.
   */
  private static SqlConnector sqlConnector = MainController.sqlConnector;

  /**
   * Set the SQL connector to something other than that belonging to the MainController (testing).
   *
   * @param sqlConnector the SQL connector to assign.
   */
  public static void setSqlConnector(SqlConnector sqlConnector) {
    Recipe.sqlConnector = sqlConnector;
  }

  /**
   * Update the instructions in the given recipe to the user's new input.
   *
   * @param recipeName the name of the recipe whose instructions are to be changed.
   * @param instructions the new instructions to update with.
   */
  public static void updateInstructions(String recipeName, String instructions) {
    try {
      // Escape line breaks in the instructions string

      Recipe.sqlConnector.executeSqlUpdate(
          "UPDATE recipe SET instruction = '" + instructions
              + "' WHERE name = '" + recipeName + "';");
    } catch (Exception e) {
      PopupManager.displayError("Update error", "Could not update instructions");
    }
  }

  /**
   * Get the instructions for the given recipe.
   *
   * @param recipeName the name of the recipe.
   * @return the instructions for the recipe.
   */
  public static String getInstructions(String recipeName) {
    String instructions = null;
    System.out.println(recipeName);
    try {
      instructions = Recipe.sqlConnector.executeSqlSelect(
          "SELECT instruction FROM recipe WHERE name = '" + recipeName + "';")
              .getString("instruction");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", e.getMessage());
    }
    return instructions;
  }

  /**
   * Delete the selected recipe from the database.
   *
   * @param recipeName the name of the recipe to delete.
   */
  public static void deleteRecipe(String recipeName) {
    try {
      Recipe.sqlConnector.executeSqlUpdate(
          "DELETE FROM recipe WHERE name = '" + recipeName + "';");
    } catch (Exception e) {
      PopupManager.displayError("Delete error", "Could not delete recipe");
    }
  }

  /**
   * Create a recipe with the given name.
   *
   * @param recipeName the name of the new recipe.
   */
  public static void createRecipe(String recipeName) {
    try {
      Recipe.sqlConnector.executeSqlUpdate(
          "INSERT INTO recipe (name) VALUES ('" + recipeName + "');");
    } catch (Exception e) {
      PopupManager.displayError("Create error", "Could not create recipe");
    }
  }

  /**
   * Get the unit of a given ingredient.
   *
   * @param ingredientName the name of the ingredient.
   * @return the unit of the ingredient.
   */
  public static String getIngredientUnit(String ingredientName) {
    String unit = null;
    try {
      unit = Recipe.sqlConnector.executeSqlSelect(
          "SELECT unit FROM ingredient WHERE name = '" + ingredientName
              + "';").getString("unit");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", e.getMessage());
    }
    return unit;
  }

  /**
   * Add the ingredient with the given name to the recipe with the given name,
   * with the given quantity.
   *
   * @param recipeName the name of the recipe to which the ingredient is to be added.
   * @param ingredientName the name of the ingredient to add.
   * @param quantity the quantity of the ingredient to add.
   */
  public static void addIngredientToRecipe(String recipeName, String ingredientName,
      float quantity) {
    try {
      int recipeId = Recipe.sqlConnector.executeSqlSelect(
          "SELECT recipe_id FROM recipe WHERE name = '" + recipeName + "';")
          .getInt("recipe_id");
      int ingredientId = Recipe.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM ingredient WHERE name = '" + ingredientName + "';")
          .getInt("ingredient_id");
      Recipe.sqlConnector.executeSqlUpdate(
          "INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity) VALUES "
              + "(" + recipeId + ", " + ingredientId + ", " + quantity + ");");
      System.out.println("Test: " + quantity);
    } catch (Exception e) {
      PopupManager.displayError("Insert error", "Could not insert ingredient");
    }
  }

  /**
   * Remove a given ingredient from the given recipe.
   *
   * @param recipeName the name of the recipe.
   * @param ingredientId the id of the ingredient to remove.
   */
  public static void removeIngredientFromRecipe(String recipeName, int ingredientId) {
    try {
      int recipeId = Recipe.sqlConnector.executeSqlSelect(
          "SELECT recipe_id FROM recipe WHERE name = '"
              + recipeName + "';").getInt("recipe_id");
      Recipe.sqlConnector.executeSqlUpdate(
          "DELETE FROM recipe_ingredients WHERE recipe_id = "
              + recipeId + " AND ingredient_id = " + ingredientId + ";");
    } catch (Exception e) {
      PopupManager.displayError("Delete error", "Could not delete ingredient");
    }
  }

  /**
   * Select the ingredients in a given recipe.
   *
   * @param recipeName the name of the recipe.
   * @return the result set of ingredients.
   */
  public static ResultSet selectIngredientsInRecipe(String recipeName) {
    ResultSet ingredients = null;

    try {
      ingredients = Recipe.sqlConnector.executeSqlSelect(
          "SELECT recipe_ingredients.ingredient_id "
              + "FROM recipe_ingredients "
              + "JOIN recipe ON recipe_ingredients.recipe_id = recipe.recipe_id "
              + "WHERE recipe.name = '" + recipeName + "';");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", "Could not select ingredients");
    }
    return ingredients;
  }

  /**
   * Get the ID of the recipe with the given name.
   *
   * @param recipeName the name of the recipe.
   * @return the ID of the recipe.
   */
  public static int getRecipeIdFromName(String recipeName) {
    int recipeId = -1;
    try {
      recipeId = Recipe.sqlConnector.executeSqlSelect(
          "SELECT recipe_id FROM recipe WHERE name = '" + recipeName + "';").getInt("recipe_id");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", "Could not select recipe id");
    }
    return recipeId;
  }


}
