package no.ntnu.idatt1002.demo.model;

/**
 * Temporary class for instantiating some recipes. This will not be part of the final app.
 */
public class CookbookMaker {

  /**
   * Create a cookbook with some recipes.
   * @return a cookbook with some recipes.
   */
  public static CookBook createCookBook() {
    Recipe recipe1 = new Recipe("Pasta", "Cook pasta", new Ingredient("Pasta"), new Ingredient("Tomato sauce"));
    Recipe recipe2 = new Recipe("Pizza", "Cook pizza", new Ingredient("Pizza dough"), new Ingredient("Tomato sauce"), new Ingredient("Cheese"));
    Recipe recipe3 = new Recipe("Salad", "Cook salad", new Ingredient("Salad"), new Ingredient("Tomato"), new Ingredient("Cucumber"));
    CookBook cookBook = new CookBook(recipe1, recipe2, recipe3);
    return cookBook;
  }

}
