package no.ntnu.idatt1005.plate.model;

import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ShoppingList class.

 */
class ShoppingListTest {

  private static final String dbFileName = "shopping_list_test.db";

  /**
   * The SQL connector used for testing.
   */
  private static final SqlConnector sqlConnector = new SqlConnector(dbFileName);

  /**
   * Set up the test environment by resetting the database before each test.
   */
  @BeforeEach
  void setUp() {
    sqlConnector.resetTestDatabase();
    ShoppingList.setSqlConnector(sqlConnector);
  }

  /**
   * Test the addItem method by asserting that the ingredient added is in the shopping list.
   */
  @Test
  @DisplayName("addItem test")
  void testAddItem() {
    // Arrange
    int ingredientId = 1000;
    float quantity = 2.0f;

    // Act
    ShoppingList.addItem(ingredientId, quantity);

    // Assert
    assertTrue(ShoppingList.inShoppingList(ingredientId));
  }

  /**
   * Test the inShoppingList method by asserting that the ingredient added is in the
   * shopping list.
   */
  @Test
  @DisplayName("inShoppingList test")
  void testInShoppingList() {

    // Arrange
    int ingredientId = 1;
    ShoppingList.addItem(ingredientId, 1);

    // Act
    boolean exists = ShoppingList.inShoppingList(ingredientId);

    // Assert
    assertTrue(exists);
  }
}