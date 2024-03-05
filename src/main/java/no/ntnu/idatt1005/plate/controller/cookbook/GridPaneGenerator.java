package no.ntnu.idatt1005.plate.controller.cookbook;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class is used to insert recipe icons into the GridPane
 * in the UiCookbook.fxml view.
 */
public class GridPaneGenerator {

  /**
   * Populate the input GridPane with recipe icons.
   * @param gridPane the grid pane in question
   * @param recipeIds a list containing the Ids of the recipes
   */
  public void populateGridPaneWithIcons(GridPane gridPane, List<Integer> recipeIds) {
    for (int i = 0; i < recipeIds.size(); i++) {
      int recipeId = recipeIds.get(i);

      // Assume that `getIconForRecipe` is a method that returns an Image for a recipe
      Image image = getIconForRecipe(recipeId);
      ImageView imageView = new ImageView(image);

      // Set the fx:id of the ImageView to the recipe name
      // imageView.setId(recipeId); This has to be defined once the database is in place

      // Add the ImageView to the GridPane
      int columnIndex = i % 3; // numColumns is the number of columns in your GridPane
      int rowIndex = i / 3;
      gridPane.add(imageView, columnIndex, rowIndex);
    }
  }

  private Image getIconForRecipe(int recipeId) {
    // Return an Image for the recipe
    // This is just a placeholder, you should replace this with your actual code
    return new Image("file:resources/images/" + recipeId + ".png");
  }
}
