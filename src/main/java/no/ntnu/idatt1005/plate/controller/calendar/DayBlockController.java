package no.ntnu.idatt1005.plate.controller.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import no.ntnu.idatt1005.plate.controller.global.MainController;

/**
 * This class is the controller for the DayBlock object in the user interface.
 */
public class DayBlockController {

  /**
   * The main controller.
   */
  @FXML
  private MainController mainController;

  /**
   * The day label.
   */
  @FXML
  private Label dayLabel;

  /**
   * The date label.
   */
  @FXML
  private Label dateLabel;

  /**
   * The button displaying the name of the recipe, and which allows the user to go to its
   * recipe view.
   */
  @FXML
  private Button recipeButton;

  /**
   * Initialize the controller.
   */
  @FXML
  private void initialize() {
  }

  /**
   * Set the main controller for this day block controller.
   *
   * @param mainController the main controller to set.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * Set the recipe name.
   *
   * @param recipeName the recipe name.
   */
  public void setRecipe(String recipeName) {
    this.recipeButton.setText(recipeName);
  }

  /**
   * Set the action event that changes to the corresponding recipe view when button is clicked.
   *
   * @param recipe the name of the recipe.
   */
  public void setActionOnRecipeButtonClicked(String recipe) {
    this.recipeButton.setOnAction(event -> {

      if (this.mainController != null) {
        this.mainController.goToRecipe(recipe);
      }
      else {
        System.out.println("Main controller is null");
      }
    });
  }

  /**
   * Set the day of the week for this DayBlock.
   *
   * @param day the day of the week.
   */

  public void setDay(String day) {
    this.dayLabel.setText(day);
  }

  /**
   * Set the date for this DayBlock.
   *
   * @param date the date.
   */
  public void setDate(String date) {
    this.dateLabel.setText(date);
  }

}
