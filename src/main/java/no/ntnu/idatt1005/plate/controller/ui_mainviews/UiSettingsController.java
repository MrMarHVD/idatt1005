package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;

/**
 * Controller class for the inventory view
 */
public class UiSettingsController {

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

  @FXML
  private ToggleButton darkModeButton;

  @FXML
  private ToggleButton vegetarianButton;

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);

    // TODO: load the user's settings from the database or user file or something and set the buttons accordingly
  }

  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null)
      toolbarController.setMainController(mainController);

  }

  public void darkModeButtonPressed() {
    boolean isCarnivore = darkModeButton.isSelected();
    System.out.println("dark mode: " + isCarnivore);
  }

  public void vegetarianButtonPressed() {
    boolean isVegetarian = vegetarianButton.isSelected();
    System.out.println("vegetarian: " + isVegetarian);
  }

}