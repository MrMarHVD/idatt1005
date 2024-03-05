package no.ntnu.idatt1005.plate.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class for writing objects to JSON files.
 */
public class JsonWriter {

  private JsonWriter() {
  }

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  /**
   * Writes a list of cookbooks to a JSON file.
   *
   * @param cookbooks list of cookbooks to write to file.
   */
  public static void writeCookbookToJson(List<CookBook> cookbooks) {
    try {
      objectMapper.writeValue(new File("src/main/resources/cookbooks.json"), cookbooks);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
    * Writes a list of recipes to a JSON file.
    *
    * @param recipes list of recipes to write to file.
    */
  public static void writeRecipesToJson(List<Recipe> recipes) {
    try {
      objectMapper.writeValue(new File("src/main/resources/recipes.json"), recipes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
    * Writes a list of ingredients to a JSON file.
    *
    * @param ingredients list of ingredients to write to file.
    */
  public static void writeIngredientsToJson(List<Ingredient> ingredients) {
    try {
      objectMapper.writeValue(new File("src/main/resources/ingredients.json"), ingredients);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
    * Writes a list of shopping list items to a JSON file.
    *
    * @param shoppingList list of shopping list items to write to file.
    */
  public static void writeShoppingListToJson(List<ShoppingListItem> shoppingList) {
    try {
      objectMapper.writeValue(new File("src/main/resources/json/shoppingList.json"), shoppingList);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
    * Writes a single shopping list item to a JSON file.
    *
    * @param item shopping list item to write to file.
    */
  public static void writeShoppingListItemToJson(ShoppingListItem item) {
    List<ShoppingListItem> shoppingList = JsonReader.getShoppingList();
    shoppingList.add(item);
    writeShoppingListToJson(shoppingList);
  }

}
