package no.ntnu.idatt1005.plate.controller.toolbar;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import no.ntnu.idatt1005.plate.MyApp;
import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

public class ToolbarController {

  /**
   * Main controller for this class.
   */
  @FXML private MainController mainController;

  @FXML private Button home;

  @FXML private Button cookbook;

  @FXML private Button inventory;

  @FXML private Button shoppingList;

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
   *
   * @param event The event that was triggered.
   */
  @FXML
  public void onHomeButtonPressed(ActionEvent event) {
    // TODO: Implement this method
  }

  /**
   * Respond to pressing the inventory button.
   *
   * @param event The event that was triggered.
   */
  @FXML
  public void onInventoryButtonPressed(ActionEvent event) {
    Node source = (Node) event.getSource();
    if (this.mainController != null) {
      this.mainController.goToInventory();
    }
  }

  @FXML
  public void onCookbookButtonPressed(ActionEvent event) {
    // TODO: Implement this method
  }

  @FXML
  public void onShoppingListButtonPressed(ActionEvent event) {
    // TODO: Implement this method
  }

  @FXML
  public void onSettingsButtonPressed(ActionEvent event) {
    // TODO: Implement this method
  }

}
