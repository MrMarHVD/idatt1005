package no.ntnu.idatt1002.demo.model;

import java.util.ArrayList;

/**
 * Cookbook class manages a set of recipes.
 */
public class CookBook {

  private ArrayList<Recipe> recipes;

  public CookBook(Recipe... recipes) {
    this.recipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      this.recipes.add(recipe);
    }

  }
  public ArrayList<Recipe> getRecipes() { return this.recipes; }

  /**
   * Find the name of a recipe.
   * @param name name the recipe.
   * @return the recipe.
   */
  public Recipe findRecipe(String name) {
    for (Recipe recipe : this.recipes) {
      if (recipe.getName().equals(name)) {
        return recipe;
      }
    }
    return null;
  }

  public void addRecipe(Recipe recipe) {
    this.recipes.add(recipe);
  }

}
