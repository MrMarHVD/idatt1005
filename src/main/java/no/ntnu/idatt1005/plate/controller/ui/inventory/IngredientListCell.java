package no.ntnu.idatt1005.plate.controller.ui.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;

/**
 * ListCell class which manages the cells in the ingredient list,
 * specifically in the inventory.
 */
public class IngredientListCell extends ListCell<Integer> {


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
  private final Label quantities = new Label();

  /**
   * The allergens.
   */
  private final Label allergens = new Label();

  /**
   * The category of the ingredient.
   */
  private final Label category = new Label();

  /**
   * Constructor for the IngredientListCell. Set the column constraints and add the various
   * components to the grid.
   */
  public IngredientListCell() {

    // Set the column constraints for the grid such that the columns are equally wide.
    int noOfColumns = 4; // Change this if you want to change the number of columns.
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth((float) (100 / noOfColumns));
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth((float) (100 / noOfColumns));
    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth((float) (100 / noOfColumns));
    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth((float) (100 / noOfColumns));

    grid.getColumnConstraints().addAll(column1, column2, column3);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.add(name, 0, 0);
    grid.add(quantities, 1, 0);
    grid.add(allergens, 2, 0);
    grid.add(category, 3, 0);
    grid.setAlignment(Pos.CENTER_LEFT);

  }



  /**
   * Updates the item in the cell (automatic).
   *
   * @param ingredientId the new ingredient.
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
              "SELECT i.name, ii.quantity AS quantity, i.unit AS unit, a.name AS allergen, "
                  + "c.name AS category "
                  + "FROM ingredient i "
                  + "LEFT JOIN allergen a ON i.allergen_id = a.id "
                  + "INNER JOIN inventory_ingredient ii ON ii.ingredient_id = i.ingredient_id "
                  + "LEFT JOIN category c ON i.category_id = c.id "
                  + "WHERE i.ingredient_id = " + ingredientId + ";"
          );


        if (ingredientDetails.next()) {
          // Set the name, allergens, and category labels
          name.setText(ingredientDetails.getString("name"));

          String quantity = ingredientDetails.getString("quantity");

          String unit = ingredientDetails.getString("unit");
          quantities.setText(quantity != null ? (quantity + " " + unit) : "None");

          String allergen = ingredientDetails.getString("allergen");
          allergens.setText(allergen != null ? allergen : "None");

          String category = ingredientDetails.getString("category");
          this.category.setText(category != null ? category : "None");
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
