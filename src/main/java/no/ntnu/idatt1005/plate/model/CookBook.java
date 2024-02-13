package no.ntnu.idatt1005.plate.model;

/**
 * CookBook class represents a cook book containing recipes.
 */
public class CookBook {

  private static int nextId = 1;

  private final int id;
  private String name;
  private Integer[] recipes;

  /**
   * No-argument constructor for Jackson.
   */
  public CookBook() {
    this.id = nextId++;
  }

  /**
   * Constructor for cook book.
   *
   * @param recipeIds ids of the recipes in the cook book.
   */
  public CookBook(String name, Integer... recipeIds) {
    this.id = nextId++;
    this.name = name;
    this.recipes = recipeIds;
  }

  public Integer[] getRecipes() {
    return this.recipes;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}