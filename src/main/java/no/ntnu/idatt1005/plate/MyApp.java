package no.ntnu.idatt1005.plate;

import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.view.MyWindow;
import no.ntnu.idatt1005.plate.model.Ingredient;
import no.ntnu.idatt1005.plate.model.Recipe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Use this class to start the application.
 *
 * @author nilstes
 */
public class MyApp extends Application {

  static Stage primaryStage;
  /**
   * Start method
   *
   * @param primaryStage
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

  MainController mainController = new MainController();

  MyApp.primaryStage = primaryStage;
  mainController.loadInitialView(MyApp.getPrimaryStage());

  }

  /**
   * Get the primaryStage.
   *
   * @return primaryStage.
   */
  public static Stage getPrimaryStage() {
    return MyApp.primaryStage; // Public getter for the primary stage
  }

    /**
     * Main method for testing
     */
    public static void main(String[] args) throws Exception {
      Application.launch(args);

   }  
}
