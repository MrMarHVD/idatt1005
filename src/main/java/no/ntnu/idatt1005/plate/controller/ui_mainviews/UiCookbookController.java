package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;

public class UiCookbookController {

  /**
   * The main controller for the application.
   */
  @FXML
  private MainController mainController;

  /**
   * The toolbar controller for this view.
   */
  @FXML private ToolbarController toolbarController;

  /**
   * The grid containing buttons for each recipe.
   */
  @FXML private GridPane gridPane;

  @FXML private TextField searchField;

  @FXML private Button sortButton;
  /**
   * The default recipe button.
   */
  //@FXML private Button recipeButton;

  /**
   * The buttons for each recipe.
   */
  private ArrayList<Button> recipeButtons = new ArrayList<>();

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect("SELECT name FROM recipe");
      while (rs.next()) {
        String name = rs.getString("name");
        addRecipeButton(name);
      }
    } catch (Exception e) {
      e.getMessage();
    }

    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
      searchCookbook(newValue);
    });

    sortButton.setOnAction(event -> {
      sortByName();
    });

  }

  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null) {
      toolbarController.setMainController(mainController);
    }
  }

  /**
   * Add a recipe button for the given recipe.
   *
   * @param recipe the recipe to add a button for.
   */
  public void addRecipeButton(String recipe) {

    Button button = new Button(recipe);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


    recipeButtons.add(button);
    updateRowConstraints();
    updateRecipeButtons();
    button.setOnAction(event -> {

      System.out.println("Going to recipe: " + recipe);
      if (this.mainController != null) {
        this.mainController.goToRecipe(recipe);
      }
    });
  }

  /**
   * Search the cookbook for the given input.
   * @param input
   */
  private void searchCookbook(String input) {
    recipeButtons.clear();
    if (gridPane != null) {
      gridPane.getChildren().clear();
    }
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect("SELECT name FROM recipe WHERE name LIKE '%" + input + "%'");
      while (rs.next()) {
        String name = rs.getString("name");
        addRecipeButton(name);
      }
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Sort the cookbook by name.
   */
  private void sortByName() {
    recipeButtons.clear();
    if (gridPane != null) {
      gridPane.getChildren().clear();
    }
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect("SELECT name FROM recipe ORDER BY name");
      while (rs.next()) {
        String name = rs.getString("name");
        addRecipeButton(name);
      }
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Update the recipe buttons in the grid with the current list of recipe buttons.
   */
  public void updateRecipeButtons() {
    if (gridPane != null) {
      gridPane.getChildren().clear();
      for (int i = 0; i < recipeButtons.size(); i++) {
        gridPane.add(recipeButtons.get(i), i % 3, i / 3);
        Button button = recipeButtons.get(i);
        GridPane.setHalignment(button, HPos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
      }
    } else {
      System.out.println("gridPane is null in updateRecipeButtons");
    }
  }

  /**
   * Update the row constraints for the grid to make all buttons fixed size.
   */
  private void updateRowConstraints() {
    gridPane.getRowConstraints().clear();

    for (int i = 0; i < gridPane.getChildren().size(); i++) {
      RowConstraints rowConstraints = new RowConstraints(60, 60, 60);
      rowConstraints.setVgrow(Priority.NEVER); // Sørger for at radene ikke vokser utover angitt høyde
      gridPane.getRowConstraints().add(rowConstraints);
    }
  }

}
