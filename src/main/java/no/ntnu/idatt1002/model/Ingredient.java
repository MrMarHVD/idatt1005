package no.ntnu.idatt1002.model;


/**
 * Class representing an ingredient for a recipe.
 */
public class Ingredient {

  /**
   * The name of the ingredient.
   */
  private final String name;

  /**
   * Constructor for ingredient.
   *
   * @param name name of the ingredient
   */
  public Ingredient(String name) {
    this.name = name;
  }

  /**
   * Get name of ingredient.
   * @return name of ingredient.
   */
  public String getName() {return this.name; }

}
