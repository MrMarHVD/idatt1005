package no.ntnu.idatt1005.plate.model;

/**
 * Class for shopping list items.
 */
public class ShoppingListItem {

  private static int nextId = 1;

  private final int id;

  private int ingredientId;

  private int amount;

  /**
   * No-argument constructor for Jackson.
   */
  public ShoppingListItem() {
    this.id = nextId++;
  }

  /**
   * Constructor for shopping list item.
   *
   * @param ingredientId id of the ingredient.
   * @param amount amount of the ingredient.
   */
  public ShoppingListItem(int ingredientId, int amount) {
    this();
    this.ingredientId = ingredientId;
    this.amount = amount;
  }

  public int getId() {
    return id;
  }

  public int getIngredientId() {
    return ingredientId;
  }

  public int getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "Add new code using database";
  }


}
