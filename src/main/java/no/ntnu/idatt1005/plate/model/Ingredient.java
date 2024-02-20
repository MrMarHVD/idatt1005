package no.ntnu.idatt1005.plate.model;

import java.util.List;


/**
 * Ingredient class represents an ingredient in a recipe.
 */
public class Ingredient {

  private static int nextId = 1;

  private final int id;
  private String name;
  private List<String> allergens;
  private String category;
  private String unit;

  /**
   * No-argument constructor for Jackson.
   */
  public Ingredient() {
    this.id = nextId++;
  }

  /**
   * Constructor for ingredient.
   *
   * @param name name of the ingredient.
   */
  public Ingredient(String name) {
    this();
    this.name = name;
  }

  /**
   * Constructor for ingredient, including unit.
   *
   * @param name name of the ingredient.
   * @param unit unit of measurement for the ingredient.
   */
  public Ingredient(String name, String unit) {
    this(name);
    this.unit = unit;
  }

  /**
   * Constructor for ingredient, including category and allergens.
   *
   * @param name      name of the ingredient.
   * @param category  category of the ingredient.
   * @param allergens allergens in the ingredient.
   */
  public Ingredient(String name, String unit, String category, String... allergens) {
    this(name, unit);
    this.category = category;
    this.allergens = List.of(allergens);
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public List<String> getAllergens() {
    return this.allergens;
  }

  public String getCategory() {
    return this.category;
  }

  public String getUnit() {
    return this.unit;
  }
}