package no.ntnu.idatt1005.plate.controller.calendar;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.data.SqlConnector;
import no.ntnu.idatt1005.plate.model.Calendar;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import no.ntnu.idatt1005.plate.model.ShoppingList;

/**
 * This class is the controller for the Calendar view in the user interface.
 */
public class CalendarController {

  private Date selectedDate;

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

  /**
   * The ComboBox showing a list of recipes based on search.
   */
  @FXML
  private ComboBox<String> recipeComboBox;

  /**
   * The search field for recipes.
   */
  @FXML
  private TextField recipeSearchField;

  /**
   * The button to change the recipe of the selected day to the selected recipe.
   */
  @FXML
  private Button changeRecipeButton;

  private final Calendar calendar = new Calendar(new SqlConnector());

  /**
   * The button to add all missing ingredients to the shopping list.
   */
  @FXML
  private Button addAllMissingButton;

  /**
   * The button to add all missing ingredients from the recipe planned for the selected day
   * to the shopping list.
   */
  @FXML
  private Button addMissingFromSelectedButton;

  /**
   * The list view showing the missing ingredients for the recipe corresponding to the
   * selected day.
   */
  @FXML
  private ListView<Integer> missingListView;

  /**
   * This method initializes the Calendar view with the correct recipes for each day.
   */
  public void initialize() {
    this.groupRadioButtons();
    this.addRadioButtonActionListeners();
    this.addRecipeButtonActionListeners();
    this.addShoppingListButtonActionListeners();
    this.initializeComboBox();
    //this.missingListView.setCellFactory(param -> new MissingIngredientListCell());
    LocalDate today = LocalDate.now();
    LocalDate thisMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    ArrayList<DayBlockController> dayBlockControllers = new ArrayList<>(Arrays.asList(
        mondayController, tuesdayController, wednesdayController, thursdayController,
        fridayController, saturdayController, sundayController));


    for (int i = 0; i < 7; i++) {
      LocalDate date = thisMonday.plusDays(i);
      if (!calendar.dayExists(Date.valueOf(date))) {
        calendar.insertDay(Date.valueOf(date));
      }

      String day = date.getDayOfWeek().toString().charAt(0) +
          date.getDayOfWeek().toString().substring(1).toLowerCase();
      dayBlockControllers.get(i).setDay(day);
      dayBlockControllers.get(i).setDate(date.toString());

      String recipe = calendar.getDayRecipes().get(date.toString());
      dayBlockControllers.get(i).setRecipe(recipe);
      dayBlockControllers.get(i).setActionOnRecipeButtonClicked(recipe); // Assign action to go to recipe
    }
  }

  /**
   * Initialize the combo box with the first 20 (or less) recipes in the database.
   */
  private void initializeComboBox() {
    int maxRecipes = 20;
    ArrayList<String> results = calendar.searchRecipes("");
    if (results.size() > maxRecipes) {
      for (int i = 0; i < maxRecipes; i++) {
        this.recipeComboBox.getItems().add(results.get(i));
      }
    } else {
      for (int i = 0; i < results.size(); i++) {
        this.recipeComboBox.getItems().add(results.get(i));
      }
    }
  }

  /**
   * Add action listeners to the radio buttons for each day of the week.
   */
  private void addRadioButtonActionListeners() {
    DayBlockController[] dayBlockControllers = new DayBlockController[]{
        mondayController, tuesdayController, wednesdayController, thursdayController,
        fridayController, saturdayController, sundayController};

    for (DayBlockController dayBlockController : dayBlockControllers) {
      dayBlockController.getSelectedButton().setOnAction(e -> {
        if (dayBlockController.getSelectedButton().isSelected()) {
          String date = dayBlockController.getDate();
          this.selectedDate = Date.valueOf(date); // Ensure that the selected date is stored
          String recipeName = Calendar.getDayRecipes().get(date);

          List<Integer> missingIngredients = Calendar.getMissingIngredients(recipeName);

          // Create an ObservableList with the IDs of the missing ingredients
          ObservableList<Integer> observableIngredients = FXCollections.observableArrayList(missingIngredients);

          // Set the ObservableList as the items of the ListView
          missingListView.setItems(observableIngredients);

          // Set the cell factory of the ListView to use MissingIngredientListCell
          missingListView.setCellFactory(param -> new MissingIngredientListCell(recipeName));

        }
      });
    }
  }

  /**
   * Initialize action listeners for functions relating to search and changing recipe
   * for the selected day.
   */
  private void addRecipeButtonActionListeners() {

    // Button for searching for recipes


    this.recipeSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
          this.recipeComboBox.getItems().clear();
          ArrayList<String> results = Calendar.searchRecipes(newValue);
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

            calendar.changeRecipe(Date.valueOf(date), recipe);
            this.initialize();
          }
        }
      }});

  }

  /**
   * Initialize action listeners for the buttons that add missing ingredients
   * to the shopping list.
   */
  private void addShoppingListButtonActionListeners() {
    this.addMissingFromSelectedButton.setOnAction(event -> {
      String recipeName = Calendar.getDayRecipes().get(this.selectedDate.toString());
      Map<Integer, Float> missingIngredients = Calendar.getMissingIngredientsWithQuantity(recipeName);
      for (int ingredientId : missingIngredients.keySet()) {

        // Add to shopping list only if not there already.
        if (!ShoppingList.inShoppingList(ingredientId)) {
          ShoppingList.addItem(ingredientId, missingIngredients.get(ingredientId));
        }
      }
    });

    // TODO: action listener for adding all missing ingredients to shopping list
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