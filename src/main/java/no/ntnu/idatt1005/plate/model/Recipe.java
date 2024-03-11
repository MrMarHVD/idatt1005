package no.ntnu.idatt1005.plate.model;

import java.util.Map;

/**
 * Recipe class represents a recipe in a cook book.
 */
public class Recipe {

  private static int nextId = 1;

  private final int id;
  private Map<Integer, Integer> ingredients; // Key: ingredient id, Value: amount
  private String instructions;
  private String name;

  /**
   * No-argument constructor for Jackson.
   */
  public Recipe() {
    this.id = nextId++;
  }

  /**
   * Constructor for recipe.
   *
   * @param name         name of the recipe.
   * @param instructions instructions for the recipe.
   * @param ingredients ingredients in the recipe.
   */
  public Recipe(String name, String instructions, Map<Integer, Integer> ingredients) {
    this();
    this.name = name;
    this.instructions = instructions;
    this.ingredients = ingredients;
  }

  public String getName() {
    return this.name;
  }

  public Map<Integer, Integer> getIngredients() {
    return this.ingredients;
  }

  public int getId() {
    return this.id;
  }

  public String getInstructions() {
    return this.instructions;
  }

  public Integer getAmountOfIngredient(int ingredientId) {
    return this.ingredients.get(ingredientId);
  }
}