package no.ntnu.idatt1005.plate.controller.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.controller.UiInventoryController;
import no.ntnu.idatt1005.plate.model.Ingredient;
import org.iq80.snappy.Main;

/**
 * ListCell class which manages the cells in the ingredient list,
 * specifically in the inventory.
 */
public class IngredientListCell extends ListCell<Integer> {

  private UiInventoryController iC;

  /**
   * The grid.
   */
  private final GridPane grid = new GridPane();

  /**
   * The name of the ingredient.
   */
  private final Label name = new Label();

  

  /**
   * The allergens.
   */
  private final Label allergens = new Label();

  /**
   * The category of the ingredient.
   */
  private final Label category = new Label();

  public IngredientListCell() {

    // Set the column constraints for the grid such that the columns are equally wide.
    int noOfColumns = 4; // Change this if you want to change the number of columns.
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
    grid.add(allergens, 1, 0);
    grid.add(category, 2, 0);
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
                    "SELECT i.name, a.name as allergen, c.name as category " +
                    "FROM ingredient i " +
                    "LEFT JOIN allergen a ON i.allergen_id = a.id " +
                    "LEFT JOIN category c ON i.category_id = c.id " +
                    "WHERE i.ingredient_id = " + ingredientId + ";"
            );

            if (ingredientDetails.next()) {
                // Set the name, allergens, and category labels
                name.setText(ingredientDetails.getString("name"));

                String allergen = ingredientDetails.getString("allergen");
                allergens.setText(allergen != null ? allergen : "None");

                String category = ingredientDetails.getString("category");
                this.category.setText(category != null ? category : "None");

                setGraphic(grid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

}
