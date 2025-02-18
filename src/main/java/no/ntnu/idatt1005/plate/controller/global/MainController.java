package no.ntnu.idatt1005.plate.controller.global;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1005.plate.MyApp;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiCookbookController;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiHomeController;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiInventoryController;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiRecipeViewController;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiSettingsController;
import no.ntnu.idatt1005.plate.controller.ui.mainviews.UiShoppingListController;
import no.ntnu.idatt1005.plate.controller.utility.PopupManager;
import no.ntnu.idatt1005.plate.data.SqlConnector;

/**
 * Main controller class handles the logic across all the views of the application.
 * The purpose of this class is to manage the controllers for each separate view
 * as well as the toolbar on a high level.
 *
 * @version 1.0
 */
public class MainController {
  public static SqlConnector sqlConnector = new SqlConnector();

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
      e.printStackTrace(); // Print stack trace if initial view fails to load. (Application crashes)
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
      PopupManager.displayError("Error", "Could not load home view.");
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
    } catch (Exception e) {
      PopupManager.displayError("Error", "Could not load cookbook view.");
    }
  }

  /**
   * Go to the inventory view.
   */
  public void goToInventory() {
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
      PopupManager.displayError("Error", "Could not load inventory view.");
    }
  }

  /**
   * Go to the shopping list view.
   */
  public void goToShoppingList() {
    try {
      FXMLLoader shoppingListLoader = new FXMLLoader(getClass().getResource(
          "/fxml/UiShoppingList.fxml"));
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
      PopupManager.displayError("Error", "Could not load shopping list view.");
    }
  }

  /**
   * Go to the settings view.
   */
  public void goToSettings() {
    try {
      FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/fxml/UiSettings.fxml"));
      Parent settingsView = settingsLoader.load();

      // Instantiate the shopping list controller
      UiSettingsController settingsController = settingsLoader.getController();

      // Set the main controller for the shopping list controller
      settingsController.setMainController(this);

      Scene scene = new Scene(settingsView);
      Stage currentStage = MyApp.getPrimaryStage();
      currentStage.setScene(scene);
      settingsController.updateView();

      currentStage.show();


    } catch (Exception e) {
      PopupManager.displayError("Error", "Could not load settings view.");
    }
  }

  /**
   * Go to a specific recipe view.
   *
   * @param recipe the recipe to go to.
   */
  public void goToRecipe(String recipe) {
    try {
      FXMLLoader recipeLoader = new FXMLLoader(getClass().getResource("/fxml/UiRecipeView.fxml"));
      Parent recipeView = recipeLoader.load();

      // Instantiate the recipe controller
      UiRecipeViewController recipeController = recipeLoader.getController();

      // Set the main controller for the recipe controller
      recipeController.setMainController(this);

      if (recipe != null) {
        recipeController.setRecipeName(recipe);
      }


      Scene scene = new Scene(recipeView);
      Stage currentStage = MyApp.getPrimaryStage();
      currentStage.setScene(scene);
      currentStage.show();
      recipeController.initializeDisplay();

    } catch (Exception e) {
      PopupManager.displayError("Error", "Could not load settings view.");
    }
  }
}
