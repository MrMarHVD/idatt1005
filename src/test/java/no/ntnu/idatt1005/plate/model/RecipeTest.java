package no.ntnu.idatt1005.plate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

  static Recipe pancake;
  static Recipe scrambledEggs;
  static Recipe oatmeal;


  @BeforeAll
  static void setUp() {
    pancake = new Recipe(
            "Pancake",
            "Mix flour, eggs, milk, sugar, and salt. Fry in a pan.",
            Map.of(
                    1, 2,
                    2, 200,
                    3, 100,
                    4, 50
            )
    );

    scrambledEggs = new Recipe(
            "Scrambled Eggs",
            "Beat eggs in a bowl. Melt butter in a pan. Pour eggs into the pan and stir until cooked.",
            Map.of(
                    1, 4,
                    5, 30,
                    6, 5
            )
    );

    oatmeal = new Recipe(
            "Oatmeal",
            "Boil water. Add oats and reduce heat. Cook until water is absorbed. Add sugar to taste.",
            Map.of(
                    8, 500,
                    30, 100,
                    4, 10
            )
    );

    //JsonWriter.writeRecipesToJson(Arrays.asList(pancake, scrambledEggs, oatmeal), "testRecipes");
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    void constructorShouldReturnExpectedValues() {
      Recipe salmonSalad = new Recipe(
              "Salmon Salad",
              "Grill salmon filet. Serve with lettuce, tomatoes, and cucumbers.",
              Map.of(
                      17, 150, // salmon filet
                      11, 100, // lettuce
                      12, 100, // tomatoes
                      13, 100 // cucumbers
              )
      );

      assertEquals("Salmon Salad", salmonSalad.getName());
      assertEquals("Grill salmon filet. Serve with lettuce, tomatoes, and cucumbers.", salmonSalad.getInstructions());
      assertTrue(salmonSalad.getIngredients().containsKey(17));
      assertTrue(salmonSalad.getIngredients().containsValue(150));
      assertEquals(4, salmonSalad.getId());
      assertEquals(150, salmonSalad.getAmountOfIngredient(17));
    }

    @Test
    void getNameShouldReturnPancake() {
      String pancakeName = pancake.getName();

      assertEquals("Pancake", pancakeName);
    }

    @Test
    void getIngredientsShouldContainKey1() {
      Map<Integer, Integer> pancakeIngredients = pancake.getIngredients();

      assertTrue(pancakeIngredients.containsKey(1));
    }

    @Test
    void getIngredientsShouldContainValue100() {
      Map<Integer, Integer> pancakeIngredients = pancake.getIngredients();

      assertTrue(pancakeIngredients.containsValue(100));
    }

    @Test
    void getIdShouldReturn1() {
      int pancakeId = pancake.getId();

      assertEquals(1, pancakeId);
    }

    @Test
    void getInstructionsShouldEqualText() {
      String pancakeInstructions = pancake.getInstructions();

      assertEquals("Mix flour, eggs, milk, sugar, and salt. Fry in a pan.", pancakeInstructions);
    }

    @Test
    void getAmountOfIngShouldEqual4() {
      int pancakeAmountOfIngredients = pancake.getAmountOfIngredient(1);

      assertEquals(2, pancakeAmountOfIngredients);
    }
  }
}