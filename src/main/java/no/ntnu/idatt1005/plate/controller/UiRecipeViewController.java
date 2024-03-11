package no.ntnu.idatt1005.plate.controller;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import no.ntnu.idatt1005.plate.controller.cookbook.GridPaneGenerator;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import javafx.fxml.FXML;
import no.ntnu.idatt1005.plate.model.Ingredient;

/**
 * Controller class for the recipe view
 */
public class UiRecipeViewController {

  /**
   * The main controller for this class.
   */
  @FXML
  private MainController mainController;

  /**
   * The toolbar controller for this class.
   */
  @FXML
  private ToolbarController toolbarController;

  /**
   * The instructions for the recipe's preparation.
   * TODO: Implement the code to fill the instructions field.
   */
  @FXML
  private TextField instructions;

  /**
   * The GridPane for displaying recommended recipes.
   * TODO: Either implement the code to fill the recommended recipes or drop this feature.
   */
  @FXML
  private GridPane recommendedRecipes;

  /**
   * The list view for displaying ingredients in the recipe
   * and their relevant properties.
   * TODO: Add a custom cell factory for the list view.
   */
  @FXML
  private ListView<Ingredient> ingredients;

  /**
   * Initialize the controller.
   */
  @FXML
  private void initialize() {

    this.setMainController(mainController);
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


}
