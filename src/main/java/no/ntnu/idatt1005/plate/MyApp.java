package no.ntnu.idatt1005.plate;

import atlantafx.base.theme.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.stage.Stage;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.model.Settings;


/**
 * Use this class to start the application.
 *
 * @version 1.0
 */
public class MyApp extends Application {

  /**
   * Primary stage, defined here to allow access from other classes using getter.
   */
  static Stage primaryStage;

  /**
   * Start method.
   *
   * @param primaryStage the primary stage.
   */
  @Override
  public void start(Stage primaryStage) {

    primaryStage.setResizable(true);

    // Assign the mainController for the app (remains the same throughout the runtime)
    MainController mainController = new MainController();

    // Set the primaryStage such that it can be accessed from other classes
    MyApp.primaryStage = primaryStage;

    // Set the theme for the app.
    Path configDir = Paths.get(System.getProperty("user.home")).resolve(".plate");
    Settings settings = new Settings(configDir);
    if (settings.getDarkMode()) {
      Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
    } else {
      Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
    }

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
   * Main method for testing.
   */
  public static void main(String[] args) throws Exception {
    Application.launch(args);

  }
}
