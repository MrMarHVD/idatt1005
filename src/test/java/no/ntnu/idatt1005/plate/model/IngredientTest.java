package no.ntnu.idatt1005.plate.model;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class IngredientTest {

  static Ingredient eggs;
  static Ingredient flour;
  static Ingredient sugar;
  static Ingredient butter;

  @BeforeAll
  static void setUp() {
    eggs = new Ingredient("egg(s)", "pieces", "protein", "yolk");
    flour = new Ingredient("flour", "grams", "carbohydrate");
    sugar = new Ingredient("sugar", "grams", "carbohydrate");
    butter = new Ingredient("butter", "grams", "dairy");
    //JsonWriter.writeIngredientsToJson(Arrays.asList(
    //        eggs, flour, sugar, butter
    //), "testIngredients");
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    void constructorShouldReturnExpectedValues() {
      Ingredient milk = new Ingredient("milk", "milliliters", "dairy", "lactose");

      assertEquals("milk", milk.getName());
      assertEquals(5, milk.getId());
      assertEquals("milliliters", milk.getUnit());
      assertEquals("dairy", milk.getCategory());
      assertTrue(milk.getAllergens().contains("lactose"));
    }

    @Test
    void getNameShouldEqualEggs() {
      String eggName = eggs.getName();

      assertEquals("egg(s)", eggName);
    }

    @Test
    void getIdShouldReturn2() {
      int flourId = flour.getId();

      assertEquals(2,flourId);
    }

    @Test
    void getUnitShouldReturnGrams() {
      String sugarUnit = sugar.getUnit();

      assertEquals("grams", sugarUnit);
    }

    @Test
    void getCategoryShouldReturnDairy() {
      String butterCategory = butter.getCategory();

      assertEquals("dairy", butterCategory);
    }

    @Test
    void getAllergensShouldContainYolk() {
      List<String> eggAllergen = eggs.getAllergens();
      String expectedAllergen = "yolk";

      assertTrue(eggAllergen.contains(expectedAllergen));
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

  }
}