package no.ntnu.idatt1005.plate.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.CookBook;

import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.data.SqlConnector;

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
   * @param recipe the recipe to add a button for.
   */
  public void addRecipeButton(String recipe) {

    Button button = new Button(recipe);
    button.setPrefWidth(100);
    button.setPrefHeight(50);
    button.setMaxHeight(75);
    button.setMaxWidth(125);


    recipeButtons.add(button);
    updateRecipeButtons();
    button.setOnAction(event -> {

      System.out.println("Going to recipe: " + recipe);
      if (this.mainController != null) {
        this.mainController.goToRecipe(recipe);
      }
    });
  }

  /**
   * Update the recipe buttons in the grid with the current list of recipe buttons.
   */
  public void updateRecipeButtons() {
    if (gridPane != null) {
      gridPane.getChildren().clear();
      for (int i = 0; i < recipeButtons.size(); i++) {
        gridPane.add(recipeButtons.get(i), i % 4, i / 4);
        Button button = recipeButtons.get(i);
        GridPane.setHalignment(button, HPos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
      }
    } else {
      System.out.println("gridPane is null in updateRecipeButtons");
    }
  }

}
