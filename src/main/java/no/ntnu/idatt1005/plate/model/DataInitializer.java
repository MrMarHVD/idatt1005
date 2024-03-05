package no.ntnu.idatt1005.plate.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    Ingredient eggs = new Ingredient("egg(s)", "pieces", "protein");
    Ingredient flour = new Ingredient("flour", "grams", "carbohydrate");
    Ingredient milk = new Ingredient("milk", "milliliters", "dairy");
    Ingredient sugar = new Ingredient("sugar", "grams", "carbohydrate");
    Ingredient butter = new Ingredient("butter", "grams", "dairy");
    Ingredient salt = new Ingredient("salt", "grams", "mineral");
    Ingredient yeast = new Ingredient("yeast", "grams", "leavening agent");
    Ingredient water = new Ingredient("water", "milliliters", "liquid");
    Ingredient oil = new Ingredient("oil", "milliliters", "fat");
    Ingredient cheese = new Ingredient("cheese", "grams", "dairy");
    Ingredient lettuce = new Ingredient("lettuce", "grams", "vegetable");
    Ingredient tomatoes = new Ingredient("tomatoes", "pieces", "fruit");
    Ingredient cucumbers = new Ingredient("cucumbers", "grams", "vegetable");
    Ingredient chickenBreast = new Ingredient("chicken breast", "grams", "protein");
    Ingredient beefSteak = new Ingredient("beef steak", "grams", "protein");
    Ingredient porkChops = new Ingredient("pork chops", "grams", "protein");
    Ingredient salmonFilet = new Ingredient("salmon filet", "grams", "protein");
    Ingredient basmatiRice = new Ingredient("basmati rice", "grams", "carbohydrate");
    Ingredient pennePasta = new Ingredient("penne pasta", "grams", "carbohydrate");
    Ingredient wholeWheatBread = new Ingredient("whole wheat bread", "grams", "carbohydrate");
    Ingredient russetPotatoes = new Ingredient("russet potatoes", "grams", "carbohydrate");
    Ingredient grannySmithApples = new Ingredient("granny smith apples", "grams", "fruit");
    Ingredient bananas = new Ingredient("bananas", "grams", "fruit");
    Ingredient navelOranges = new Ingredient("navel oranges", "grams", "fruit");
    Ingredient strawberries = new Ingredient("strawberries", "grams", "fruit");
    Ingredient broccoli = new Ingredient("broccoli", "grams", "vegetable");
    Ingredient carrots = new Ingredient("carrots", "grams", "vegetable");
    Ingredient peas = new Ingredient("peas", "grams", "vegetable");
    Ingredient spinach = new Ingredient("spinach", "grams", "vegetable");
    Ingredient oats = new Ingredient("oats", "grams", "carbohydrate");
    Ingredient bacon = new Ingredient("bacon", "grams", "protein");

    // Write ingredients to JSON
    JsonWriter.writeIngredientsToJson(Arrays.asList(
        eggs, flour, milk, sugar, butter, salt, yeast,
        water, oil, cheese, lettuce, tomatoes, cucumbers,
        chickenBreast, beefSteak, porkChops, salmonFilet,
        basmatiRice, pennePasta, wholeWheatBread, russetPotatoes,
        grannySmithApples, bananas, navelOranges, strawberries,
        broccoli, carrots, peas, spinach, oats, bacon
), "ingredients");
}

  /**
   * Method for creating recipes and writing them to JSON.
   */
  public static void createRecipes() {
    Recipe pancake = new Recipe(
            "Pancake",
            "Mix flour, eggs, milk, sugar, and salt. Fry in a pan.",
            Map.of(
                    1, 2,
                    2, 200,
                    3, 100,
                    4, 50
            )
    );

    Recipe scrambledEggs = new Recipe(
          "Scrambled Eggs",
          "Beat eggs in a bowl. Melt butter in a pan. Pour eggs into the pan and stir until cooked.",
          Map.of(
                  1, 4,
                  5, 30,
                  6, 5
          )
    );

  Recipe oatmeal = new Recipe(
          "Oatmeal",
          "Boil water. Add oats and reduce heat. Cook until water is absorbed. Add sugar to taste.",
          Map.of(
                  8, 500,
                  30, 100,
                  4, 10
          )
    );

    Recipe chickenSalad = new Recipe(
            "Chicken Salad",
            "Grill chicken breast. Serve with lettuce, tomatoes, and cucumbers.",
            Map.of(
                    14, 150, // chicken breast
                    11, 100, // lettuce
                    12, 2, // tomatoes
                    13, 100 // cucumbers
            )
    );

    Recipe pastaCarbonara = new Recipe(
          "Pasta Carbonara",
          "Cook penne pasta. In a separate pan, cook bacon with cheese and beaten eggs. Mix together and serve.",
          Map.of(
                  31, 200, // bacon
                  10, 100, // cheese
                  1, 2, // eggs
                  19, 200 // penne pasta
          )
    );

    Recipe steakAndPotatoes = new Recipe(
            "Steak and Potatoes",
            "Grill beef steak. Serve with russet potatoes.",
            Map.of(
                    15, 200, // beef steak
                    21, 200 // russet potatoes
            )
    );

  Recipe chickenStirFry = new Recipe(
        "Chicken Stir Fry",
        "Cut chicken breast into strips. Stir fry with vegetables in oil. Serve with basmati rice.",
        Map.of(
                14, 200, // chicken breast
                27, 100, // broccoli
                28, 100, // carrots
                9, 30, // oil
                18, 200 // basmati rice
        )
);

Recipe spaghettiBolognese = new Recipe(
        "Spaghetti Bolognese",
        "Cook penne pasta. In a separate pan, cook beef steak with tomatoes. Mix together and serve.",
        Map.of(
                15, 200, // beef steak
                12, 200, // tomatoes
                19, 200 // penne pasta
        )
);

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





    // Write recipes to JSON
    JsonWriter.writeRecipesToJson(Arrays.asList(pancake, scrambledEggs, oatmeal, chickenSalad, pastaCarbonara, steakAndPotatoes, chickenStirFry, spaghettiBolognese, salmonSalad), "recipes");
  }

  /**
   * Method for creating cookbooks and writing them to JSON.
   */
  public static void createCookBooks() {
    CookBook breakfast = new CookBook("Breakfast", 1, 2, 3 );
    CookBook lunch = new CookBook("Lunch", 4, 5, 6);
    CookBook dinner = new CookBook("Dinner", 7, 8, 9);
    JsonWriter.writeCookbookToJson(Arrays.asList(breakfast, lunch, dinner), "cookbooks");
  }

  public static void main(String[] args) {
    DataInitializer.createIngredients();
    DataInitializer.createCookBooks();
    DataInitializer.createRecipes();
  }

  /**
   * Method for creating shopping list items and writing them to JSON.
   */
  public static void createShoppingList() {
    ShoppingListItem item = new ShoppingListItem(2, 200);
    ShoppingListItem item2 = new ShoppingListItem(3, 300);
    ShoppingListItem item3 = new ShoppingListItem(4, 50);
    ShoppingListItem item4 = new ShoppingListItem(5, 30);


    JsonWriter.writeShoppingListToJson(Arrays.asList(item, item2, item3, item4));
  }

}
