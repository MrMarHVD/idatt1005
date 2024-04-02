package no.ntnu.idatt1005.plate.controller.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import no.ntnu.idatt1005.plate.model.Calendar;

import java.sql.Date;
import java.time.LocalDate;

/**
 * This class is the controller for the Today view in the homepage.
 */
public class TodayController {

  @FXML
  private Label mealNameLabel;


  public void initialize() {
    setMealName();
  }

  public void setMealName() {
    LocalDate today = LocalDate.now();
    String mealName = Calendar.getRecipe(Date.valueOf(today));
    this.mealNameLabel.setText(mealName);
  }
}