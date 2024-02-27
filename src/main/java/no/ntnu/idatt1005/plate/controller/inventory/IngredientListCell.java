package no.ntnu.idatt1005.plate.controller.inventory;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import no.ntnu.idatt1005.plate.model.Ingredient;

/**
 * ListCell class which manages the cells in the ingredient list,
 * specifically in the inventory.
 */
public class IngredientListCell extends ListCell<Ingredient> {

  /**
   * The grid.
   */
  private GridPane grid = new GridPane();

  /**
   * The name of the ingredient.
   */
  private Label name = new Label();

  /**
   * The allergens.
   */
  private Label allergens = new Label();

  /**
   * The category of the ingredient.
   */
  private Label category = new Label();

  public IngredientListCell() {

    // Set the column constraints for the grid such that the columns are equally wide.
    int noOfColumns = 3; // Change this if you want to change the number of columns.
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
   * @param item the new ingredient.
   * @param empty whether the cell is to be empty.
   */
  @Override
  protected void updateItem(Ingredient item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setGraphic(null);
    } else {
      name.setText(item.getName());

      // If the ingredient has allergens, display them. Otherwise, display "None".
      if (item.getAllergens() != null && !item.getAllergens().isEmpty()) {
        allergens.setText(String.join(", ", item.getAllergens()));
      } else {
        allergens.setText("None");
      }
      category.setText(item.getCategory());
      setGraphic(grid);
    }
  }

}
