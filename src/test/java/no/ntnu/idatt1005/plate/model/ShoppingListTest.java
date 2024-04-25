package no.ntnu.idatt1005.plate.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ShoppingList class.

 */
class ShoppingListTest {

  /**
   * The SQL connector used for testing.
   */
  private static final SqlConnector sqlConnector = new SqlConnector("memory");

  /**
   * Set up the test environment by resetting the database before each test.
   */
  @BeforeEach
  void setUp() {
    sqlConnector.closeConnection();
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

  /**
   * Test the buyItems-method by asserting that an ingredient added to the shopping list is not in
   * the shopping list after being bought.
   */
  @Test
  @DisplayName("buyItems test")
  void testBuyItemsPositive() {
    // Arrange
    int ingredientId = 1000;
    float quantity = 2.0f;
    ShoppingList.addItem(ingredientId, quantity);

    // Act
    try {
      ShoppingList.buyItems(Arrays.asList(ingredientId));
    } catch (SQLException e) {
      fail("SQLException should not have been thrown.");
    }

    // Assert
    assertFalse(ShoppingList.inShoppingList(ingredientId));
  }

  /**
   * Test deleteItems-method by asserting that an ingredient added to the shopping list is not in
   * the shopping list after being deleted.
   */
  @Test
  @DisplayName("deleteItems test")
  void testDeleteItemsPositive() {
    // Arrange
    int ingredientId = 5;
    float quantity = 2.0f;
    ShoppingList.addItem(ingredientId, quantity);

    // Act
    try {
      ShoppingList.deleteItems(Arrays.asList(ingredientId));
    } catch (SQLException e) {
      fail("SQLException should not have been thrown.");
    }

    // Assert
    assertFalse(ShoppingList.inShoppingList(ingredientId));
  }

  /**
   * Test the selectAllMatchingIds-method by asserting that the correct items in the
   * shopping list are returned.
   */
  @Test
  @DisplayName("selectAllMatchingIds test")
  void testSelectAllMatchingIdsPositive() {
    // Arrange
    int ingredientId = 5;
    float quantity = 2.0f;
    ShoppingList.addItem(ingredientId, quantity);

    // Act
    ResultSet rs = null;
    try {
      rs = ShoppingList.selectAllMatchingIds();
    } catch (Exception e) {
      fail("Exception should not have been thrown.");
    }

    // Assert
    try {
      assertTrue(rs.next());
      assertEquals(ingredientId, rs.getInt("ingredient_id"));
      assertEquals(quantity, rs.getFloat("quantity"), 0.01);
    } catch (SQLException e) {
      fail("SQLException should not have been thrown.");
    }
  }

  /**
   * Test the deleteLessThanZero-method by asserting that an ingredient with a negative quantity
   * is not in the shopping list.
   */
  @Test
  @DisplayName("deleteLessThanZero test")
  void testDeleteLessThanZero() {
    // Arrange
    int ingredientId = 5;
    float quantity = -2.0f;
    ShoppingList.addItem(ingredientId, quantity);

    // Act
    ShoppingList.deleteLessThanZero();

    // Assert
    assertFalse(ShoppingList.inShoppingList(ingredientId));
  }

  /**
   * Test the selectAllItems-method by asserting that the correct items in the
   * shopping list are returned after being added.
   */
  @Test
  @DisplayName("selectAllItems test")
  void testSelectAllItems() {
    // Arrange
    int ingredientId1 = 5;
    float quantity1 = 2.0f;
    ShoppingList.addItem(ingredientId1, quantity1);

    int ingredientId2 = 6;
    float quantity2 = 3.0f;
    ShoppingList.addItem(ingredientId2, quantity2);

    // Act
    ResultSet rs = null;
    try {
      rs = ShoppingList.selectAllItems();
    } catch (Exception e) {
      fail("Exception should not have been thrown.");
    }

    // Assert
    try {
      assertTrue(rs.next());
      assertEquals(ingredientId1, rs.getInt("ingredient_id"));

      assertTrue(rs.next());
      assertEquals(ingredientId2, rs.getInt("ingredient_id"));

      assertFalse(rs.next()); // There should be no more items
    } catch (SQLException e) {
      fail("SQLException should not have been thrown.");
    }
  }

  /**
   * Test selectShoppingListItemFromId by adding a new ingredient and asserting that
   * the correct ingredient is returned.
   */
  @Test
  @DisplayName("selectShoppingListItemFromId test")
  void testSelectShoppingListItemFromId() {
      // Arrange
      int ingredientId = 7;
      float quantity = 2.0f;
      ShoppingList.addItem(ingredientId, quantity);

      // Act
      ResultSet rs = null;
      try {
          rs = ShoppingList.selectShoppingListItemFromId(ingredientId);
      } catch (Exception e) {
          fail("Exception should not have been thrown.");
      }

      // Assert
      try {
          assertTrue(rs.next());
          assertEquals(ingredientId, rs.getInt("ingredient_id"));
          assertEquals(quantity, rs.getFloat("quantity"), 0.01);
      } catch (SQLException e) {
          fail("SQLException should not have been thrown.");
      }
  }

  @Test
  @DisplayName("updateShoppingListQuantity test")
  void testUpdateShoppingListQuantity() {
    // Arrange
    int ingredientId = 7;
    float initialQuantity = 2.0f;
    float additionalQuantity = 3.0f;
    ShoppingList.addItem(ingredientId, initialQuantity);

    // Act
    ShoppingList.updateShoppingListQuantity(ingredientId, additionalQuantity);

    // Assert
    ResultSet rs = null;
    try {
      rs = ShoppingList.selectShoppingListItemFromId(ingredientId);
    } catch (Exception e) {
      fail("Exception should not have been thrown.");
    }

    try {
      assertTrue(rs.next());
      assertEquals(ingredientId, rs.getInt("ingredient_id"));
      assertEquals(initialQuantity + additionalQuantity, rs.getFloat("quantity"), 0.01);
    } catch (SQLException e) {
      fail("SQLException should not have been thrown.");
    }
  }

  /**
   * Test the insertIntoShoppingList method by asserting that the ingredient added is
   * to be found in the shopping list.
   */
  @Test
  @DisplayName("insertIntoShoppingList test")
  void testInsertIntoShoppingList() {
    // Arrange
    int ingredientId = 7;
    float quantity = 2.0f;

    // Act
    ShoppingList.insertIntoShoppingList(ingredientId, quantity);

    // Assert
    ResultSet rs = null;
    try {
      rs = ShoppingList.selectShoppingListItemFromId(ingredientId);
    } catch (Exception e) {
      fail("Exception should not have been thrown.");
    }

    try {
      assertTrue(rs.next());
      assertEquals(ingredientId, rs.getInt("ingredient_id"));
      assertEquals(quantity, rs.getFloat("quantity"), 0.01);
    } catch (SQLException e) {
      fail("SQLException should not have been thrown.");
    }
  }

  @Test
  @DisplayName("getAllItems returns a map with the correct items")
  void testGetAllItemsReturnsCorrectItems() {
    // Arrange
    int ingredientId1 = 5;
    float quantity1 = 2.0f;
    ShoppingList.addItem(ingredientId1, quantity1);

    int ingredientId2 = 6;
    float quantity2 = 1.0f;
    ShoppingList.addItem(ingredientId2, quantity2);

    // Act
    var items = ShoppingList.getAllItems();
    // Assert
    assertTrue(items.containsKey(ingredientId1));
  }

}
