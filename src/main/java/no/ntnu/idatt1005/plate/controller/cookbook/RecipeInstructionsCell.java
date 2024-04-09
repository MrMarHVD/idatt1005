package no.ntnu.idatt1005.plate.controller.cookbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import no.ntnu.idatt1005.plate.controller.global.MainController;

import javafx.scene.layout.Priority;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui_mainviews.UiInventoryController;

import org.iq80.snappy.Main;
import org.w3c.dom.Text;


/**
 * ListCell class which manages the cells in the ingredient list,
 * specifically in the inventory.
 */
public class RecipeInstructionsCell extends ListCell<Integer> {

  /**
   * The grid.
   */
  private final GridPane grid = new GridPane();

  /**
   * The name of the ingredient.
   */
  private final Label name = new Label();

  private final TextArea instructionsArea = new TextArea();

  public RecipeInstructionsCell() {
    ColumnConstraints column1 = new ColumnConstraints();

    grid.getColumnConstraints().add(column1);
    grid.setHgap(10);
    grid.setVgap(10);
  }

  /**
   * Updates the item in the cell (automatic).
   *
   * @param recipeId the new ingredient.
   * @param empty    whether the cell is to be empty.
   */

  @Override
  protected void updateItem(Integer recipeId, boolean empty) {
    super.updateItem(recipeId, empty);

    if (recipeId == null || empty) {
      setGraphic(null);
    } else {
      try {
        // Fetch ingredient details from the database
        ResultSet recipeInstructions = MainController.sqlConnector.executeSqlSelect(
                "SELECT instruction, name " +
                        "FROM recipe " +
                        "WHERE recipe_id = " + recipeId + ";"
        );

        if (recipeInstructions.next()) {
          name.setText(recipeInstructions.getString("name"));

          String instruction = recipeInstructions.getString("instruction");
          //instructionsArea.setText(instruction != null ? instruction : "None");
          System.out.println(instruction);
          setGraphic(grid);
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

