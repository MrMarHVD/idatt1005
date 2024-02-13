package no.ntnu.idatt1005.plate.controller;

import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

public class UiInventoryController {

  @FXML private MainController mainController;

  @FXML private ToolbarController toolbarController;

  public void initialize() {

  }

  /**
   * Set the main controller for this class.
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
