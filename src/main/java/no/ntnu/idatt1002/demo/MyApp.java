package no.ntnu.idatt1002.demo;

import no.ntnu.idatt1002.demo.view.MyWindow;
import no.ntnu.idatt1002.demo.model.Ingredient;
import no.ntnu.idatt1002.demo.model.Recipe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Use this class to start the application.
 *
 * @author nilstes
 */
public class MyApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      // Load the FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource(
          "/no/ntnu/idatt1002/demo/view/UI.fxml"));
      Parent root = loader.load();

      // Set the scene to the stage and configure the primary stage
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Purchase Planner");

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
        MyWindow window = new MyWindow("The Window");
        window.setVisible(true);
        System.out.println("Hei");

      Ingredient eggs = new Ingredient("eggs");
      Ingredient flour = new Ingredient("flour");
      Ingredient milk = new Ingredient("milk");

      Recipe bread = new Recipe("Add the eggs and milk, then mix with flour.",
          flour, milk, eggs);

      System.out.println(bread.toString());
   }  
}
