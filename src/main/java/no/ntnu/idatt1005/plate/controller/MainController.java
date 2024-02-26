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
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

// TODO: improve error handling (i.e. no printStackTrace)

/**
 * Main controller class handles the logic across all the views of the application.
 * The purpose of this class is to manage the controllers for each separate view
 * as well as the toolbar on a high level.
 */
public class MainController {

  private void initialize() {

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

      // Set the main controller for the home controller
      UiHomeController homeController = homeLoader.getController();
      homeController.setMainController(this);

      Scene scene = new Scene(homeView);
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

      UiHomeController homeController = homeLoader.getController();
      homeController.setMainController(this);

      Scene scene = new Scene(homeView);
      Stage currentStage = MyApp.getPrimaryStage();
      currentStage.setScene(scene);

      currentStage.setTitle("Plate 1.0");

      currentStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Go to the cookbook view.
   */
  public void goToCookbook() {
    try {
      FXMLLoader cookbookLoader = new FXMLLoader(getClass().getResource("/fxml/UiCookbook.fxml"));
      Parent cookbookView = cookbookLoader.load();

      // Instantiate the cookbook controller
      UiCookbookController cookbookController = cookbookLoader.getController();

      // Set the main controller for the cookbook controller
      cookbookController.setMainController(this);

      Scene scene = new Scene(cookbookView);
      Stage currentStage = MyApp.getPrimaryStage();
      currentStage.setScene(scene);
      currentStage.show();
  }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Go to the inventory view.
   */
  public void goToInventory() {
    System.out.println("Test");
    try {
      FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("/fxml/UiInventory.fxml"));
      Parent inventoryView = inventoryLoader.load();

      // Instantiate the inventory controller
      UiInventoryController inventoryController = inventoryLoader.getController();

      // Set the main controller for the inventory controller
      inventoryController.setMainController(this);

      Scene scene = new Scene(inventoryView);
      Stage currentStage = MyApp.getPrimaryStage();
      currentStage.setScene(scene);
      //Stage stage = (Stage) inventory.getScene().getWindow();
      currentStage.show();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Go to the shopping list view.
   */
  public void goToShoppingList() {
    try {
      FXMLLoader shoppingListLoader = new FXMLLoader(getClass().getResource("/fxml/UiShoppingList.fxml"));
      Parent shoppingListView = shoppingListLoader.load();

      // Instantiate the shopping list controller
      UiShoppingListController shoppingListController = shoppingListLoader.getController();

      // Set the main controller for the shopping list controller
      shoppingListController.setMainController(this);

      Scene scene = new Scene(shoppingListView);
      Stage currentStage = MyApp.getPrimaryStage();
      currentStage.setScene(scene);
      currentStage.show();


    } catch (Exception e) {

      e.printStackTrace();
    }
  }

  /**
   * Go to the settings view.
   */
  public void goToSettings() {
    // Go to settings
  }

}
