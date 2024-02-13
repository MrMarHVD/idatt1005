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
    /*try {

      // Load the FXML file.
      String FXML_PATH = "/fxml/UiHome.fxml";
      FXMLLoader loader = new FXMLLoader(MyApp.class.getResource(FXML_PATH));
      //System.out.println(MyApp.class.getResource(FXML_PATH));

      Scene scene = null;

      // Load the controller.
      //UiController controller = loader.getController();
      try {
        scene = new Scene(loader.load(), 800, 800);

      } catch (Exception e)  {
        e.printStackTrace();
      }
      // Set the scene to the stage and configure the primary stage

      primaryStage.setScene(scene);
      primaryStage.setTitle("Plate 1.0");
      primaryStage.setMinWidth(300);
      primaryStage.setMinHeight(300);

      // Show the primary stage
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }*/
  MainController mainController = new MainController();
  mainController.loadInitialView(primaryStage);


  }

  /**
   * Get the primaryStage.
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
