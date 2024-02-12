package no.ntnu.idatt1005.plate.model;

import java.util.List;


/**
 * Class representing an ingredient for a recipe.
 */
public class Ingredient {

  /**
   * The name of the ingredient.
   */
  private final String name;

  /**
   * The allergens in this ingredient.
   */
  private List<String> allergens;

  private String category;

  /**
   * Constructor for ingredient.
   *
   * @param name name of the ingredient.
   */
  public Ingredient(String name) {
    this.name = name;
  }

  /**
   * Parameterised constructor for ingredient.
   *
   * @param name name of the ingredient
   */
  public Ingredient(String name, String category, String... allergens) {

    this.name = name;
    this.category = category;
    this.allergens = List.of(allergens);

    }

  /**
   * Get name of ingredient.
   * @return name of ingredient.
   */
  public String getName() {return this.name; }

}
