package no.ntnu.idatt1005.plate.model;

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
   * The name of the database file used for testing.
   */
  private static final String dbFileName = "calendar_test.db";

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
  private static final SqlConnector sqlConnector = new SqlConnector(dbFileName);

  /**
   * The calendar used for testing.
   */
  private static Calendar calendar = new Calendar(sqlConnector);

  /**
   * Set up the test environment.
   */
  @BeforeEach
  void setUp() {
    sqlConnector.resetTestDatabase();
    date = Date.valueOf(LocalDate.now());
    date1 = Date.valueOf(LocalDate.now().plusDays(1));
    calendar = new Calendar(sqlConnector);
  }

  /**
   * Test the constructor.
   */
  @Test
  @DisplayName("constructor test")
  void testConstructor() {
    //assert
    assertNotNull(calendar);
  }

  /**
   * Test the insert day method.
   */
  @Test
  @DisplayName("insert day test")
  void testInsertDay() {

    //act
    calendar.insertDay(date, false);

    //assert
  assertTrue(calendar.dayExists(date));
  }

  /**
   * Test the day exists method by asserting the result false when a day not found in the calendar
   * is used as an argument.
   */
  @Test
  @DisplayName("day exists negative test")
  void testDayExistsNegative() {
    //act
    boolean exists = calendar.dayExists(date1);

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
    String recipe = calendar.getRecipe(date);

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
    String recipe = calendar.getRecipe(date1);

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
    String newRecipe = "Margherita Pizza";

    //act
    calendar.changeRecipe(date, newRecipe);

    //assert
    assertEquals(newRecipe, calendar.getRecipe(date));
  }

  /**
   * Test the get day recipes method by asserting the recipe obtained is equal to the recipe in the
   * calendar.
   */
  @Test
  @DisplayName("get day recipes test")
  void testGetDayRecipes() {

    //act
    String recipe = calendar.getRecipe(date);

    //assert
    assertEquals(recipe, calendar.getDayRecipes().get(date.toString()));
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
    String recipe = calendar.searchRecipes(search, false).get(0);

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
    int size = calendar.searchRecipes(search, false).size();

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
    calendar.removeDay(date);

    //assert
    assertFalse(calendar.dayExists(date));
  }

  /**
   * Test the get missing ingredients method by asserting the result not equal to null.
   */
  @Test
  @DisplayName("get missing ingredients test")
  void testGetMissingIngredients() {
    //arrange

    //act
    String missingIngredients = calendar.getMissingIngredients("Margherita Pizza").toString();

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
    String missingIngredients = calendar.getMissingIngredientsWithQuantity("Margherita Pizza",
        2).toString();

    //assert
    assertNotNull(missingIngredients);
  }

  /**
   * Clean up after all tests have been run.
   */
  @AfterAll
  static void cleanUp() {
    try {
      Files.deleteIfExists(Paths.get("src/main/resources/" + dbFileName));
      System.out.println(dbFileName + " deleted successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}