package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;

/**
 * This class is the controller for the user interface, mediating between the model and view
 * layers.
 */
public class UiHomeController {

  @FXML
  private MainController mainController;

  @FXML
  private ToolbarController toolbarController;

  @FXML
  private CalendarController calendarController;

  @FXML
  private Button home;

  @FXML
  private Button cookbook;

  @FXML
  private Button testButton;


  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);
  }

  /**
   * Set the main controller for this class and the toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
    this.toolbarController.setMainController(mainController);
  }

  // Define an event handler method for your button
  @FXML
  private void handleButtonAction() {
    // Your logic here
    System.out.println("Button clicked!");
  }




}
