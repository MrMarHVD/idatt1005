package no.ntnu.idatt1002.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookBookTest {

  private CookBook cookBook;
  private Recipe cakeRecipe;
  private Recipe breadRecipe;

  Ingredient flour = new Ingredient("Flour");
  Ingredient sugar = new Ingredient("Sugar");
  Ingredient milk = new Ingredient("Milk");
  Ingredient water = new Ingredient("Water");
  Ingredient yeast = new Ingredient("Yeast");

  @BeforeEach
  void setUp() {
    cakeRecipe = new Recipe("Cake", "Add ingredients together", flour, sugar, milk);
    breadRecipe = new Recipe("Bread", "Make a dough", flour, water, yeast);
    cookBook = new CookBook(cakeRecipe);
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    void addRecipeShouldAddRecipe() {
      cookBook.addRecipe(breadRecipe);

      assertTrue(cookBook.getRecipes().contains(breadRecipe), "Cook book should contain breadRecipe");
    }

    @Test
    void getRecipesShouldEqualExpectedSize() {
      assertEquals(1, cookBook.getRecipes().size(), "Cook book size should equal 1");
      cookBook.addRecipe(breadRecipe);
      assertEquals(2, cookBook.getRecipes().size(), "Cook book size should have increased to 2");
    }


    @Test
    void findRecipeShouldReturnRecipe() {
      assertEquals(cakeRecipe, cookBook.findRecipe("Cake"), "The recipe should equal cakeRecipe");
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    void findRecipeShouldReturnNullWithInvalidName() {

      assertNull(cookBook.findRecipe("InvalidName"), "Should return nothing");
    }
  }
}