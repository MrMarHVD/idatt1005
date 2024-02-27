package no.ntnu.idatt1005.plate.controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.Ingredient;
import no.ntnu.idatt1005.plate.model.JsonReader;

/**
 * Controller class for the inventory view
 */
public class UiInventoryController {

  /**
   * The main controller for the application.
   */
  @FXML
  private MainController mainController;

  /**
   * The toolbar controller for this view.
   */
  @FXML
  private ToolbarController toolbarController;

  @FXML
  private ListView<String> ingredientListView;

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);

    if (ingredientListView != null) {
      this.displayIngredients();
    } else {
      System.out.println("ingredientListView is null");
    }
  }

  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null)
      toolbarController.setMainController(mainController);

  }

  /**
   * Display all ingredients in the inventory.
   */
  public void displayIngredients() {
    List<Ingredient> allIngredients = JsonReader.getInventoryIngredients();
    ObservableList<Ingredient> observableIngredients = FXCollections.observableArrayList(allIngredients);
    ObservableList<String> ingredientNames = FXCollections.observableArrayList();
    for (Ingredient ingredient : allIngredients) {
      ingredientNames.add(ingredient.getName());
    }
    ingredientListView.setItems(ingredientNames);
  }

}
