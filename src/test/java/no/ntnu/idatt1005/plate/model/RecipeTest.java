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
   * The name of the database file used for testing.
   */
  private static final String dbFileName = "recipe_test.db";

  /**
   * The SQL connector used for testing.
   */
  private static final SqlConnector sqlConnector = new SqlConnector(dbFileName);

  /**
   * Set up the test environment by resetting the database before each test.
   */
  @BeforeEach
  void setUp() {
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
   * Test getRecipeIdFromName by asserting that the recipeId is greater than 0.
   */
  @Test
  void testGetRecipeIdFromName() {
    String recipeName = "Egg and Onion Scramble";
    int recipeId = Recipe.getRecipeIdFromName(recipeName);
    assertTrue(recipeId > 0);
  }

  // Your test methods go here

  /**
   * Tear down the test environment by resetting the database after all tests.
   */
  @AfterAll
  static void tearDown() {
    sqlConnector.resetTestDatabase();
    try {
      Files.deleteIfExists(Paths.get("src/main/resources/" + dbFileName));
      System.out.println(dbFileName + " deleted successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}