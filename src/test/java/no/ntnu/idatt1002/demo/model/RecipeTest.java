package no.ntnu.idatt1002.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
  Ingredient flour = new Ingredient("Flour");
  Ingredient sugar = new Ingredient("Sugar");
  Ingredient milk = new Ingredient("Milk");

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    void getNameShouldReturnCake() {
      Recipe cakeRecipe = new Recipe("Cake", "Add it all together",flour, sugar, milk);

      assertEquals("Cake", cakeRecipe.getName(), "Test should equal cake");
    }

    @Test
    void toStringTest() {
      Recipe cakeRecipe = new Recipe("Cake", "Add it all together",flour, sugar, milk);

      String expected = "Recipe{ingredients=[Flour, Sugar, Milk], instructions='Add it all together'}";

      assertEquals(expected, cakeRecipe.toString(), "toString should equal expected");
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    void getNameShouldNotEqualBread() {
      Recipe cakeRecipe = new Recipe("Cake", "Add it all together",flour, sugar, milk);

      assertNotEquals("Bread", cakeRecipe.getName(), "Test should not equal bread");
    }

  }

}