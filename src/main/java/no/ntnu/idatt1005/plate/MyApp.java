package no.ntnu.idatt1005.plate;

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

  /**
   * Start method
   *
   * @param primaryStage
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    try {

      // Load the FXML file.
      String FXML_PATH = "/fxml/UiHome.fxml";
      FXMLLoader loader = new FXMLLoader(MyApp.class.getResource(FXML_PATH));

      /*
      Note: I had massive issues getting the application window to open and repeatedly got the error
      "Location is not set" when trying to load the FXML file. I tried many different solutions, and
      the final solution ended up being setting the scene = to null prior to instantiating it and
      including the width and height parameters in the scene constructor. No idea why.
       */
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
      primaryStage.setTitle("Purchase Planner");
      primaryStage.setMinWidth(300);
      primaryStage.setMinHeight(300);

      // Show the primary stage
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

    /**
     * Main method for testing
     */
    public static void main(String[] args) throws Exception {
      Application.launch(args);
      System.out.println("Hei");

      Ingredient eggs = new Ingredient("eggs");
      Ingredient flour = new Ingredient("flour");
      Ingredient milk = new Ingredient("milk");

      Recipe bread = new Recipe("Bread", "Add the eggs and milk, then mix with flour.",
          flour, milk, eggs);

      System.out.println(bread.toString());
   }  
}
