package no.ntnu.idatt1005.plate.controller.inventory;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import no.ntnu.idatt1005.plate.controller.UiInventoryController;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Ingredient;
import no.ntnu.idatt1005.plate.model.JsonReader;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

/**
 * Controller class for the specific subsection of the inventory view
 * relating to the display and handling of ingredients.
 */

public class InventoryController {

  /**
   * The parent controller.
   */
  @FXML
  private UiInventoryController mainController;

  /**
   * The list view for displaying ingredients.
   */
  @FXML
  private ListView<Ingredient> ingredientListView;

  /**
   * Initialize the controller.
   */
  public void initialize() {

  }

  /**
   * Set the parent controller.
   *
   * @param mainController the parent controller (UiInventoryController).
   */
  public void setMainController(UiInventoryController mainController) {
    this.mainController = mainController;
  }

  /**
   * Display all ingredients in the inventory.
   */
  public void displayIngredients() {
    List<Ingredient> allIngredients = JsonReader.getAllIngredients();
    ObservableList<Ingredient> observableIngredients = FXCollections.observableArrayList(allIngredients);
    ingredientListView.setItems(observableIngredients);
  }

}
