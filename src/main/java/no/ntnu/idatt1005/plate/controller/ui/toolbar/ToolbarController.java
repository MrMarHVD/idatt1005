package no.ntnu.idatt1005.plate.controller.ui.toolbar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import no.ntnu.idatt1005.plate.controller.global.MainController;

/**
 * Controller class for the toolbar in the GUI.
 *
 * @version 1.0
 */
public class ToolbarController {

  /**
   * Main controller for this class.
   */
  @FXML private MainController mainController;

  /**
   * The button for going to the home view.
   */
  @FXML private Button home;

  /**
   * The button for going to the cookbook view.
   */
  @FXML private Button cookbook;

  /**
   * The button for going to the inventory view.
   */
  @FXML private Button inventory;

  /**
   * The button for going to the shopping list view.
   */
  @FXML private Button shoppingList;

  /**
   * The button for going to the settings view.
   */
  @FXML private Button settings;


  /**
   * Initialize the controller.
   */
  @FXML public void initialize() {

  }

  /**
   * Set the main controller for this class.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * Respond to pressing the home button.
   * 'onHomeButtonPressed' is set as the action for the home button in the FXML file.
   *
   * @param event The event that was triggered.
   */
  @FXML
  public void onHomeButtonPressed(ActionEvent event) {
    if (this.mainController != null) {
      this.mainController.goToHome();
    }
  }

  /**
   * Respond to pressing the inventory button.
   *
   * @param event The event that was triggered.
   */
  @FXML
  public void onInventoryButtonPressed(ActionEvent event) {
    if (this.mainController != null) {
      this.mainController.goToInventory();
    }
  }

  /**
   * Respond to pressing the cookbook button.
   *
   * @param event the event triggered.
   */
  @FXML
  public void onCookbookButtonPressed(ActionEvent event) {
    if (this.mainController != null) {
      this.mainController.goToCookbook();
    }
  }

  /**
   * Respond to pressing the shopping list button.
   *
   * @param event The event that was triggered.
   */
  @FXML
  public void onShoppingListButtonPressed(ActionEvent event) {
    if (this.mainController != null) {
      this.mainController.goToShoppingList();
    }
  }

  /**
   * Respond to pressing the settings button.
   *
   * @param event the event that was triggered.
   */
  @FXML
  public void onSettingsButtonPressed(ActionEvent event) {
    if (this.mainController != null) {
      this.mainController.goToSettings();
    }
  }

}
