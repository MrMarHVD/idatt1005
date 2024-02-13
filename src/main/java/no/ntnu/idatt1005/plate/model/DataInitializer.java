package no.ntnu.idatt1005.plate.model;

import java.util.Arrays;

/**
 * Class for creating data to be written to JSON.
 * Only used to set up initial data, not in the final application.
 */
public class DataInitializer {

  private DataInitializer() {
  }


  /**
   * Method for creating ingredients and writing them to JSON.
   */
  public static void createIngredients() {
    Ingredient eggs = new Ingredient("eggs");
    Ingredient flour = new Ingredient("flour");
    Ingredient milk = new Ingredient("milk");
    Ingredient sugar = new Ingredient("sugar");
    Ingredient butter = new Ingredient("butter");
    Ingredient salt = new Ingredient("salt");
    Ingredient yeast = new Ingredient("yeast");
    Ingredient water = new Ingredient("water");
    Ingredient oil = new Ingredient("oil");
    Ingredient cheese = new Ingredient("cheese");
    Ingredient lettuce = new Ingredient("lettuce");
    Ingredient tomatoes = new Ingredient("tomatoes");
    Ingredient cucumbers = new Ingredient("cucumbers");
    Ingredient vegetables = new Ingredient("vegetables");

    // Write ingredients to JSON
    JsonWriter.writeIngredientsToJson(Arrays.asList(
            eggs, flour, milk, sugar, butter, salt, yeast,
            water, oil, cheese, lettuce, tomatoes, cucumbers, vegetables
    ));
  }

  /**
   * Method for creating recipes and writing them to JSON.
   */
  public static void createRecipes() {
    Recipe pancake = new Recipe(
            "Pancake",
            "Mix flour, eggs, milk, sugar, and salt. Fry in a pan.",
            2, 1, 3, 4, 5
    );
    Recipe bread = new Recipe(
            "Bread",
            "Mix flour, yeast, water, and salt. Let it rise. Bake in the oven.",
            2, 6, 7, 5
    );
    Recipe salad = new Recipe(
            "Salad",
            "Mix lettuce, tomatoes, cucumbers, and cheese. Add oil and salt.",
            10, 11, 12, 9, 5
    );


    // Write recipes to JSON
    JsonWriter.writeRecipesToJson(Arrays.asList(pancake, bread, salad));
  }

  /**
   * Method for creating cookbooks and writing them to JSON.
   */
  public static void createCookBooks() {
    CookBook cookBook1 = new CookBook("Breakfast", 1, 2);
    CookBook cookBook2 = new CookBook("Lunch", 3);
    JsonWriter.writeCookbookToJson(Arrays.asList(cookBook1, cookBook2));
  }


}
