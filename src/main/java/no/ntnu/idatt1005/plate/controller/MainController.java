package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import no.ntnu.idatt1005.plate.MyApp;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

/**
 * Main controller class handles the logic across all the views of the application.
 * The purpose of this class is to manage the controllers for each separate view
 * as well as the toolbar on a high level.
 */
public class MainController {



  private void initialize() {

  }

  private void initializeToolbar() {
    FXMLLoader toolbarLoader = new FXMLLoader(getClass().getResource("/fxml/toolbar/Toolbar.fxml"));
  }

  /**
   * Load the initial view. Only called once when the application starts.
   *
   * @param stage the primaryStage
   */
  public void loadInitialView(Stage stage) {
    try {
      FXMLLoader homeLoader = new FXMLLoader(MyApp.class.getResource("/fxml/UiHome.fxml"));
      Parent homeView = homeLoader.load();

      Scene scene = new Scene(homeView, 800, 800);
      stage.setScene(scene);

      stage.setTitle("Plate 1.0");
      stage.setMinWidth(300);
      stage.setMinHeight(300);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Go to the home view.
   */
  public void goToHome() {
    try {
      FXMLLoader homeLoader = new FXMLLoader(MyApp.class.getResource("/fxml/UiHome.fxml"));
      Parent homeView = homeLoader.load();

      Scene scene = new Scene(homeView, 800, 800);
      Stage currentStage = (Stage) MyApp.getPrimaryStage();
      currentStage.setScene(scene);

      currentStage.setTitle("Plate 1.0");
      currentStage.setMinWidth(300);
      currentStage.setMinHeight(300);
      currentStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Go to the inventory view.
   */
  public void goToInventory() {
    try {
      FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("/fxml/UiInventory.fxml"));
      Parent inventoryView = inventoryLoader.load();

      Scene scene = new Scene(inventoryView);
      Stage currentStage = (Stage) MyApp.getPrimaryStage();
      currentStage.setScene(scene);
      //Stage stage = (Stage) inventory.getScene().getWindow();
      currentStage.show();

    } catch (Exception e) {

      e.printStackTrace();
    }
  }

  public void goToShoppingList() {
    // Go to shopping list
  }

  public void goToSettings() {
    // Go to settings
  }

}
