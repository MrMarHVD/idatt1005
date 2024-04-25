package no.ntnu.idatt1005.plate.model;

import java.util.ArrayList;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Inventory class.
 */
class InventoryTest {


  /**
   * The SQL connector used for testing.
   */
  private static final SqlConnector sqlConnector = new SqlConnector("memory");

  /**
   * The name of the ingredient used for testing.
   */
  private static final String ingredientName = "Apple";

  /**
   * Set up the test environment by resetting the database before each test.
   */
  @BeforeEach
  void setUp() {
    sqlConnector.closeConnection();
    sqlConnector.resetTestDatabase();
    Inventory.setSqlConnector(sqlConnector);
  }

  /**
   * Test the selectIngredient method by asserting the name of the ingredient to be the one
   * correctly corresponding to the given ID.
   */
  @Test
  void testSelectIngredient() {
    int ingredientId = 1;
    assertEquals("Apple", Inventory.selectIngredient(ingredientId));
  }

  /**
   * Test whether the ingredient exists in the inventory by asserting that the
   * ingredient with the given name exists in the inventory (this ingredient is
   * automatically added to the inventory by the database reset).
   */
  @Test
  void testIngredientExists() {
      assertTrue(Inventory.ingredientExists(ingredientName));
  }

  /**
   * Test whether selecting all ingredients works by asserting that the returned ResultSet
   * has a next element.
   *
   * @throws SQLException if the SQL query fails.
   */
  @Test
  void testSelectAllInventoryIngredients() throws SQLException {
    ResultSet rs = Inventory.selectAllInventoryIngredients();
    assertTrue(rs.next());
  }

  /**
   * Test whether searching ingredients works by asserting that the ingredient with the given
   * name is found.
   *
   * @throws SQLException if the SQL query fails.
   */
  @Test
  void testSearchIngredients() throws SQLException {
    String search = ingredientName; // Replace with actual ingredient name
    ArrayList<String> apple = Inventory.searchIngredients(search);
    assertEquals(apple.get(0), ingredientName);
  }

  /**
   * Test whether sorting ingredients by name works by asserting that the returned ResultSet
   * has a next element.
   *
   * @throws SQLException if the SQL query fails.
   */
  @Test
  void testSortByName() throws SQLException {
    ResultSet rs = Inventory.sortByName();
    assertTrue(rs.next());
  }

  /**
   * Test whether sorting ingredients by category works by asserting that the returned ResultSet
   * has a next element.
   *
   * @throws SQLException if the SQL query fails.
   */
  @Test
  void testSortByCategory() throws SQLException {
    ResultSet rs = Inventory.sortByCategory();
    assertTrue(rs.next());
  }

  /**
   * Test whether deleting an ingredient works by asserting that the ingredient added and then
   * deleted is no longer in the inventory.
   */
  @Test
  void testDeleteIngredient() {
    int ingredientId = 4; // Replace with actual ingredient id
    Inventory.deleteIngredient(ingredientId);
    Inventory.addNewIngredient("Beef", 2.0f);
    Inventory.deleteIngredient(ingredientId);
    assertFalse(Inventory.ingredientExistsInInventory(Inventory.selectIngredient(ingredientId)));
  }

  /**
   * Test whether updating an ingredient works by asserting that the quantity of the ingredient
   * is equal to the old quantity plus the additional quantity added.
   *
   * @throws SQLException if the SQL query fails.
   */
  @Test
  void testUpdateIngredient() throws SQLException {
    ResultSet rs1 = sqlConnector.executeSqlSelect("SELECT "
        + "* FROM inventory_ingredient WHERE id = 1;");
    float oldQuantity = rs1.getFloat("quantity");
    float additionalQuantity = 3.0f;
    Inventory.updateIngredient("Milk", additionalQuantity);
    ResultSet rs2 = sqlConnector.executeSqlSelect("SELECT "
        + "* FROM inventory_ingredient WHERE id = 1;");
    float actualQuantity = rs2.getFloat("quantity");
    assertEquals(oldQuantity + additionalQuantity, actualQuantity);
  }

  /**
   * Test whether adding a new ingredient works by asserting that the ingredient added is in the
   * inventory.
   */
  @Test
  void testAddNewIngredient() {
    Inventory.addNewIngredient("Apple", 2);
    assertTrue(Inventory.ingredientExistsInInventory("Apple"));
  }

  /**
   * Test whether adding a new ingredient to the inventory works by asserting that the ingredient
   * added is in the inventory.
   */
  @Test
  @DisplayName("selectIngredientIdFromName selects the correct ID")
  void testSelectIngredientIdFromName() {
    String ingredientName = "Apple";
    ResultSet rs = Inventory.selectIngredientIdFromName(ingredientName);
    int ingredientId = 0;
    try {
      if (rs.next()) {
        ingredientId = rs.getInt("ingredient_id");
      }
    } catch (SQLException e) {
      fail(e.getMessage());
    }
    assertEquals(1, ingredientId);
  }

  /**
   * Test whether searching ingredients in the inventory works by asserting that the ingredient
   * with the given name is found.
   */
  @Test
  @DisplayName("searchIngredientsInInventory returns correct ingredients")
  void testSearchIngredientsInInventory() {
    String search = "sweet";
    ResultSet rs = Inventory.searchIngredientsInInventory(search);
    ArrayList<String> ingredients = new ArrayList<>();
    try {
      while (rs.next()) {
        ingredients.add(rs.getString("ingredient_id"));
      }
    } catch (SQLException e) {
      fail(e.getMessage());
    }
    assertEquals(1, ingredients.size());
    assertEquals("17", ingredients.get(0));
  }

  @Test
  @DisplayName("deleteLessThanZero deletes ingredients with quantity less than zero")
  void testDeleteLessThanZero() {
    Inventory.addNewIngredient("Pepperoni", -5);
    assertTrue(Inventory.ingredientExistsInInventory("Pepperoni"));
    Inventory.deleteLessThanZero();
    ResultSet rs = MainController.sqlConnector.executeSqlSelect(
        "SELECT * FROM inventory_ingredient LEFT JOIN ingredient i ON inventory_ingredient.ingredient_id = i.ingredient_id;");
    try {
      while (rs.next()) {
        if (rs.getString("ingredient_id").equals("18")) {

          fail("Ingredient with quantity " + rs.getFloat("quantity") + " should have been deleted");
        }
      }
    } catch (SQLException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Tear down the test environment by resetting the database after all tests.
   */
  @AfterAll
  static void tearDown() {
    sqlConnector.closeConnection();
    sqlConnector.resetTestDatabase();
  }
}