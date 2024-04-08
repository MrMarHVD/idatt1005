package no.ntnu.idatt1005.plate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CookBookTest {

  static CookBook breakfast;
  static CookBook lunch;


  @BeforeAll
  static void setUp() {
    //breakfast = new CookBook("Breakfast", 1, 2, 3 );
    //lunch = new CookBook("Lunch", 4, 5, 6);
    //JsonWriter.writeCookbookToJson(Arrays.asList(breakfast, lunch), "TestCookbooks");
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    void constructorShouldReturnExpectedValues() {
      CookBook dinner = new CookBook("Dinner", 7, 8, 9);

      assertEquals("Dinner", dinner.getName());
      assertEquals(3, dinner.getId());
      assertEquals(3, dinner.getRecipes().length);
      assertEquals("[7, 8, 9]", Arrays.toString(dinner.getRecipes()));
    }

    @Test
    void getRecipesShouldReturnExpectedList() {
      String breakfastRecipes = Arrays.toString(breakfast.getRecipes());

      assertEquals("[1, 2, 3]", breakfastRecipes);
    }

    @Test
    void getIdShouldReturn1() {
      int breakfastId = breakfast.getId();

      assertEquals(1, breakfastId);
    }

    @Test
    void getNameShouldReturnBreakfast() {
      String breakfastName = breakfast.getName();

      assertEquals("Breakfast", breakfastName);
    }
  }
}