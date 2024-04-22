package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.data.SqlConnector;

/**
 * Class for managing SQL queries relating to the shopping list.
 */
public class ShoppingList {

  /**
   * The SQL connector for this class.
   */
  public static SqlConnector sqlConnector = MainController.sqlConnector;

  /**
   * Set the SQL connector to something other than that belonging to the MainController (testing).
   *
   * @param sqlConnector the SQL connector to assign.
   */
  public static void setSqlConnector(SqlConnector sqlConnector) {
    ShoppingList.sqlConnector = sqlConnector;
  }

  /**
   * Add an item to the shopping list, or update the quantity
   * if it already exists.
   *
   * @param ingredientId the ingredient id to look for and add.
   * @param quantity the quantity to add.
   */
  public static void addItem(int ingredientId, float quantity) {
    // Insert the ingredient into the shopping list, or ignore if it already exists
    ShoppingList.sqlConnector.executeSqlUpdate(
        "INSERT OR IGNORE INTO shopping_list_items (ingredient_id, quantity) "
            + "VALUES (" + ingredientId + ", 0);"
    );

    // Increment the quantity of the ingredient
    ShoppingList.sqlConnector.executeSqlUpdate(
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
    ResultSet rs = ShoppingList.sqlConnector.executeSqlSelect(
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
   * Buy items in the shopping list (move to inventory).
   *
   * @param selectedItems the selected items to be bought.
   * @throws SQLException if the SQL query fails (caught by the caller).
   */
  public static void buyItems(List<Integer> selectedItems) throws SQLException {

    ResultSet rs = ShoppingList.sqlConnector.executeSqlSelect(
        "SELECT ingredient_id, quantity FROM shopping_list_items WHERE ingredient_id IN ("
            + selectedItems.toString().substring(1, selectedItems.toString().length() - 1)
            + ")");
    while (rs.next()) {
      int ingredientId = rs.getInt("ingredient_id");
      float qty = rs.getFloat("quantity");

      ResultSet rs2 = ShoppingList.sqlConnector.executeSqlSelect(
          "SELECT * FROM inventory_ingredient WHERE ingredient_id = " + ingredientId);
      if (rs2.next()) {
        ShoppingList.sqlConnector.executeSqlUpdate(
            "UPDATE inventory_ingredient SET quantity = quantity + " + qty
                + " WHERE ingredient_id = "
                + ingredientId);
      } else {
        ShoppingList.sqlConnector.executeSqlUpdate(
            "INSERT INTO inventory_ingredient(ingredient_id, quantity ) VALUES(" + ingredientId
                + ", "
                + qty + ")");
      }
      ShoppingList.sqlConnector.executeSqlUpdate(
          "DELETE FROM shopping_list_items WHERE ingredient_id = " + ingredientId);
    }
  }

  /**
   * Delete items from the shopping list.
   *
   * @param selectedItems the items to delete.
   * @throws SQLException if the SQL query fails (caught by the caller).
   */
  public static void deleteItems(List<Integer> selectedItems) throws SQLException {
    ShoppingList.sqlConnector.executeSqlUpdate(
        "DELETE FROM shopping_list_items WHERE ingredient_id IN ("
            + selectedItems.toString().substring(1, selectedItems.toString().length() - 1)
            + ")");
  }

  /**
   * Select all the items in the ingredient table that are in the shopping list.
   *
   * @return a ResultSet with the returned items.
   */
  public static ResultSet selectAllMatchingIds() {
    return ShoppingList.sqlConnector.executeSqlSelect(
        "SELECT ingredient.ingredient_id, name, quantity, "
            + "unit FROM shopping_list_items JOIN ingredient ON "
            + "shopping_list_items.ingredient_id = ingredient.ingredient_id");
  }

  /**
   * Delete all items in the shopping list with a quantity less than or equal to zero.
   */
  public static void deleteLessThanZero() {
    ShoppingList.sqlConnector.executeSqlUpdate(
        "DELETE FROM shopping_list_items WHERE quantity <= 0");
  }

  /**
   * Select all items in the shopping list.
   *
   * @return a ResultSet of all items in the shopping list.
   */
  public static ResultSet selectAllItems() {
    return ShoppingList.sqlConnector.executeSqlSelect(
        "SELECT ingredient_id FROM shopping_list_items");
  }


  /**
   * Select a shopping list item from its ingredient ID.
   *
   * @param ingredientId the ID to select from.
   * @return a ResultSet of the selected item.
   */
  public static ResultSet selectShoppingListItemFromId(int ingredientId) {
    return ShoppingList.sqlConnector.executeSqlSelect(
        "SELECT * FROM shopping_list_items WHERE ingredient_id = " + ingredientId);
  }

  /**
   * Update the quantity of an item in the shopping list.
   *
   * @param ingredientId the ID of the ingredient to update.
   * @param quantity the quantity to update with.
   */
  public static void updateShoppingListQuantity(int ingredientId, float quantity) {
    ShoppingList.sqlConnector.executeSqlUpdate(
        "UPDATE shopping_list_items SET quantity = quantity + " + quantity
            + " WHERE ingredient_id = " + ingredientId);
  }

  /**
   * Insert an item into the shopping list.
   *
   * @param ingredientId the ID of the ingredient to insert.
   * @param quantity the quantity of the ingredient to insert.
   */
  public static void insertIntoShoppingList(int ingredientId, float quantity) {
    // Check if the ingredient exists in the ingredient table
    ResultSet rs = ShoppingList.sqlConnector.executeSqlSelect(
        "SELECT * FROM ingredient WHERE ingredient_id = " + ingredientId);

    try {
      // If the ingredient exists, insert it into the shopping list
      if (rs.next()) {
        ShoppingList.sqlConnector.executeSqlUpdate(
            "INSERT INTO shopping_list_items (ingredient_id, quantity) VALUES (" + ingredientId + ", "
                + quantity + ")");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
