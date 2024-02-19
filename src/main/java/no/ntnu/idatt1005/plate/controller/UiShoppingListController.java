package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;

/**
 * Controller class for the shopping list view

 */
public class UiShoppingListController {

  @FXML private MainController mainController;

  @FXML
  private ToolbarController toolbarController;

  /**
   * Initialize the controller.

   */
  public void initialize() {
    this.setMainController(mainController);
  }

  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  @FXML public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null) {
      toolbarController.setMainController(mainController);
    }
  }

}
