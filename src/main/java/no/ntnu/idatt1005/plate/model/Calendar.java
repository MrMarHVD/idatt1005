package no.ntnu.idatt1005.plate.model;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatt1005.plate.data.SqlConnector;

/**
 * This class handles the calendar functionality of the application.
 */
public class Calendar {

  private SqlConnector sqlConnector;


  public Calendar(SqlConnector sqlConnector) {
    this.sqlConnector = sqlConnector;
  }



  /**
   * This method checks if a day already exists in the database.
   *
   * @param date the date to check
   * @return true if the day exists, false if not
   */
  public boolean dayExists(Date date) {
    String query = "SELECT * FROM day WHERE date = '" + date + "';";
    ResultSet rs = sqlConnector.executeSqlSelect(query);

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
  public void insertDay(Date date) {
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
  }

  /**
   * This method gets the recipes for each day in the day table.
   *
   * @return a map with the date as key and the recipe as value
   */
  public Map<String, String> getDayRecipes() {
    Map<String, String> dayRecipes = new HashMap<>();
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

  public String getRecipe (Date date) {
    String recipe = "";
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

  public void changeRecipe(Date date, String recipe) {
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
  public ArrayList<String> searchRecipes (String search) {
    ArrayList<String> recipes = new ArrayList<>();
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


}
