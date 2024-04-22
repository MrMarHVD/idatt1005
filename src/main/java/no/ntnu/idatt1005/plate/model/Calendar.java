package no.ntnu.idatt1005.plate.model;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.data.SqlConnector;

/**
 * This class handles the calendar functionality of the application.
 */
public class Calendar {

  /**
   * The SQL connector for this class
   */
  private static SqlConnector sqlConnector = MainController.sqlConnector;

  /**
   * Set the SQL connector to something other than that belonging to the MainController (testing).
   *
   * @param sqlConnector the SQL connector to assign.
   */
  public static void setSqlConnector(SqlConnector sqlConnector) {
    Calendar.sqlConnector = sqlConnector;
  }

  /**
   * This method checks if a day already exists in the database.
   *
   * @param date the date to check
   * @return true if the day exists, false if not
   */
  public static boolean dayExists(Date date) {
    String query = "SELECT * FROM day WHERE date = '" + date + "';";
    ResultSet rs = Calendar.sqlConnector.executeSqlSelect(query);

    try {
      if (rs != null && rs.next()) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * This method inserts a day into the database with a random recipe.
   *
   * @param date the date to insert
   */
  public static void insertDay(Date date, boolean vegetarian) {
    String selectQuery = "SELECT recipe_id FROM recipe ORDER BY ABS(RANDOM()) LIMIT 1;";
    if (vegetarian) {
      selectQuery = "SELECT recipe_id FROM recipe WHERE vegetarian = 1 "
          + "ORDER BY ABS(RANDOM()) LIMIT 1;";
    }
    ResultSet rs = Calendar.sqlConnector.executeSqlSelect(selectQuery);
    int recipeId = 0;
    try {
      if (rs != null) {
        recipeId = rs.getInt("recipe_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    String query = "INSERT INTO day(date, recipe_id) VALUES ('" + date + "', " + recipeId + ");";
    Calendar.sqlConnector.executeSqlUpdate(query);
  }

  /**
   * Method for removing a given day from the database.
   *
   * @param date the date to remove.
   */
  public static void removeDay(Date date) {
    String query = "DELETE FROM day WHERE date = '" + date + "';";
    sqlConnector.executeSqlUpdate(query);
  }

  /**
   * This method gets the recipes for each day in the day table.
   *
   * @return a map with the date as key and the recipe as value-
   */
  public static Map<String, String> getDayRecipes() {
    Map<String, String> dayRecipes = new HashMap<>();
    String selectQuery = "SELECT * FROM day JOIN recipe ON day.recipe_id = recipe.recipe_id";

    //SqlConnector sqlConnector = MainController.sqlConnector;
    ResultSet rs = Calendar.sqlConnector.executeSqlSelect(selectQuery);

    if (rs != null) {
      try {
        while (rs.next()) {
          String date = rs.getString("date");
          String recipe = rs.getString("name");
          dayRecipes.put(date, recipe);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.err.println("Error: ResultSet is null");
    }

    return dayRecipes;
  }

  /**
   * Get a recipe by a given date.
   *
   * @param date the date to search with.
   * @return the recipe name.
   */
  public static String getRecipe(Date date) {
    String recipe = "";
    String selectQuery = "SELECT * FROM day JOIN recipe ON day.recipe_id "
        + "= recipe.recipe_id WHERE date = '" + date + "';";
    ResultSet rs = Calendar.sqlConnector.executeSqlSelect(selectQuery);
    if (rs != null) {
      try {
        recipe = rs.getString("name");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.err.println("Error: ResultSet is null");
    }
    return recipe;
  }

  /**
   * Change the recipe for a given date to a new recipe.
   *
   * @param date the date to change the recipe for.
   * @param recipe the new recipe
   */
  public static void changeRecipe(Date date, String recipe) {
    String selectQuery = "SELECT recipe_id FROM recipe WHERE name = '" + recipe + "';";
    ResultSet rs = Calendar.sqlConnector.executeSqlSelect(selectQuery);
    int recipeId = 0;
    try {
      if (rs != null) {
        recipeId = rs.getInt("recipe_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    String updateQuery = "UPDATE day SET recipe_id = " + recipeId + " WHERE date = '" + date + "';";
    Calendar.sqlConnector.executeSqlUpdate(updateQuery);
  }

  /**
   * Search the database for recipes with a name that contains the search string.
   *
   * @param search the string to search the database for.
   * @return an arraylist containing the names of the recipes that match the search string.
   */

  public static ArrayList<String> searchRecipes(String search, boolean vegetarian) {

    ArrayList<String> recipes = new ArrayList<>();
    String selectQuery = "SELECT * FROM recipe WHERE name LIKE '%" + search + "%';";
    if (vegetarian) {
      selectQuery = "SELECT * FROM recipe WHERE name LIKE '%" + search + "%' AND vegetarian = 1;";
    }
    //SqlConnector sqlConnector = MainController.sqlConnector;
    ResultSet rs = Calendar.sqlConnector.executeSqlSelect(selectQuery);
    if (rs != null) {
      try {
        while (rs.next()) {
          recipes.add(rs.getString("name"));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.err.println("Error: ResultSet is null");
    }
    return recipes;
  }

  /**
   * Get a list of missing ingredients from the inventory based on an input
   * recipe.
   *
   * @param recipe the input recipe.
   * @return a list of missing ingredients.
   */
  public static List<Integer> getMissingIngredients(String recipe) {
    List<Integer> missingIngredients = new ArrayList<>();
    try {
      // Fetch the list of ingredients required for the recipe
      ResultSet rs = Calendar.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity "
              + "FROM recipe_ingredients "
              + "WHERE recipe_id = (SELECT recipe_id FROM recipe WHERE name = '" + recipe + "')"
      );

      while (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");
        float requiredQuantity = rs.getFloat("quantity");

        // Check if the ingredient is available in the inventory in the required quantity
        ResultSet rsInventory = Calendar.sqlConnector.executeSqlSelect(
            "SELECT quantity "
                + "FROM inventory_ingredient "
                + "WHERE ingredient_id = " + ingredientId
        );

        if (rsInventory.next()) {
          float availableQuantity = rsInventory.getFloat("quantity");
          if (availableQuantity < requiredQuantity) {
            missingIngredients.add(ingredientId);
          }
        } else {
          missingIngredients.add(ingredientId);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    for (int i = 0; i < missingIngredients.size(); i++) {
      System.out.println(missingIngredients.get(i));
    }
    return missingIngredients;
  }

  /**
   * Get a list of missing ingredients and the missing quantity
   * from the inventory based on an input
   * recipe.
   *
   * @param recipe the input recipe.
   * @return a list of missing ingredients.
   */
  public static Map<Integer, Float> getMissingIngredientsWithQuantity(String recipe,
      float portions) {
    Map<Integer, Float> missingIngredients = new HashMap<>();
    try {
      // Fetch the list of ingredients required for the recipe
      ResultSet rs = Calendar.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity "
              + "FROM recipe_ingredients "
              + "WHERE recipe_id = (SELECT recipe_id FROM recipe WHERE name = '" + recipe + "')"

      );

      while (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");
        float requiredQuantity = rs.getFloat("quantity") * portions;

        // Check if the ingredient is available in the inventory in the required quantity
        ResultSet rsInventory = Calendar.sqlConnector.executeSqlSelect(
            "SELECT quantity "
                + "FROM inventory_ingredient "
                + "WHERE ingredient_id = " + ingredientId
        );

        if (rsInventory.next()) {
          float availableQuantity = rsInventory.getFloat("quantity");
          if (availableQuantity < requiredQuantity) {
            missingIngredients.put(ingredientId, requiredQuantity - availableQuantity);
          }
        } else {
          missingIngredients.put(ingredientId, requiredQuantity);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return missingIngredients;
  }

  /**
   * Get a list of ingredients and the quantity required for a given recipe.
   *
   * @param recipe the name of the recipe.
   * @param portions the portions of the recipe.
   * @return a map of ingredients and the quantity required.
   */
  public static Map<Integer, Float> getIngredientsAndQuantity(String recipe, float portions) {
    Map<Integer, Float> totalIngredients = new HashMap<>();
    try {
      // Fetch the list of ingredients required for the recipe
      ResultSet rs = Calendar.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity "
              + "FROM recipe_ingredients "
              + "WHERE recipe_id = (SELECT recipe_id FROM recipe WHERE name = '" + recipe + "')"
      );

      while (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");
        float requiredQuantity = rs.getFloat("quantity") * portions;
        totalIngredients.put(ingredientId, requiredQuantity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return totalIngredients;
  }

  /**
   * Get a list of missing ingredients and the missing quantity by comparing the ingredients
   * in a Map to what you have.
   *
   * @param ingredients the ingredients and the quantity required
   * @return a map of missing ingredients and the quantity missing
   */
  public static Map<Integer, Float> getMissingIngredientsFromMap(Map<Integer, Float> ingredients) {
    Map<Integer, Float> missingIngredients = new HashMap<>();
    try {
      for (Map.Entry<Integer, Float> entry : ingredients.entrySet()) {
        int ingredientId = entry.getKey();
        float requiredQuantity = entry.getValue();

        // Check if the ingredient is available in the inventory in the required quantity
        ResultSet rsInventory = Calendar.sqlConnector.executeSqlSelect(
            "SELECT quantity "
                + "FROM inventory_ingredient "
                + "WHERE ingredient_id = " + ingredientId
        );

        if (rsInventory.next()) {
          float availableQuantity = rsInventory.getFloat("quantity");
          if (availableQuantity < requiredQuantity) {
            missingIngredients.put(ingredientId, requiredQuantity - availableQuantity);
          }
        } else {
          missingIngredients.put(ingredientId, requiredQuantity);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return missingIngredients;
  }



}
