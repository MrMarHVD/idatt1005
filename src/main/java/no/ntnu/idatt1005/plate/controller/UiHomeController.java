package no.ntnu.idatt1005.plate.controller;

import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

/**
 * This class is the controller for the user interface, mediating between the model and view
 * layers.
 */
public class UiHomeController {

  @FXML
  private CalendarController calendar;

  @FXML
  private Button home;

  @FXML
  private  Button cookbook;

  @FXML
  private Button testButton;

  @FXML
  private ListView<String> cookBookListView;

  // Define an event handler method for your button
  @FXML
  private void handleButtonAction() {
    // Your logic here
    System.out.println("Button clicked!");
  }

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.initializeCookBook();
    this.addRecipes();
  }

  /**
   * Initialize cookbook components.
   */
  public void initializeCookBook() {
    testButton.setOnAction(e -> handleButtonAction());

    cookBookListView.setCellFactory(lv -> {
      ListCell<String> cell = new ListCell<>();
      Insets insets = new Insets(5, 10, 5, 10);
      cell.setPadding(insets);
      cell.textProperty().bind(cell.itemProperty());
      return cell;
    });

  }

  /**
   * Add the names of the recipes to the cookbook list view.
   */
  public void addRecipes() {
    for (Recipe recipe : CookbookMaker.createCookBook().getRecipes()) {
      cookBookListView.getItems().add(recipe.getName());
    }
  }
}
