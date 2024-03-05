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
      if (anyTableMissing()) {
        resetDatabase();
        System.out.println("Database reset");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Close the connection to the database.
   */
  public void close() {
    try {
      if (con != null) {
        //con.close();
        //System.out.println("Connection closed");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
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
    return !tableExists("ingredient") || !tableExists("allergen") || !tableExists("category") || !tableExists("recipe_ingredients") || !tableExists("recipe") || !tableExists("day") || !tableExists("inventory_ingredient");
  }

  /**
   * Reset the database to its initial state.
   */
  private void resetDatabase() {
    executeSqlUpdate("DROP TABLE IF EXISTS inventory_ingredient;\n"
        + "DROP TABLE IF EXISTS day;\n"
        + "DROP TABLE IF EXISTS recipe_ingredients;\n"
        + "DROP TABLE IF EXISTS recipe;\n"
        + "DROP TABLE IF EXISTS ingredient;\n"
        + "DROP TABLE IF EXISTS category;\n"
        + "DROP TABLE IF EXISTS allergen;\n"
        + "\n"
        + "CREATE TABLE allergen(\n"
        + "  id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  CONSTRAINT allergen_pk PRIMARY KEY(id)\n"
        + ");\n"
        + "CREATE TABLE category(\n"
        + "  id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  CONSTRAINT category_pk PRIMARY KEY(id)\n"
        + ");\n"
        + "CREATE TABLE ingredient(\n"
        + "  ingredient_id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  allergen_id INTEGER,\n"
        + "  category_id INTEGER,\n"
        + "  CONSTRAINT ingredient_pk PRIMARY KEY(ingredient_id),\n"
        + "  CONSTRAINT allergen_fk FOREIGN KEY(allergen_id) REFERENCES allergen(id),\n"
        + "  CONSTRAINT category_fk FOREIGN KEY(category_id) REFERENCES category(id)\n"
        + ");\n"
        + "CREATE TABLE recipe(\n"
        + "  recipe_id INTEGER NOT NULL,\n"
        + "  name VARCHAR(60) NOT NULL,\n"
        + "  instruction TEXT NOT NULL,\n"
        + "  CONSTRAINT recipe_pk PRIMARY KEY(recipe_id)\n"
        + ");\n"
        + "CREATE TABLE recipe_ingredients(\n"
        + "  recipe_id INTEGER,\n"
        + "  ingredient_id INTEGER,\n"
        + "  quantity DECIMAL(10,2),\n"
        + "  unit VARCHAR(50),\n"
        + "  CONSTRAINT RecipeIngredients_pk PRIMARY KEY (recipe_id, ingredient_id),\n"
        + "  CONSTRAINT recipe_fk FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id),\n"
        + "  CONSTRAINT ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)\n"
        + ");\n"
        + "CREATE TABLE day(\n"
        + "  day_in_week INTEGER,\n"
        + "  recipe_id INTEGER,\n"
        + "  CONSTRAINT day_pk PRIMARY KEY (day_in_week),\n"
        + "  CONSTRAINT day_fk FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id)\n"
        + ");\n"
        + "CREATE TABLE inventory_ingredient(\n"
        + "  id INTEGER,\n"
        + "  ingredient_id INTEGER,\n"
        + "  quantity DECIMAL(10,2),\n"
        + "  unit VARCHAR(50),\n"
        + "  CONSTRAINT inv_pk PRIMARY KEY (id),\n"
        + "  CONSTRAINT inv_ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)\n"
        + ");\n"
        + "-- Allergener\n"
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
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (1, 'Apple', NULL, 2);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (2, 'Orange', NULL, 2);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (3, 'Milk', 4, 5);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (4, 'Beef', NULL, 3);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (5, 'Chicken', NULL, 3);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (6, 'Egg', 2, 3);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (7, 'Butter', 4, 5);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (8, 'Flour', 3, 6);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (9, 'Bread', 3, 2);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (10, 'Salt', NULL, 8);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (11, 'Pepper', NULL, 8);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (12, 'Chillipepper', NULL, 8);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (13, 'Paprika', NULL, 1);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (14, 'Cuecumber', NULL, 1);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (15, 'Corn', NULL, 1);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (16, 'Onion', NULL, 1);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (17, 'Sweet Chilli Sauce', NULL, 7);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (18, 'Pepperoni', NULL, 3);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (19, 'Cheese', NULL, 5);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (20, 'Pizza dough', 3, 6);\n"
        + "INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id) VALUES (21, 'Pizza sauce', NULL, 7);\n"
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
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (1, 20, 1, 'unit'); -- Pizza Dough\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (1, 21, 0.5, 'cup'); -- Pizza Sauce\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (1, 19, 100, 'g'); -- Cheese\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (2, 9, 2, 'slices'); -- Bread\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (2, 7, 1, 'tbsp'); -- Butter\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (2, 19, 50, 'g'); -- Cheese\n"
        + "\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (3, 5, 300, 'g'); -- Chicken\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (3, 13, 2, 'tsp'); -- Paprika\n"
        + "\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (4, 20, 1, 'unit'); -- Pizza Dough\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (4, 21, 0.5, 'cup'); -- Pizza Sauce\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (4, 18, 50, 'g'); -- Pepperoni\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (4, 12, 2, 'units'); -- Chilli Pepper\n"
        + "\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (5, 6, 3, 'units'); -- Eggs\n"
        + "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, unit) VALUES (5, 16, 1, 'unit'); -- Onion\n"
        + "\n"
        + "\n"
        + "\n"
        + "-- Dager\n"
        + "INSERT INTO day(day_in_week, recipe_id) VALUES (1, 1); -- Mandag\n"
        + "INSERT INTO day(day_in_week, recipe_id) VALUES (3, 2); -- Onsdag\n"
        + "INSERT INTO day(day_in_week, recipe_id) VALUES (5, 3); -- Fredag\n"
        + "\n"
        + "-- Lager\n"
        + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity, unit) VALUES (1, 3, 2.5, 'liters'); -- Melk\n"
        + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity, unit) VALUES (2, 9, 1, 'loaf'); -- Brød\n"
        + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity, unit) VALUES (3, 15, 0.5, 'kg'); -- Agurk\n"
        + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity, unit) VALUES (4, 16, 1, 'kg'); -- Mais\n"
        + "INSERT INTO inventory_ingredient(id, ingredient_id, quantity, unit) VALUES (5, 17, 0.3, 'bottle'); -- Sweet Chilli \n");
  }


}
