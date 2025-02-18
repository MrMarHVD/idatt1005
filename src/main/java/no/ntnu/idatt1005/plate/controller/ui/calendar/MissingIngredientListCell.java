package no.ntnu.idatt1005.plate.controller.ui.calendar;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.model.ShoppingList;

/**
 * ListCell class which manages ingredients that are missing from the inventory
 * based on the required ingredients in the recipe selected in the calendar.
 *
 * @version 1.0
 */
public class MissingIngredientListCell extends ListCell<Integer> {

  /**
   * The name of the recipe.
   */
  private final String recipeName;

  /**
   * The grid.
   */
  private final GridPane grid = new GridPane();

  /**
   * The name of the ingredient.
   */
  private final Label name = new Label();

  /**
   * The quantity of the ingredient.
   */
  private final Label quantity = new Label();

  /**
   * Label showing whether the ingredient in question is in the shopping list already.
   */
  private final Label inShoppingList = new Label();


  /**
   * Constructor for the MissingIngredientListCell class.
   *
   * @param recipeName the name of the recipe.
   */
  public MissingIngredientListCell(String recipeName) {
    this.recipeName = recipeName;
    // Set the column constraints for the grid such that the columns are equally wide.
    int noOfColumns = 3; // Number of columns in the list view
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth((float) (100 / noOfColumns));
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth((float) (100 / noOfColumns));
    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth((float) (100 / noOfColumns));

    grid.getColumnConstraints().addAll(column1, column2, column3);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.add(name, 0, 0);
    grid.add(quantity, 1, 0);
    grid.add(inShoppingList, 2, 0);
  }



  /**
   * Updates the item in the cell (automatic).
   *
   * @param empty whether the cell is to be empty.
   */
  @Override
  protected void updateItem(Integer ingredientId, boolean empty) {
    super.updateItem(ingredientId, empty);

    if (ingredientId == null || empty) {
      setGraphic(null);
    } else {
      try {
        // Fetch ingredient details from the database
        ResultSet ingredientDetails = MainController.sqlConnector.executeSqlSelect(

            "SELECT i.name AS name, i.ingredient_id AS ingredient_id, "
                + "i.unit AS unit, SUM(ri.quantity - IFNULL(ii.quantity, 0)) AS quantity "
                + "FROM ingredient i "
                + "JOIN recipe_ingredients ri ON i.ingredient_id = ri.ingredient_id "
                + "LEFT JOIN inventory_ingredient ii ON i.ingredient_id = ii.ingredient_id "
                + "WHERE ri.recipe_id = (SELECT recipe_id FROM recipe WHERE name = '"
                + this.recipeName + "') "
                + "AND i.ingredient_id = " + ingredientId + " "
                + "GROUP BY i.name, i.unit;"
        );

        if (ingredientDetails.next()) {
          // Set the name, allergens, and category labels
          name.setText(ingredientDetails.getString("name"));

          String quantity = ingredientDetails.getString("quantity");
          String unit = ingredientDetails.getString("unit");
          this.quantity.setText(quantity != null ? (quantity + " " + unit) : "None");

          // Check if the ingredient is already in the shopping list
          if (ShoppingList.inShoppingList(ingredientDetails.getInt("ingredient_id"))) {
            this.inShoppingList.setText("Yes");
          } else {
            this.inShoppingList.setText("No");
          }
          int index = getIndex();
          if (index % 2 == 0) {
            grid.setStyle("-fx-background-color: #ffffff00;");
          } else {
            grid.setStyle("-fx-background-color: #e0e0e033;");
          }
          setGraphic(grid);
        }
      } catch (SQLException e) {
        PopupManager.displayError("Selection error", "Could not select ingredients.");
      }
    }
  }

}
