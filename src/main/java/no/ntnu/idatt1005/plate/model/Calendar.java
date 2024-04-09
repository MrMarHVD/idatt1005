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


  private Calendar() {
  }



  /**
   * This method checks if a day already exists in the database.
   *
   * @param date the date to check
   * @return true if the day exists, false if not
   */
  public static boolean dayExists(Date date) {
    String query = "SELECT * FROM day WHERE date = '" + date + "';";
    SqlConnector sqlConnector = new SqlConnector();
    ResultSet rs = sqlConnector.executeSqlSelect(query);

    try {
      if (rs != null && rs.next()) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Day: " + date + " does not exist in db");
    return false;
  }

  /**
   * This method inserts a day into the database with a random recipe.
   *
   * @param date the date to insert
   */
  public static void insertDay(Date date) {
    SqlConnector sqlConnector = new SqlConnector();

    // gets a random recipe from the database
    // this should be moved to a separate utils class
    String selectQuery = "SELECT recipe_id FROM recipe ORDER BY ABS(RANDOM()) LIMIT 1;";
    ResultSet rs = sqlConnector.executeSqlSelect(selectQuery);
    int recipeId = 0;
    try {
      if (rs != null) {
        recipeId = rs.getInt("recipe_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    String query = "INSERT INTO day(date, recipe_id) VALUES ('" + date + "', " + recipeId + ");";
    sqlConnector.executeSqlUpdate(query);
    System.out.println("Day: " + date + " added to calendar with recipe: " + recipeId);
  }

  /**
   * This method gets the recipes for each day in the calendar.
   *
   * @return a map with the date as key and the recipe as value
   */
  public static Map<String, String> getDayRecipes() {
    Map<String, String> dayRecipes = new HashMap<>();
    SqlConnector sqlConnector = new SqlConnector();
    String selectQuery = "SELECT * FROM day JOIN recipe ON day.recipe_id = recipe.recipe_id";

    ResultSet rs = sqlConnector.executeSqlSelect(selectQuery);

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

  public static String getRecipe (Date date) {
    String recipe = "";
    SqlConnector sqlConnector = new SqlConnector();
    String selectQuery = "SELECT * FROM day JOIN recipe ON day.recipe_id = recipe.recipe_id WHERE date = '" + date + "';";
    ResultSet rs = sqlConnector.executeSqlSelect(selectQuery);
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

  public static void changeRecipe(Date date, String recipe) {
    SqlConnector sqlConnector = new SqlConnector();
    String selectQuery = "SELECT recipe_id FROM recipe WHERE name = '" + recipe + "';";
    ResultSet rs = sqlConnector.executeSqlSelect(selectQuery);
    int recipeId = 0;
    try {
      if (rs != null) {
        recipeId = rs.getInt("recipe_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    String updateQuery = "UPDATE day SET recipe_id = " + recipeId + " WHERE date = '" + date + "';";
    sqlConnector.executeSqlUpdate(updateQuery);
  }

  /**
   * Search the database for recipes with a name that contains the search string.
   *
   * @param search the string to search the database for.
   * @return an arraylist containing the names of the recipes that match the search string.
   */
  public static ArrayList<String> searchRecipes (String search) {
    ArrayList<String> recipes = new ArrayList<>();
    SqlConnector sqlConnector = new SqlConnector();
    String selectQuery = "SELECT * FROM recipe WHERE name LIKE '%" + search + "%';";
    ResultSet rs = sqlConnector.executeSqlSelect(selectQuery);
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
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity " +
              "FROM recipe_ingredients " +
              "WHERE recipe_id = (SELECT recipe_id FROM recipe WHERE name = '" + recipe + "')"
      );


      while (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");
        float requiredQuantity = rs.getFloat("quantity");

        // Check if the ingredient is available in the inventory in the required quantity
        ResultSet rsInventory = MainController.sqlConnector.executeSqlSelect(
            "SELECT quantity " +
                "FROM inventory_ingredient " +
                "WHERE ingredient_id = " + ingredientId
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
    } for (int i = 0; i < missingIngredients.size(); i++) {
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
  public static Map<Integer, Float> getMissingIngredientsWithQuantity(String recipe) {
    Map<Integer, Float> missingIngredients = new HashMap<>();
    try {
      // Fetch the list of ingredients required for the recipe
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT ingredient_id, quantity " +
              "FROM recipe_ingredients " +
              "WHERE recipe_id = (SELECT recipe_id FROM recipe WHERE name = '" + recipe + "')"
      );

      while (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");
        float requiredQuantity = rs.getFloat("quantity");

        // Check if the ingredient is available in the inventory in the required quantity
        ResultSet rsInventory = MainController.sqlConnector.executeSqlSelect(
            "SELECT quantity " +
                "FROM inventory_ingredient " +
                "WHERE ingredient_id = " + ingredientId
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
