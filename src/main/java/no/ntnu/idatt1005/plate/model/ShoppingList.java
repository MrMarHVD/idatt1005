package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idatt1005.plate.controller.global.MainController;

/**
 * Class for managing SQL queries relating to the shopping list.
 */
public class ShoppingList {

  /**
   * Add an item to the shopping list, or update the quantity
   * if it already exists.
   *
   * @param ingredientId the ingredient id to look for and add.
   * @param quantity the quantity to add.
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

}
