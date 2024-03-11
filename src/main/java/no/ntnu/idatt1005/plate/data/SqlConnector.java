package no.ntnu.idatt1005.plate.data;

import java.sql.*;

public class SqlConnector {

  private static Connection con = null;
  /**
   * Constructor for the SqlConnector class.
   */
  public SqlConnector() {
    try {
      if (con == null) {
        con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/plate.db");
      }
      resetDatabase();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Close the connection to the database.
   */
  public void close() {
    //try {
    //if (con != null) {
    //con.close();
    //System.out.println("Connection closed");
    //}
    //} catch (Exception e) {
    //System.out.println(e.getMessage());
    //}
  }


  /**
   * Check if a table exists in the database.
   *
   * @param tableName the name of the table.
   * @return true if the table exists, false otherwise.
   */
  private boolean tableExists(String tableName) {
    try  {
      ResultSet tables = executeSqlSelect("SELECT * FROM %s".formatted(tableName));
      return tables.next();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  /**
   * Execute a SQL select query.
   *
   * @param query the query to execute.
   * @return the result set from the query.
   */
  public ResultSet executeSqlSelect(String query) {
    ResultSet rs = null;
    try  {
      if (con.isClosed()) {
        con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/plate.db");
      }
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery(query);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    finally {
      close();
    }
    return rs;
  }

  /**
   * Execute a SQL update query.
   *
   * @param query the query to execute.
   */
  public void executeSqlUpdate(String query) {
    try {
      if (con.isClosed()) {
        con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/plate.db");
      }
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      close();
    }
  }

  /**
   * Check if any of the tables are missing in the database.
   *
   * @return true if any table is missing, false otherwise.
   */
  private boolean anyTableMissing() {
    return !tableExists("ingredient") || !tableExists("allergen") || !tableExists("category") || !tableExists("recipe_ingredients") || !tableExists("recipe") || !tableExists("day");
  }

  /**
   * Reset the database to its initial state.
   */
  private void resetDatabase() {
    executeSqlUpdate("CREATE TABLE IF NOT EXISTS allergen(\n"
        + "  id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  CONSTRAINT allergen_pk PRIMARY KEY(id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS category(\n"
        + "  id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  CONSTRAINT category_pk PRIMARY KEY(id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS ingredient(\n"
        + "  ingredient_id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  allergen_id INTEGER,\n"
        + "  category_id INTEGER,\n"
        + "  unit VARCHAR(60),\n"
        + "  CONSTRAINT ingredient_pk PRIMARY KEY(ingredient_id),\n"
        + "  CONSTRAINT allergen_fk FOREIGN KEY(allergen_id) REFERENCES allergen(id),\n"
        + "  CONSTRAINT category_fk FOREIGN KEY(category_id) REFERENCES category(id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS recipe(\n"
        + "  recipe_id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  instruction TEXT NOT NULL,\n"
        + "  CONSTRAINT recipe_pk PRIMARY KEY(recipe_id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS recipe_ingredients(\n"
        + "  recipe_id INTEGER,\n"
        + "  ingredient_id INTEGER,\n"
        + "  quantity DECIMAL(10,2),\n"
        + "  CONSTRAINT RecipeIngredients_pk PRIMARY KEY (recipe_id, ingredient_id),\n"
        + "  CONSTRAINT recipe_fk FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id),\n"
        + "  CONSTRAINT ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS day(\n"
        + "  day_in_week INTEGER,\n"
        + "  recipe_id INTEGER,\n"
        + "  CONSTRAINT day_pk PRIMARY KEY (day_in_week),\n"
        + "  CONSTRAINT day_fk FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS inventory_ingredient(\n"
        + "  id INTEGER,\n"
        + "  ingredient_id INTEGER,\n"
        + "  quantity DECIMAL(10,2),\n"
        + "  CONSTRAINT inv_pk PRIMARY KEY (id),\n"
        + "  CONSTRAINT inv_ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)\n"
        + ");\n"
        + "CREATE TABLE IF NOT EXISTS shopping_list_items(\n"
        + "  id INTEGER,\n"
        + "  ingredient_id INTEGER,\n"
        + "  quantity DECIMAL(10,2),\n"
        + "  CONSTRAINT inv_pk PRIMARY KEY (id),\n"
        + "  CONSTRAINT inv_ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)\n"
        + ");\n");
    if (anyTableMissing()) {
      executeSqlUpdate("-- Allergener\n"
          + "INSERT INTO allergen(id, name) values(1,'Nuts');\n"
          + "INSERT INTO allergen(id, name) values(2,'Eggs');\n"
          + "INSERT INTO allergen(id, name) values(3,'Gluten');\n"
          + "INSERT INTO allergen(id, name) values(4,'Laktose');\n"
          + "\n"
          + "-- Kategorier\n"
          + "INSERT INTO category(id, name) VALUES (1, 'Vegetable');\n"
          + "INSERT INTO category(id, name) VALUES (2, 'Fruit');\n"
          + "INSERT INTO category(id, name) VALUES (3, 'Protein');\n"
          + "INSERT INTO category(id, name) VALUES (4, 'Berry');\n"
          + "INSERT INTO category(id, name) VALUES (5, 'Dairy');\n"
          + "INSERT INTO category(id, name) VALUES (6, 'Bakery');\n"
          + "INSERT INTO category(id, name) VALUES (7, 'Condiment');\n"
          + "INSERT INTO category(id, name) VALUES (8, 'Spice');\n"
          + "\n"
          + "-- Ingredienser\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (1, 'Apple', NULL, 2, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (2, 'Orange', NULL, 2, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (3, 'Milk', 4, 5, 'dL');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (4, 'Beef', NULL, 3, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (5, 'Chicken', NULL, 3, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (6, 'Egg', 2, 3, 'qty');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (7, 'Butter', 4, 5, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (8, 'Flour', 3, 6, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (9, 'Bread', 3, 2, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (10, 'Salt', NULL, 8, 'g');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (11, 'Pepper', NULL, 8, 'g');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (12, 'Chillipepper', NULL, 8, 'g');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (13, 'Paprika', NULL, 1, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (14, 'Cuecumber', NULL, 1, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (15, 'Corn', NULL, 1, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (16, 'Onion', NULL, 1, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (17, 'Sweet Chilli Sauce', NULL, 7, 'dL');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (18, 'Pepperoni', NULL, 3, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (19, 'Cheese', NULL, 5, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (20, 'Pizza dough', 3, 6, 'kg');\n"
          + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (21, 'Pizza sauce', NULL, 7, 'dL');\n"
          + "\n"
          + "-- Oppskrifter\n"
          + "INSERT INTO recipe(recipe_id, name, instruction) VALUES (1, 'Margherita Pizza', 'Classic Margherita Pizza with tomato sauce, mozzarella, and fresh basil. Roll out pizza dough, add sauce and toppings, and bake.');\n"
          + "\n"
          + "INSERT INTO recipe(recipe_id, name, instruction) VALUES (2, 'Classic Grilled Cheese Sandwich', 'A simple and delicious grilled cheese sandwich made with butter, bread, and melted cheese. Butter both sides of bread, add cheese, and grill until golden brown.');\n"
          + "\n"
          + "INSERT INTO recipe(recipe_id, name, instruction) VALUES (3, 'Chicken and Paprika Skewers', 'Flavorful Chicken and Paprika Skewers with marinated chicken and paprika. Skewer chicken and paprika alternately, grill, and serve.');\n"
          + "\n"
          + "INSERT INTO recipe(recipe_id, name, instruction) VALUES (4, 'Spicy Pepperoni Pizza', 'Spicy Pepperoni Pizza with tomato sauce, pepperoni, and chilli peppers. Roll out pizza dough, add sauce, toppings, and bake.');\n"
          + "\n"
          + "INSERT INTO recipe(recipe_id, name, instruction) VALUES (5, 'Egg and Onion Scramble', 'Quick and tasty Egg and Onion Scramble with scrambled eggs and sautéed onions. Scramble eggs, sauté onions, and mix together.');\n"
          + "\n"
          + "\n"
          + "-- OppskriftsIngredienser\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (1, 20, 1); -- Pizza Dough\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (1, 21, 0.5); -- Pizza Sauce\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (1, 19, 100); -- Cheese\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (2, 9, 2); -- Bread\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (2, 7, 1); -- Butter\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (2, 19, 50); -- Cheese\n"
          + "\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (3, 5, 300); -- Chicken\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (3, 13, 2); -- Paprika\n"
          + "\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 20, 1); -- Pizza Dough\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 21, 0.5); -- Pizza Sauce\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 18, 50); -- Pepperoni\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 12, 2); -- Chilli Pepper\n"
          + "\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (5, 6, 3); -- Eggs\n"
          + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (5, 16, 1); -- Onion\n"

          + "-- Dager\n"
          + "INSERT INTO day(day_in_week, recipe_id) VALUES (1, 1); -- Mandag\n"
          + "INSERT INTO day(day_in_week, recipe_id) VALUES (3, 2); -- Onsdag\n"
          + "INSERT INTO day(day_in_week, recipe_id) VALUES (5, 3); -- Fredag\n"
          + "\n"
          + "-- Lager\n"
          + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (1, 3, 2.5); -- Melk\n"
          + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (2, 9, 1); -- Brød\n"
          + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (3, 15, 0.5); -- Agurk\n"
          + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (4, 16, 1); -- Mais\n"
          + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (5, 17, 0.3); -- Sweet Chilli \n");
      System.out.println("data inserted");
    }
  }


}
