package no.ntnu.idatt1005.plate.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Recipe class.
 */
class RecipeTest {

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
    Recipe.setSqlConnector(sqlConnector);
    sqlConnector.resetTestDatabase();
  }

  /**
   * Test updateInstructions by adding new instructions and asserting them equal
   * to the instructions retrieved from the database.
   */
  @Test
  void testUpdateInstructions() {
    String recipeName = "Egg and Onion Scramble";
    String newInstructions = "Bake in oven for 20 minutes";
    Recipe.updateInstructions(recipeName, newInstructions);
    assertEquals(newInstructions, Recipe.getInstructions(recipeName));
  }

  /**
   * Test getInstructions by asserting that the instructions are not null.
   */
  @Test
  void testGetInstructions() {
    String recipeName = "Egg and Onion Scramble";
    String instructions = Recipe.getInstructions(recipeName);
    assertNotNull(instructions);
  }

  /**
   * Test getInstructions negatively by asserting that the instructions are null for a non-existent
   * recipe.
   */
  @Test
  void testGetInstructionsForNonExistentRecipe() {
    String recipeName = "NonExistentRecipe";
    String instructions = Recipe.getInstructions(recipeName);
    assertNull(instructions, "Instructions for a non-existent recipe should be null");
  }

  /**
   * Test deleteRecipe by asserting that the instructions are null after deleting the recipe.
   */
  @Test
  void testDeleteRecipe() {
    String recipeName = "Egg and Onion Scramble";
    Recipe.deleteRecipe(recipeName);
    assertNull(Recipe.getInstructions(recipeName));
  }

  /**
   * Test createRecipe by asserting that the instructions are not null after creating a recipe.
   */
  @Test
  void testCreateRecipe() {
    String recipeName = "Burger";
    Recipe.createRecipe(recipeName);
    Recipe.updateInstructions("Burger", "Test");
    assertNotNull(Recipe.getInstructions(recipeName));
  }

  /**
   * Test getIngredientUnit by asserting that the unit is not null.
   */
  @Test
  void testGetIngredientUnit() {
    String ingredientName = "Cheese";
    String unit = Recipe.getIngredientUnit(ingredientName);
    assertNotNull(unit);
  }

  /**
   * Test getIngredientUnit negatively by asserting the unit obtained equal
   * to null for a non-existent ingredient.
   */
  @Test
  void testGetIngredientUnitForNonExistentIngredient() {
    String ingredientName = "NonExistentIngredient";
    String unit = Recipe.getIngredientUnit(ingredientName);
    assertNull(unit, "Unit for a non-existent ingredient should be null");
  }

  /**
   * Test selectIngredientsInRecipe by asserting that the selected ingredient ResultSet is
   * not null.
   */
  @Test
  void testSelectIngredientsInRecipe() {
    String recipeName = "Egg and Onion Scramble";
    ResultSet ingredients = Recipe.selectIngredientsInRecipe(recipeName);
    assertNotNull(ingredients);
  }

  /**
   * Test selectIngredientsInRecipe negatively by asserting that getting the ingredient at
   * index 0 of the ResultSet throws an SQLException.
   */
  @Test
  void testSelectIngredientsInNonExistentRecipe() {
    String recipeName = "NonExistentRecipe";
    ResultSet ingredients = Recipe.selectIngredientsInRecipe(recipeName);
    assertThrows(SQLException.class, () -> ingredients.getString(0));
  }

  /**
   * Test getRecipeIdFromName by asserting that the recipeId is greater than 0.
   */
  @Test
  void testGetRecipeIdFromName() {
    String recipeName = "Egg and Onion Scramble";
    int recipeId = Recipe.getRecipeIdFromName(recipeName);
    assertTrue(recipeId > 0);
  }

  /**
   * Test getRecipeIdFromNonExistentRecipeName by asserting the resulting ID
   * equal to 0.
   */
  @Test
  void testGetRecipeIdFromNonExistentRecipeName() {
    String recipeName = "NonExistentRecipe";
    int recipeId = Recipe.getRecipeIdFromName(recipeName);
    assertEquals(0, recipeId, "Recipe ID for a non-existent recipe should be 0");
  }

  /**
   * Test addIngredientToRecipe by asserting that the ingredient is added to the recipe.
   */
  @Test
  @DisplayName("addIngredientToRecipe adds ingredient to recipe")
  void testAddIngredientToRecipe() {
    String recipeName = "Egg and Onion Scramble";
    String ingredientName = "Cheese";
    int ingredientId = 19;
    float quantity = 1.0f;
    Recipe.addIngredientToRecipe(recipeName, ingredientName, quantity);
    ResultSet ingredients = Recipe.selectIngredientsInRecipe(recipeName);
    boolean found = false;
    try {
      while (ingredients.next()) {
        if (ingredients.getInt("ingredient_id") == ingredientId) {
          found = true;
        }
      }

      assertTrue(found);
    } catch (SQLException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test removeIngredientFromRecipe by asserting that the ingredient is removed from the recipe.
   */
  @Test
  @DisplayName("removeIngredientFromRecipe removes ingredient from recipe")
  void testRemoveIngredientFromRecipe() {
    String recipeName = "Egg and Onion Scramble";
    int ingredientId = 6;
    Recipe.removeIngredientFromRecipe(recipeName, ingredientId);
    ResultSet ingredients = Recipe.selectIngredientsInRecipe(recipeName);
    boolean found = false;
    try {
      while (ingredients.next()) {
        if (ingredients.getInt("ingredient_id") == ingredientId) {
          found = true;
        }
      }
      assertFalse(found);
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