package no.ntnu.idatt1005.plate.controller.ui.mainviews;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.ui.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Settings;

/**
 * Controller class for the inventory view.
 *
 * @version 1.0
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

  /**
   * The button to turn dark mode on or off.
   */
  @FXML
  private ToggleButton darkModeButton;

  /**
   * The button to turn vegetarian mode on or off.
   */
  @FXML
  private ToggleButton vegetarianButton;

  /**
   * The config directory (set to the user's home directory).
   */
  private final Path configDir = Paths.get(System.getProperty("user.home")).resolve(".plate");

  /**
   * Assign the data found in the config directory, to the settings.
   */
  private final Settings settings = new Settings(configDir);

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);
    darkModeButton.setSelected(settings.getDarkMode());
    vegetarianButton.setSelected(settings.getVegetarian());
  }

  public void updateView() {
    darkModeButton.setSelected(settings.getDarkMode());
    vegetarianButton.setSelected(settings.getVegetarian());
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
   * Saves the settings to the config file.
   */
  public void saveSettings() {
    settings.saveSettings(darkModeButton.isSelected(), vegetarianButton.isSelected());
    updateTheme();
  }

  /**
   * Update the theme for the application.
   */
  private void updateTheme() {
    if (settings.getDarkMode()) {

      String cupertinoDarkStylesheet = new CupertinoDark().getUserAgentStylesheet();

      Application.setUserAgentStylesheet(cupertinoDarkStylesheet);
    }  else {
      Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
    }
  }

}