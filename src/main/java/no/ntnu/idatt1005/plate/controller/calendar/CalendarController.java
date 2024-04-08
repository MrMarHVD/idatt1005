package no.ntnu.idatt1005.plate.controller.calendar;

import java.sql.Date;
import java.time.LocalDate;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.model.Calendar;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.Text;

/**
 * This class is the controller for the Calendar view in the user interface.
 */
public class CalendarController {

  /**
   * The main controller for the application.
   */
  @FXML
  private MainController mainController;

  /**
   * Each of the controllers for the seven day blocks.
   */
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

  @FXML
  private ComboBox<String> recipeComboBox;

  @FXML
  private TextField recipeSearchField;

  @FXML
  private Button searchButton;

  @FXML
  private Button changeRecipeButton;

  /**
   * This method initializes the Calendar view with the correct recipes for each day.
   */
  public void initialize() {
    this.groupRadioButtons();
    this.addActionListeners();
    LocalDate today = LocalDate.now();
    LocalDate thisMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    ArrayList<DayBlockController> dayBlockControllers = new ArrayList<>(Arrays.asList(
        mondayController, tuesdayController, wednesdayController, thursdayController,
        fridayController, saturdayController, sundayController));


    for (int i = 0; i < 7; i++) {
      LocalDate date = thisMonday.plusDays(i);
      if (!Calendar.dayExists(Date.valueOf(date))) {
        Calendar.insertDay(Date.valueOf(date));
      }

      String day = date.getDayOfWeek().toString().charAt(0) +
          date.getDayOfWeek().toString().substring(1).toLowerCase();
      dayBlockControllers.get(i).setDay(day);
      dayBlockControllers.get(i).setDate(date.toString());

      String recipe = Calendar.getDayRecipes().get(date.toString());
      dayBlockControllers.get(i).setRecipe(recipe);
      dayBlockControllers.get(i).setActionOnRecipeButtonClicked(recipe); // Assign action to go to recipe
    }
  }

  /**
   * Add action listeners to the buttons for searching for-and changing recipes.
   */
  private void addActionListeners() {

    // Button for searching for recipes
    this.searchButton.setOnAction(e -> {
      this.recipeComboBox.getItems().clear();
      String search = this.recipeSearchField.getText();
      ArrayList<String> results = Calendar.searchRecipes(search);
      for (int i = 0; i < results.size(); i++) {
        this.recipeComboBox.getItems().add(results.get(i));
      }
    });

    // Button for changing recipe
    this.changeRecipeButton.setOnAction(e -> {
          String recipe = this.recipeComboBox.getValue();
          if (recipe != null) {
            for (DayBlockController dayBlockController : new DayBlockController[]{
                mondayController, tuesdayController, wednesdayController, thursdayController,
                fridayController, saturdayController, sundayController}) {
              if (dayBlockController.getSelectedButton().isSelected()) {
                String date = dayBlockController.getDate();

            Calendar.changeRecipe(Date.valueOf(date), recipe);
            this.initialize();
          }
        }
    }});
  }


  /**
   * Group the radio buttons for each of the seven days of the week.
   */
  private void groupRadioButtons() {
    ToggleGroup group = new ToggleGroup();

    this.mondayController.getSelectedButton().setToggleGroup(group);
    this.tuesdayController.getSelectedButton().setToggleGroup(group);
    this.wednesdayController.getSelectedButton().setToggleGroup(group);
    this.thursdayController.getSelectedButton().setToggleGroup(group);
    this.fridayController.getSelectedButton().setToggleGroup(group);
    this.saturdayController.getSelectedButton().setToggleGroup(group);
    this.sundayController.getSelectedButton().setToggleGroup(group);
  }

  /**
   * Set the main controller for each of the day block controllers.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    // Assign main controller to the day block controllers to enable navigation to recipes
    this.mondayController.setMainController(mainController);
    this.tuesdayController.setMainController(mainController);
    this.wednesdayController.setMainController(mainController);
    this.thursdayController.setMainController(mainController);
    this.fridayController.setMainController(mainController);
    this.saturdayController.setMainController(mainController);
    this.sundayController.setMainController(mainController);
  }
}