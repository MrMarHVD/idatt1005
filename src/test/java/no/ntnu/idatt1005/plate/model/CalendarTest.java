package no.ntnu.idatt1005.plate.model;

import java.util.Map;
import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Calendar class.
 */
class CalendarTest {

  /**
   * Date used for testing: the current date.
   */
  private static Date date = Date.valueOf(LocalDate.now());

  /**
   * Second date used for testing: tomorrow's date.
   */
  private static Date date1 = Date.valueOf(LocalDate.now().plusDays(1));

  /**
   * The SQL connector used for testing.
   */
  private static final SqlConnector sqlConnector = new SqlConnector("memory");

  /**
   * Set up the test environment.
   */
  @BeforeEach
  void setUp() {
    sqlConnector.closeConnection();
    sqlConnector.resetTestDatabase();
    Calendar.setSqlConnector(sqlConnector);
    date = Date.valueOf(LocalDate.now());
    date1 = Date.valueOf(LocalDate.now().plusDays(1));
  }

  /**
   * Test the insert day method.
   */
  @Test
  @DisplayName("insert day test")
  void testInsertDay() {

    //act
    Calendar.insertDay(Date.valueOf(LocalDate.now().plusDays(7)), false);

    //assert
    assertTrue(Calendar.dayExists(date));
  }

  /**
   * Test the day exists method by asserting the result false when a day not found in the calendar
   * is used as an argument.
   */
  @Test
  @DisplayName("day exists negative test")
  void testDayExistsNegative() {
    //act
    boolean exists = Calendar.dayExists(Date.valueOf(LocalDate.now().plusDays(10)));

    //assert
    assertFalse(exists);
  }

  /**
   * Test the day exists method by asserting the recipe obtained not equal to null.
   */
  @Test
  @DisplayName("get recipe test")
  void testGetRecipe() {

    //act
    String recipe = Calendar.getRecipe(date);

    //assert
    assertNotNull(recipe);
  }

  /**
   * Test the get recipe method by asserting the result null when a day not found in the calendar.
   */
  @Test
  @DisplayName("get recipe negative test")
  void testGetRecipeNegative() {
    //act
    String recipe = Calendar.getRecipe(Date.valueOf(LocalDate.now().plusDays(10)));

    //assert
    assertNull(recipe);
  }

  /**
   * Test the change recipe method by asserting the new recipe is equal to the recipe obtained.
   */
  @Test
  @DisplayName("change recipe test")
  void testChangeRecipe() {
    //arrange
    String newRecipe = "Spicy Pepperoni Pizza";

    //act
    Calendar.changeRecipe(date, newRecipe);

    //assert
    assertEquals(newRecipe, Calendar.getRecipe(date));
  }

  /**
   * Test the get day recipes method by asserting the recipe obtained is equal to the recipe in the
   * calendar.
   */
  @Test
  @DisplayName("get day recipes test")
  void testGetDayRecipes() {

    //act
    String recipe = Calendar.getRecipe(date);

    //assert
    assertEquals(recipe, Calendar.getDayRecipes().get(date.toString()));
  }

  /**
   * Test the search recipes method by asserting the recipe obtained contains the search string.
   */
  @Test
  @DisplayName("search recipes test")
  void testSearchRecipes() {
    //arrange
    String search = "Pizza";

    //act
    String recipe = Calendar.searchRecipes(search, false).get(0);

    //assert
    assertTrue(recipe.contains(search));
  }

  /**
   * Test the search recipes method by asserting the size of the result is 0 when the search string
   * does not match any recipe.
   */
  @Test
  @DisplayName("search recipes negative test")
  void testSearchRecipesNegative() {
    //arrange
    String search = "Nonexistent";

    //act
    int size = Calendar.searchRecipes(search, false).size();

    //assert
    assertEquals(0, size);
  }

  /**
   *
   */
  @Test
  @DisplayName("remove day test")
  void testRemoveDay() {

    //act
    Calendar.removeDay(date);

    //assert
    assertFalse(Calendar.dayExists(date));
  }

  /**
   * Test the get missing ingredients method by asserting the result not equal to null.
   */
  @Test
  @DisplayName("get missing ingredients test")
  void testGetMissingIngredients() {
    //arrange

    //act
    String missingIngredients = Calendar.getMissingIngredients("Margherita Pizza").toString();

    //assert
    assertNotNull(missingIngredients);

  }

  /**
   * Test the get missing ingredients with quantity method by asserting the result not equal to null.
   */
  @Test
  @DisplayName("get missing ingredients with quantity test")
  void testGetMissingIngredientsWithQuantity() {
    //arrange

    //act
    String missingIngredients = Calendar.getMissingIngredientsWithQuantity("Margherita Pizza",
        2).toString();

    //assert
    assertNotNull(missingIngredients);
  }


  @Test
  @DisplayName("getIngredientsAndQuantity returns correct values")
  void testGetIngredientsAndQuantity() {
    //arrange
    String recipe = "Margherita Pizza";
    int portion = 1;

    //act
    Map<Integer, Float> ingredientsAndQuantity = Calendar.getIngredientsAndQuantity(recipe, portion);
    //assert
    assertNotNull(ingredientsAndQuantity);
    assertEquals(3, ingredientsAndQuantity.size());
    assertEquals("{19=1.0, 20=1.0, 21=0.5}", ingredientsAndQuantity.toString());

  }

  @Test
  @DisplayName("getMissingIngredientsFromMap returns correct values")
  void testGetMissingIngredientsFromMap() {
    //arrange
    Map<Integer, Float> ingredientsAndQuantity = Map.of(19, 1.0f, 20, 1.0f, 21, 0.5f);

    //act
    String missingIngredients = Calendar.getMissingIngredientsFromMap(ingredientsAndQuantity).toString();

    //assert
    assertNotNull(missingIngredients);
    assertEquals("{19=1.0, 20=1.0, 21=0.5}", missingIngredients);
  }

  /**
   * Clean up after all tests have been run.
   */
  @AfterAll
  static void tearDown() {
    sqlConnector.closeConnection();
    sqlConnector.resetTestDatabase();
  }
}