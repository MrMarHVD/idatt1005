package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Settings;

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

  /**
   * Saves the settings to the config file.
   */
  public void saveSettings() {
    Settings.saveSettings(darkModeButton.isSelected(), vegetarianButton.isSelected());
  }

}