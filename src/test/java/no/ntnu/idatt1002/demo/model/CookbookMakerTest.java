package no.ntnu.idatt1002.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookbookMakerTest {

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    void cookBookMakerShouldBe3inSize() {
      CookBook cookBook = CookbookMaker.createCookBook();

      assertEquals(3, cookBook.getRecipes().size(), "Size should be equal to 3");
    }

    @Test
    void cookBookMakerShouldReturnValidRecipes() {
      CookBook cookBook = CookbookMaker.createCookBook();

      assertNotNull(cookBook.findRecipe("Pizza"));
      assertNotNull(cookBook.findRecipe("Pasta"));
      assertNotNull(cookBook.findRecipe("Salad"));
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    void cookBookMakerShouldReturnNullWithInvalidRecipe() {
      CookBook cookBook = CookbookMaker.createCookBook();

      assertNull(cookBook.findRecipe("Steak"));
    }
  }
}