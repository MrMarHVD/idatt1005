package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;

/**
 * Class for managing the sql queries for the inventory.
 */
public class Inventory {

  /**
   * Select an ingredient by its ID.
   *
   * @param ingredientId the ID to compare with.
   * @return the name of the ingredient.
   */
  public static String selectIngredient(int ingredientId) {
    String ingredientName = null;
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT * FROM ingredient WHERE ingredient_id = " + ingredientId + ";");
      if (rs.next()) {
        ingredientName = rs.getString("name");
      } else {
        return null;
      }
    } catch (Exception e) {
      PopupManager.displayError("Selection error", "Could not select ingredient");
    }
    return ingredientName;
  }

  /**
   * Check whether the given ingredient exists in the inventory.
   *
   * @param name the name of the ingredient.
   * @return the boolean value of whether the ingredient exists.
   */
  public static boolean ingredientExistsInInventory(String name) {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT * FROM inventory_ingredient "
              + "LEFT JOIN ingredient i ON inventory_ingredient.ingredient_id = i.ingredient_id "
              + "WHERE i.name = '" + name + "';");
      if (rs.next()) {
        return true;
      }
    } catch (Exception e) {
      PopupManager.displayError("Selection error", "Could not select ingredient");
    }
    return false;
  }

  public static boolean ingredientExists(String name) {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT * FROM ingredient WHERE name = '" + name + "';");
      if (rs.next()) {
        return true;
      }
    } catch (Exception e) {
      PopupManager.displayError("Selection error", "Could not select ingredient");
    }
    return false;
  }

  /**
   * Select all ingredients in the inventory.
   *
   * @return the result set of the query.
   */
  public static ResultSet selectAllInventoryIngredients() {
    return MainController.sqlConnector.executeSqlSelect(
        "SELECT i.ingredient_id "
            + "FROM ingredient i "
            + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id;");
  }

  /**
   * Search for ingredients in the inventory.
   *
   * @param search the search query.
   * @return the result set of the query.
   */
  public static ResultSet searchIngredientsInInventory(String search) {
    return MainController.sqlConnector.executeSqlSelect(""
        + "SELECT i.ingredient_id "
        + "FROM ingredient i "
        + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
        + "WHERE i.name LIKE '%" + search + "%';");
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
   * Sort ingredients by name.
   *
   * @return a result set of sorted ingredients by name.
   */
  public static ResultSet sortByName() {
    return MainController.sqlConnector.executeSqlSelect(""
        + "SELECT i.ingredient_id "
        + "FROM ingredient i "
        + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
        + "ORDER BY i.name ASC;");
  }

  /**
   * Sort ingredients by category.
   *
   * @return a result set of the sorted ingredients by category.
   */
  public static ResultSet sortByCategory() {
    return MainController.sqlConnector.executeSqlSelect(""
        + "SELECT i.ingredient_id "
        + "FROM ingredient i "
        + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
        + "LEFT JOIN category c ON i.category_id = c.id "
        + "ORDER BY c.name ASC;");
  }

  /**
   * Delete the ingredient with the given ID from the inventory.
   *
   * @param ingredientId the ID of the ingredient to delete.
   */
  public static void deleteIngredient(int ingredientId) {
    MainController.sqlConnector.executeSqlUpdate(""
        + "DELETE FROM inventory_ingredient "
        + "WHERE ingredient_id = " + ingredientId + ";");
  }

  /**
   * Update an ingredient with the given quantity.
   *
   * @param name the name of the ingredient to update.
   * @param quantity the quantity to update with.
   */
  public static void updateIngredient(String name, float quantity) {
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

  /**
   * Add new ingredient to the inventory.
   *
   * @param name the name of the ingredient to add.
   * @param quantity the quantity of the ingredient to add.
   */
  public static void addNewIngredient(String name, float quantity) {
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
      PopupManager.displayErrorFull("Error", "Failed to add ingredient", e.getMessage());
      e.printStackTrace();
    }
  }
}
