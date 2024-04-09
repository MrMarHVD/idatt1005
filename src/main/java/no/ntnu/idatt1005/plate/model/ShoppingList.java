package no.ntnu.idatt1005.plate.model;

import no.ntnu.idatt1005.plate.controller.global.MainController;

/**
 * Class for managing SQL queries relating to the shopping list.
 */
public class ShoppingList {

  public static void addItem(int ingredientId, float quantity) {
    // Insert the ingredient into the shopping list, or ignore if it already exists
    MainController.sqlConnector.executeSqlUpdate(
        "INSERT OR IGNORE INTO shopping_list_items (ingredient_id, quantity) " +
            "VALUES (" + ingredientId + ", 0);"
    );

    // Increment the quantity of the ingredient
    MainController.sqlConnector.executeSqlUpdate(
        "UPDATE shopping_list_items SET quantity = quantity + " + quantity + " " +
            "WHERE ingredient_id = " + ingredientId + ";"
    );
  }

}
