package no.ntnu.idatt1005.plate.controller.calendar;

import java.sql.Date;
import java.time.LocalDate;


import javafx.fxml.FXML;
import no.ntnu.idatt1005.plate.model.Calendar;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the controller for the Calendar view in the user interface.
 */
public class CalendarController {

  @FXML
  private DayBlockController mondayController;

  @FXML
  private DayBlockController tuesdayController;

  @FXML
  private DayBlockController wednesdayController;

  @FXML
  private DayBlockController thursdayController;

  @FXML
  private DayBlockController fridayController;

  @FXML
  private DayBlockController saturdayController;

  @FXML
  private DayBlockController sundayController;

  /**
   * This method initializes the Calendar view with the correct recipes for each day.
   */
  public void initialize() {
    LocalDate today = LocalDate.now();
    LocalDate thisMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    ArrayList<DayBlockController> dayBlockControllers = new ArrayList<>(Arrays.asList(mondayController, tuesdayController, wednesdayController, thursdayController, fridayController, saturdayController, sundayController));


    for (int i = 0; i < 7; i++) {
      LocalDate date = thisMonday.plusDays(i);
      if (!Calendar.dayExists(Date.valueOf(date))) {
        Calendar.insertDay(Date.valueOf(date));
      }

      String day = date.getDayOfWeek().toString().charAt(0) + date.getDayOfWeek().toString().substring(1).toLowerCase();
      dayBlockControllers.get(i).setDay(day);
      dayBlockControllers.get(i).setDate(date.toString());

      String recipe = Calendar.getDayRecipes().get(date.toString());
      dayBlockControllers.get(i).setRecipe(recipe);
    }
  }
}