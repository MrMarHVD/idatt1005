package no.ntnu.idatt1005.plate;

import no.ntnu.idatt1005.plate.controller.global.MainController;

import javafx.application.Application;
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

    primaryStage.setResizable(true);



    // Assign the mainController for the app (remains the same throughout the runtime)
    MainController mainController = new MainController();

    // Set the primaryStage such that i can be accessed from other classes
    MyApp.primaryStage = primaryStage;

    // Load initial view (home view)
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
