package no.ntnu.idatt1005.plate.controller.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import no.ntnu.idatt1005.plate.data.SqlConnector;
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
    SqlConnector sqlConnector = new SqlConnector();
    Calendar calendar = new Calendar(sqlConnector);
    LocalDate today = LocalDate.now();
    String mealName = calendar.getRecipe(Date.valueOf(today));
    this.mealNameLabel.setText(mealName);
  }
}