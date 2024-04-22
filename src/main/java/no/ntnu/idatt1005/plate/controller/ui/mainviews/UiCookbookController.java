package no.ntnu.idatt1005.plate.controller.ui.mainviews;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.Settings;

/**
 * Controller class for the cookbook view in the GUI.
 */
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

  @FXML private Button addButton;

  @FXML private TextField recipeNameTextField;

  /**
   * The buttons for each recipe.
   */
  private ArrayList<Button> recipeButtons = new ArrayList<>();

  private boolean isSortedAsc = false;

  private final Path configDir = Paths.get(System.getProperty("user.home")).resolve(".plate");
  private final Settings settings = new Settings(configDir);

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT name, vegetarian FROM recipe");
      while (rs.next()) {
        if (rs.getInt("vegetarian") == 1 && settings.getVegetarian()
            || !settings.getVegetarian()) {
          String name = rs.getString("name");
          addRecipeButton(name);
        }
      }
    } catch (Exception e) {
      e.getMessage();
    }

    this.addButton.setOnAction(event -> {
      if (this.recipeNameTextField.getText().isEmpty()) {
        PopupManager.displayErrorFull("Recipe name cannot be empty", "Please enter a recipe name","Recipe name cannot be empty, please enter a recipe name");
        return;
      }
      Recipe.createRecipe(this.recipeNameTextField.getText());
      if (this.mainController != null) {
        this.mainController.goToRecipe(this.recipeNameTextField.getText());
      }
    });

    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
      searchCookbook(newValue);
    });

    sortButton.setOnAction(event -> {
      if (isSortedAsc) {
        sortDesc();
        sortButton.setText("A - Z");
      } else {
        sortAsc();
        sortButton.setText("Z - A");
      }
      isSortedAsc = !isSortedAsc;
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
    button.setStyle("-fx-wrap-text: true; -fx-text-alignment: CENTER");


    recipeButtons.add(button);
    updateRowConstraints();
    updateRecipeButtons();
    button.setOnAction(event -> {

      if (this.mainController != null) {
        this.mainController.goToRecipe(recipe);
      }
    });
  }

  /**
   * Search the cookbook for the given input.
   *
   * @param input the search input string.
   */
  private void searchCookbook(String input) {
    recipeButtons.clear();
    if (gridPane != null) {
      gridPane.getChildren().clear();
    }
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT name, vegetarian FROM recipe WHERE name LIKE '%" + input + "%'");
      while (rs.next()) {
        if (rs.getInt("vegetarian") == 1 && settings.getVegetarian()
            || !settings.getVegetarian()) {
          String name = rs.getString("name");
          addRecipeButton(name);
        }
      }
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Sort the cookbook by name in ascending order.
   */
  private void sortAsc() {
    recipeButtons.clear();
    if (gridPane != null) {
      gridPane.getChildren().clear();
    }
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT name, vegetarian FROM recipe ORDER BY name");
      while (rs.next()) {
        if (rs.getInt("vegetarian") == 1 && settings.getVegetarian() || !settings.getVegetarian()) {
          String name = rs.getString("name");
          addRecipeButton(name);
        }
      }
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Sort the cookbook by name in descending order.
   */
  private void sortDesc() {
    recipeButtons.clear();
    if (gridPane != null) {
      gridPane.getChildren().clear();
    }
    try {
      ResultSet rs = MainController.sqlConnector.executeSqlSelect(
          "SELECT name, vegetarian FROM recipe ORDER BY name DESC");
      while (rs.next()) {
        if (rs.getInt("vegetarian") == 1 && settings.getVegetarian()
            || !settings.getVegetarian()) {
          String name = rs.getString("name");
          addRecipeButton(name);
        }
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
      PopupManager.displayError("Error", "gridPane is null in updateRecipeButtons");
    }
  }

  /**
   * Update the row constraints for the grid to make all buttons fixed size.
   */
  private void updateRowConstraints() {
    gridPane.getRowConstraints().clear();

    for (int i = 0; i < gridPane.getChildren().size(); i++) {
      RowConstraints rowConstraints = new RowConstraints(60, 60, 60);
      rowConstraints.setVgrow(Priority.NEVER);
      gridPane.getRowConstraints().add(rowConstraints);
    }
  }

}
