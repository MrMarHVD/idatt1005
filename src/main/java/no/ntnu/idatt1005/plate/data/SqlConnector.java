package no.ntnu.idatt1005.plate.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import javax.swing.Popup;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;

/**
 * This class handles all direct interactions with the SQLite database, and is
 * responsible for querying and updating it according to the needs of the application.
 */
public class SqlConnector {

  private Connection con = null;

  /**
   * The path to the create, insert and drop SQL files.
   */
  private static final String createSqlFilePath = "src/main/resources/CreateQuery.sql";
  private static final String insertSqlFilePath = "src/main/resources/InsertQuery.sql";
  private static final String dropSqlFilePath = "src/main/resources/DropQuery.sql";

  /**
   * The file name of the database.
   */
  private static String dbFileName = "plate.db";


  /**
   * Returns the connection to the database
   * @return connection to database
   */
    private Connection getConnection() {
    if (dbFileName.equals("memory")) {
      con = DriverManager.getConnection("jdbc:sqlite::memory:");
      } else {
        con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/" + dbFileName);
      }
  }

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
   * Constructor for testing SqlConnector class, points to a different database.
   *
   * @param dbFileName the path to the database file
   */
  public SqlConnector(String dbFileName) {
    try {
      if (con == null) {
        SqlConnector.dbFileName = dbFileName;

        // Run database in memory if the file name is ":memory:".
        con = getConnection();

      }
    } catch (Exception e) {
      PopupManager.displayError("Database error", e.getMessage());
    }
  }

  /**
   * Close the connection to the database.
   */
  public void closeConnection() {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      }
    } catch (SQLException e) {
      PopupManager.displayError("Database error", e.getMessage());
    }
  }


  /**
   * Check if a table exists in the database.
   *
   * @param tableName the name of the table.
   * @return true if the table exists, false otherwise.
   */
  private boolean tableExists(String tableName) {
    try {
      ResultSet tables = executeSqlSelect("SELECT * FROM %s".formatted(tableName));
      return tables.next();
    } catch (SQLException e) {
      PopupManager.displayError("Database error", e.getMessage());
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
    try {
      if (con.isClosed()) {
        con = getConnection();
      }
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery(query);
    } catch (SQLException e) {
      PopupManager.displayError("SQL Select error", "Could not select data.");
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
        con = getConnection();
      }
      Statement stmt = con.createStatement();

      stmt.executeUpdate(query);
    } catch (SQLException e) {
      PopupManager.displayError("SQL Update error", "Could not update data.");
    }
  }

  /**
   * Check if any of the tables are missing in the database.
   *
   * @return true if any table is missing, false otherwise.
   */
  private boolean anyTableMissing() {
    return (!tableExists("ingredient") || !tableExists("allergen")
        || !tableExists("category")
        || !tableExists("recipe_ingredients") || !tableExists("recipe")
        || !tableExists("day"));
  }

  /**
   * Reset the database fully to its initial state.
   */
  public synchronized void resetTestDatabase() {
    runSqlFile(dropSqlFilePath); // Drop all tables.
    this.closeConnection();
    //new java.io.File("src/main/resources/calendar_test.db").delete();
    runSqlFile(createSqlFilePath);
    if (anyTableMissing()) {
      runSqlFile(insertSqlFilePath);
    }
  }

  /**
   * Reset the database to its initial state. If any table is missing, the tables are created and
   * default  data is inserted.
   */
  public synchronized void resetDatabase() {
    runSqlFile(createSqlFilePath);
    if (anyTableMissing()) {
      runSqlFile(insertSqlFilePath);
    }
  }

  /**
   * Run the queries in a SQL file.
   *
   * @param path the path to the file.
   */
  private void runSqlFile(String path) {
    try {
      String query = new String(Files.readAllBytes(Paths.get(path)));
      executeSqlUpdate(query);
    } catch (IOException e) {
      PopupManager.displayError("SQL file error", "Could not read SQL file.");
    }
  }


}
