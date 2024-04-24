package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1005.plate.controller.global.MainController;

/**
 * Class for managing SQL queries relating to the shopping list.
 */
public class ShoppingList {

  /**
   * Add an item to the shopping list, or update the quantity if it already exists.
   *
   * @param ingredientId the ingredient id to look for and add.
   * @param quantity     the quantity to add.
   */
  public static void addItem(int ingredientId, float quantity) {
    // Insert the ingredient into the shopping list, or ignore if it already exists
    MainController.sqlConnector.executeSqlUpdate(
        "INSERT OR IGNORE INTO shopping_list_items (ingredient_id, quantity) "
            + "VALUES (" + ingredientId + ", 0);"
    );

    // Increment the quantity of the ingredient
    MainController.sqlConnector.executeSqlUpdate(
        "UPDATE shopping_list_items SET quantity = quantity + " + quantity + " "
            + "WHERE ingredient_id = " + ingredientId + ";"
    );
  }

  /**
   * Check whether the ingredient specified by the ID in question is in the shopping list.
   *
   * @param ingredientId the ingredient ID to check for.
   * @return the boolean value of whether the ingredient is in the shopping list.
   */
  public static boolean inShoppingList(int ingredientId) {
    ResultSet rs = MainController.sqlConnector.executeSqlSelect(
        "SELECT * FROM shopping_list_items WHERE ingredient_id = " + ingredientId + ";"
    );

    try {
      if (rs.next()) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Add selected items to the shopping list
   * @param selectedItems the list of selected items to add
   */
  public static void buySelectedItems(List<Integer> selectedItems) {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity FROM shopping_list_items WHERE ingredient_id IN ("
              + selectedItems.toString().substring(1, selectedItems.toString().length() - 1)
              + ")");
      while (rs.next()) {
        System.out.println(rs.getInt("ingredient_id"));
        int ingredientId = rs.getInt("ingredient_id");
        float qty = rs.getFloat("quantity");

        ResultSet rs2 = MainController.sqlConnector.executeSqlSelect(
            "SELECT * FROM inventory_ingredient WHERE ingredient_id = " + ingredientId);
        if (rs2.next()) {
          MainController.sqlConnector.executeSqlUpdate(
              "UPDATE inventory_ingredient SET quantity = quantity + " + qty
                  + " WHERE ingredient_id = "
                  + ingredientId);
        } else {
          MainController.sqlConnector.executeSqlUpdate(
              "INSERT INTO inventory_ingredient(ingredient_id, quantity ) VALUES(" + ingredientId
                  + ", "
                  + qty + ")");
        }
        MainController.sqlConnector.executeSqlUpdate(
            "DELETE FROM shopping_list_items WHERE ingredient_id = " + ingredientId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Clear selected items from the shopping list.
   *
   * @param selectedItems the list of selected items to clear.
   */
  public static void clearSelectedItems(List<Integer> selectedItems) {
    MainController.sqlConnector.executeSqlUpdate(
        "DELETE FROM shopping_list_items WHERE ingredient_id IN ("
            + selectedItems.toString().substring(1, selectedItems.toString().length() - 1)
            + ")");
  }

  /**
   * Get all items in the shopping list in a hashmap. The key is the ingredient ID and the value is
   * a string representation of the item
   *
   * @return a hashmap of all items in the shopping list
   */
  public static Map<Integer, String> getAllItems() {
    try {
      Map<Integer, String> items = new HashMap<>();
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient.ingredient_id, name, quantity, "
              + "unit FROM shopping_list_items JOIN ingredient ON "
              + "shopping_list_items.ingredient_id = ingredient.ingredient_id");
      while (rs.next()) {
        if (Float.parseFloat(rs.getString("quantity")) <= 0) {
          MainController.sqlConnector.executeSqlUpdate(
              "DELETE FROM shopping_list_items WHERE quantity <= 0");
          continue;
        }
        int ingredientId = rs.getInt("ingredient_id");
        String name = rs.getString("name");
        String quantity = rs.getString("quantity");
        String unit = rs.getString("unit");
        String item = String.format("%-30s %-5s %-15s", name, quantity, unit);
        items.put(ingredientId, item);
      }
      return items;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Add selected items to the shopping list. If the item already exists, update the quantity.
   *
   * @param itemName the name of the item to add
   * @param quantity the quantity of the item to add
   */
  public static void addSelectedItems(String itemName, String quantity) {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id FROM ingredient WHERE name = '" + itemName + "'");
      int ingredientId = rs.getInt("ingredient_id");
      if (ingredientInShoppingList(ingredientId)) {
        updateIngredient(ingredientId, quantity);
        return;
      }

      MainController.sqlConnector.executeSqlUpdate(
          "INSERT INTO shopping_list_items (ingredient_id, quantity) VALUES (" + ingredientId + ", "
              + Float.parseFloat(quantity) + ")");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Check if an ingredient is already in the shopping list.
   *
   * @param ingredientId the ID of the ingredient to check for
   * @return true if the ingredient is in the shopping list, false otherwise
   */
  private static boolean ingredientInShoppingList(int ingredientId) {
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT * FROM shopping_list_items WHERE ingredient_id = " + ingredientId);
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Update the quantity of an ingredient in the shopping list.
   *
   * @param ingredientId the ID of the ingredient to update
   * @param quantity     the quantity to update with
   */
  private static void updateIngredient(int ingredientId, String quantity) {
    try {
      MainController.sqlConnector.executeSqlUpdate(
          "UPDATE shopping_list_items SET quantity = quantity + " + Float.parseFloat(quantity)
              + " WHERE ingredient_id = " + ingredientId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
