package no.ntnu.idatt1005.plate.controller.ui.cookbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiRecipeViewController;

/**
 * ListCell class which manages the cells in the ingredient list,
 * specifically ingredients in a recipe within the recipe view.
=======
import javafx.scene.layout.Priority;
import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.controller.UiInventoryController;
import no.ntnu.idatt1005.plate.model.Ingredient;
import org.iq80.snappy.Main;

/**
 * ListCell class which manages the cells in the ingredient list,
 * specifically in the inventory.
>>>>>>> feature/cookbook
 */
public class RecipeListCell extends ListCell<Integer> {

  /**
   * Controller for this list cell.
   */
  private final UiRecipeViewController controller;


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
  //private final Label category = new Label();

  public RecipeListCell(UiRecipeViewController controller) {
    this.controller = controller;

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
    grid.add(quantities, 1, 0);
    grid.add(allergens, 2, 0);
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

                "SELECT i.name AS name, i.unit AS unit, a.name AS allergen, ri.quantity AS quantity, c.name AS category " +
                "FROM ingredient i " +
                "LEFT JOIN allergen a ON i.allergen_id = a.id " +
                "JOIN recipe_ingredients ri ON i.ingredient_id = ri.ingredient_id " +
                "JOIN category c ON i.category_id = c.id " +
                "WHERE ri.ingredient_id = '" + ingredientId + "';"
        );

        if (ingredientDetails.next()) {
          // Set the name, allergens, and category labels
          name.setText(ingredientDetails.getString("name"));

          String quantity = ingredientDetails.getString("quantity");
          String unit = ingredientDetails.getString("unit");

          /* Check if the input to the portion text field can be converted to a float, and if so,
          calculate the new quantity and add it to the display*/
          if (this.controller.getPortionTextField().getText().matches("[+-]?([0-9]*[.])?[0-9]+")) {
            quantities.setText(quantity != null ? ((Float.parseFloat(quantity) *
                Float.parseFloat(this.controller.getPortionTextField().getText())) + " " + unit) : "None");
          } else {
            quantities.setText(quantity != null ? ((quantity + " " + unit)) : "None");
          }

          String allergen = ingredientDetails.getString("allergen");
          allergens.setText(allergen != null ? allergen : "None");

          //String category = ingredientDetails.getString("category");
          //this.category.setText(category != null ? category : "None");

          setGraphic(grid);
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

}
