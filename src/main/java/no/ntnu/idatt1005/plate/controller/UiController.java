package no.ntnu.idatt1002.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class is the controller for the user interface, mediating between the model and view
 * layers.
 */
public class UiController {
  @FXML
  private Button testButton;

  // Define an event handler method for your button
  @FXML
  private void handleButtonAction() {
    // Your logic here
    System.out.println("Button clicked!");
  }


}
