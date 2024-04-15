package no.ntnu.idatt1005.plate.controller.ui_mainviews;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Settings;

import java.nio.file.Path;
import java.nio.file.Paths;

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

  private final Path configDir = Paths.get(System.getProperty("user.home")).resolve(".plate");
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

    if (toolbarController != null)
      toolbarController.setMainController(mainController);

  }

  /**
   * Saves the settings to the config file.
   */
  public void saveSettings() {
    settings.saveSettings(darkModeButton.isSelected(), vegetarianButton.isSelected());
    updateTheme();
  }

  private void updateTheme() {
    if (settings.getDarkMode()) {

      String cupertinoDarkStylesheet = new CupertinoDark().getUserAgentStylesheet();
//      String customStylesheet = getClass().getResource("C:\\Users\\konra\\Documents\\Skole\\Semester 2\\Systemutvikling\\Prosjekt\\purchase-planner\\src\\main\\resources\\styles\\plateStyleDark.css").toExternalForm();
      String combinedStylesheet = cupertinoDarkStylesheet + "\n" ;

      Application.setUserAgentStylesheet(combinedStylesheet);
    }  else {
      Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
    }
  }

}