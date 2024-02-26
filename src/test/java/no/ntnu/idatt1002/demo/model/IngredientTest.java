package no.ntnu.idatt1002.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    void constructorShouldReturnExpectedValues() {
      Ingredient ingredient = new Ingredient("Bread", "Wheat product", "Gluten", "Protein");

      assertEquals("Bread", ingredient.getName());
      // Add getCategory & getAllergens

    }
    @Test
    void getNameShouldEqualMilk() {
      Ingredient ingredient = new Ingredient("Milk");

      assertEquals("Milk", ingredient.getName(), "Test should equal Milk");
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    void getNameShouldNotEqualWater() {
      Ingredient ingredient = new Ingredient("Milk");

      assertNotEquals("Water", ingredient.getName(), "Test should not equal Milk");
    }

  }


}