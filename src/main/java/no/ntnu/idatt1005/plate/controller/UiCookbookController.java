package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1005.plate.controller.cookbook.GridPaneGenerator;
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
  @FXML
  private ToolbarController toolbarController;

  /**
   * Initialize the controller.
   */
  public void initialize() {

    this.setMainController(mainController);
    // TODO: Instantiate a GridPaneGenerator and implement function
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
