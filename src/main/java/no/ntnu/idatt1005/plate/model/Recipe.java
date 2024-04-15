package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.global.PopupManager;

/**
 * This is a class for managing SQL-queries relating to the recipe view.
 */
public class Recipe {

  /**
   * Update the instructions in the given recipe to the user's new input.
   *
   * @param recipeName the name of the recipe whose instructions are to be changed.
   * @param instructions the new instructions to update with.
   */
  public static void updateInstructions(String recipeName, String instructions) {
    try {
      // Escape line breaks in the instructions string

      MainController.sqlConnector.executeSqlUpdate(
          "UPDATE recipe SET instruction = '" + instructions + "' WHERE name = '" + recipeName + "';");
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
      instructions = MainController.sqlConnector.executeSqlSelect(
          "SELECT instruction FROM recipe WHERE name = '" + recipeName + "';").getString("instruction");
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
      MainController.sqlConnector.executeSqlUpdate(
          "DELETE FROM recipe WHERE name = '" + recipeName + "';");
    } catch (Exception e) {
      PopupManager.displayError("Delete error", "Could not delete recipe");
    }
  }

  public static void createRecipe(String recipeName) {
    try {
      MainController.sqlConnector.executeSqlUpdate(
          "INSERT INTO recipe (name) VALUES ('" + recipeName + "');");
    } catch (Exception e) {
      PopupManager.displayError("Create error", "Could not create recipe");
    }
  }

  /**
   * Search the database for ingredients with a name that contains the search string.
   *
   * @param search the search input.
   * @return a list of all the matching ingredients.
   */
  public static ArrayList<String> searchIngredients(String search) {
    ArrayList<String> ingredients = new ArrayList<>();
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect("SELECT name FROM ingredient WHERE name LIKE '%" + search + "%'");
      while (rs.next()) {
        String name = rs.getString("name");
        ingredients.add(name);
      }
    } catch (Exception e) {
      e.getMessage();
    }
    return ingredients;
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
      unit = MainController.sqlConnector.executeSqlSelect(
          "SELECT unit FROM ingredient WHERE name = '" + ingredientName + "';").getString("unit");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", e.getMessage());
    }
    return unit;
  }

  // TODO: add method which checks whether the ingredient the user has tried to add, is already in the recipe. If so, add quantity instead.
  public static void addIngredientToRecipe(String recipeName, String ingredientName, float quantity) {
    try {
      int recipeId = MainController.sqlConnector.executeSqlSelect(
          "SELECT recipe_id FROM recipe WHERE name = '" + recipeName + "';").getInt("recipe_id");
      int ingredientId = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM ingredient WHERE name = '" + ingredientName + "';").getInt("ingredient_id");
      MainController.sqlConnector.executeSqlUpdate(
          "INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity) VALUES (" + recipeId + ", " + ingredientId + ", " + quantity + ");");
    } catch (Exception e) {
      PopupManager.displayError("Insert error", "Could not insert ingredient");
    }
  }

  public static void removeIngredientFromRecipe(String recipeName, int ingredientId) {
    try {
      int recipeId = MainController.sqlConnector.executeSqlSelect(
          "SELECT recipe_id FROM recipe WHERE name = '" + recipeName + "';").getInt("recipe_id");
      MainController.sqlConnector.executeSqlUpdate(
          "DELETE FROM recipe_ingredients WHERE recipe_id = " + recipeId + " AND ingredient_id = " + ingredientId + ";");
    } catch (Exception e) {
      PopupManager.displayError("Delete error", "Could not delete ingredient");
    }
  }

  public static ResultSet selectIngredientsInRecipe(String recipeName) {
    ResultSet ingredients = null;

    try {
      ingredients = MainController.sqlConnector.executeSqlSelect(
          "SELECT recipe_ingredients.ingredient_id " +
              "FROM recipe_ingredients " +
              "JOIN recipe ON recipe_ingredients.recipe_id = recipe.recipe_id " +
              "WHERE recipe.name = '" + recipeName + "';");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", "Could not select ingredients");
    }
    return ingredients;
  }

}
