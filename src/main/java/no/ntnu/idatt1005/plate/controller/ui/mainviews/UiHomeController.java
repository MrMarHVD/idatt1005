package no.ntnu.idatt1005.plate.controller.ui.mainviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import no.ntnu.idatt1005.plate.controller.ui.calendar.CalendarController;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui.toolbar.ToolbarController;

/**
 * This class is the controller for the user interface, mediating between the model and view
 * layers.
 */
public class UiHomeController {

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
   * The calendar controller for the calendar in the left sidebar.
   */
  @FXML
  private CalendarController calendarController;


  /**
   * Initialize the controller.
   */
  public void initialize() {

  }

  /**
   * Set the main controller for this class, the calendar controller, and the toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
    this.toolbarController.setMainController(mainController);
    this.calendarController.setMainController(mainController);
  }

  // Define an event handler method for your button
  @FXML
  private void handleButtonAction() {
    // Your logic here
    System.out.println("Button clicked!");
  }




}
