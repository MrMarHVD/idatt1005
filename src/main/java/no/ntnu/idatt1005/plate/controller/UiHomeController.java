package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javax.tools.Tool;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

/**
 * This class is the controller for the user interface, mediating between the model and view
 * layers.
 */
public class UiHomeController {

  @FXML
  private MainController mainController;

  @FXML
  private ToolbarController toolbarController;

  @FXML
  private CalendarController calendarController;

  @FXML
  private Button home;

  @FXML
  private Button cookbook;

  @FXML
  private Button testButton;

  @FXML
  private ListView<String> cookBookListView;

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);

    this.initializeCookBook();
    this.addRecipes();
  }

  /**
   * Set the main controller for this class.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
    this.toolbarController.setMainController(mainController);
  }

  // Define an event handler method for your button
  @FXML
  private void handleButtonAction() {
    // Your logic here
    System.out.println("Button clicked!");
  }


  public void initializeToolbar() {
    FXMLLoader toolbarLoader = new FXMLLoader(getClass().getResource("/fxml/toolbar/Toolbar.fxml"));
  }

  /**
   * Initialize cookbook components. (temporary)
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
