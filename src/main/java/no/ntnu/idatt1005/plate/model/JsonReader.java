package no.ntnu.idatt1005.plate.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
* Class for reading objects from JSON files.
*/
public class JsonReader {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private JsonReader() {
  }

  /**
   * Reads a list of ingredients from a JSON file.
   *
   *
   * @param filePath path to the JSON file.
   * @param typeReference type reference for the list of ingredients.
   * @return list of ingredients.
   */
  private static <T> List<T> readValueFromFile(
          String filePath,
          TypeReference<List<T>> typeReference
  ) {
    try {
      return objectMapper.readValue(new File(filePath), typeReference);
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  /**
   * Gets a single ingredient by its id.
   *
   *
   * @return ingredient with the given id, or null if not found.
   */
  public static Ingredient getIngredientById(int id) {
    List<Ingredient> ingredients = readValueFromFile(
            "src/main/resources/json/ingredients.json",
            new TypeReference<>() {}
    );
    return ingredients.stream().filter(ingredient -> ingredient.getId() == id)
            .findFirst().orElse(null);
  }

  // JsonReader.java

  /**
   * Gets all ingredients from the inventory.
   *
   *
   * @return list of all ingredients.
   */
  public static List<Ingredient> getInventoryIngredients() {
      try {
          ObjectMapper objectMapper = new ObjectMapper();
          File file = new File("src/main/resources/json/inventory.json");
          return objectMapper.readValue(file, new TypeReference<List<Ingredient>>(){});
      } catch (IOException e) {
          e.printStackTrace();
          return Collections.emptyList();
      }
  }


  /**
   * Gets a single recipe by its id.
   *
   *
   * @return recipe with the given id, or null if not found.
   */
  public static Recipe getRecipeById(int id) {
    List<Recipe> recipes = readValueFromFile(
            "src/main/resources/json/recipes.json",
            new TypeReference<>() {}
    );
    return recipes.stream().filter(recipe -> recipe.getId() == id).findFirst().orElse(null);
  }

  /**
   * Gets a single cookbook by its id.
   *
   *
   * @return cookbook with the given id, or null if not found.
   */
  public static CookBook getCookBookById(int id) {
    List<CookBook> cookBooks = readValueFromFile(
            "src/main/resources/json/cookbooks.json",
            new TypeReference<>() {}
    );
    return cookBooks.stream().filter(cookBook -> cookBook.getId() == id).findFirst().orElse(null);
  }



}