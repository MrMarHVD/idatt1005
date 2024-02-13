package no.ntnu.idatt1005.plate.model;

/**
 * Recipe class represents a recipe in a cook book.
 */
public class Recipe {

  private static int nextId = 1;

  private final int id;
  private Integer[] ingredients;
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
   * @param ingredientIds ids of the ingredients in the recipe.
   */
  public Recipe(String name, String instructions, Integer... ingredientIds) {
    this();
    this.name = name;
    this.instructions = instructions;
    this.ingredients = ingredientIds;
  }

  public String getName() {
    return this.name;
  }

  public Integer[] getIngredients() {
    return this.ingredients;
  }

  public int getId() {
    return this.id;
  }

  public String getInstructions() {
    return this.instructions;
  }
}