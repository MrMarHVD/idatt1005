package no.ntnu.idatt1005.plate.controller.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import no.ntnu.idatt1005.plate.controller.global.MainController;

/**
 * This class is the controller for the DayBlock object in the user interface.
 */
public class DayBlockController {

  @FXML
  private MainController mainController;

  @FXML
  private Label dayLabel;

  @FXML
  private Label dateLabel;

  @FXML
  private Button recipeButton;

  @FXML
  private void initialize() {
    this.setMainController(mainController);
  }

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  public void setRecipe(String recipeName) {
    this.recipeButton.setText(recipeName);
  }

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

  public void setDay(String day) {
    this.dayLabel.setText(day);
  }

  public void setDate(String date) {
    this.dateLabel.setText(date);
  }

}
