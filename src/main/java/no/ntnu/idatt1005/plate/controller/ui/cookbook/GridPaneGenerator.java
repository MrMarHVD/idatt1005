package no.ntnu.idatt1005.plate.controller.ui.cookbook;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * This class is used to insert recipe icons into the GridPane
 * in the UiCookbook.fxml view and handle when user clicks them.
 */
public class GridPaneGenerator {

  /**
   * The GridPane to insert the recipe icons into.
   */
  private final GridPane gridPane;

  /**
   * Default constructor.
   *
   * @param gridPane gridPane to insert the recipe icons into.
   */
  public GridPaneGenerator(GridPane gridPane) {
    this.gridPane = gridPane;
  }

  /**
   * Populate the input GridPane with recipe icons.
   * @param recipeIds a list containing the Ids of the recipes
   */
  public void populateGridPaneWithIcons(List<Integer> recipeIds) {
    for (int i = 0; i < recipeIds.size(); i++) {
      int recipeId = recipeIds.get(i);

      // Assume that `getIconForRecipe` is a method that returns an Image for a recipe
      Image image = getIconForRecipe(recipeId);
      ImageView imageView = new ImageView(image);

      // Handle when the user clicks the recipe icon.
      imageView.setOnMouseClicked(event -> iconClicked(event, recipeId));

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

  /**
   * Handle mouse click of the recipe icons.
   * @param event the event that was triggered
   * @param recipeId the ID of the recipe in question
   */
  private void iconClicked(MouseEvent event, int recipeId)  {
    // Code to handle the click on the icon
  }
}
