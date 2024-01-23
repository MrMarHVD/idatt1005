package no.ntnu.idatt1002.demo.model;

import java.util.Arrays;
import java.util.ArrayList;

public class Recipe {

  ArrayList<Ingredient> ingredients;

  String instructions;

  /**
   * Constructor for recipe.
   *
   * @param ingredients ingredients in the recipe.
   */
  public Recipe(String instructions, Ingredient firstIngredient, Ingredient... ingredients) {

    this.ingredients = new ArrayList<Ingredient>();

    this.ingredients.add(firstIngredient);
    this.ingredients.addAll(Arrays.asList(ingredients));

    this.instructions = instructions;
  }

  /**
   * toString-method returns the ingredients and instructions as Strings.
   *
   * @return ingredients and instructions as strings.
   */
  @Override
  public String toString() {

    ArrayList<String> ingredientsStrings = new ArrayList<>();
    for (Ingredient ingredient : this.ingredients) {
      ingredientsStrings.add(ingredient.getName());
    }
    return "Recipe{" +
        "ingredients=" + ingredientsStrings +
        ", instructions='" + instructions + '\'' +
        '}';
  }
}
