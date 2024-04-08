package no.ntnu.idatt1005.plate.controller.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class is the controller for the DayBlock object in the user interface.
 */
public class DayBlockController {

  @FXML
  private Label dayLabel;

  @FXML
  private Label dateLabel;

  @FXML
  private Label recipeLabel;

  public void setRecipe(String recipeName) {
    this.recipeLabel.setText(recipeName);
  }

  public void setDay(String day) {
    this.dayLabel.setText(day);
  }

  public void setDate(String date) {
    this.dateLabel.setText(date);
  }

}
