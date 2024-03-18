package no.ntnu.idatt1005.plate.controller.shoppinglist;

import static java.lang.Integer.parseInt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.ShoppingListItem;


/**
 * Controller class for the shopping list view.

 */
public class UiShoppingListController {

  @FXML
  private ListView<ShoppingListItem> listView;

  @FXML
  private TextField itemNameField;

  @FXML
  private TextField itemAmountField;

  private ObservableList<ShoppingListItem> items;

  @FXML
  private MainController mainController;

  @FXML
  private ToolbarController toolbarController;


  /**
   * Initialize the controller.

   */
  @FXML public void initialize() {
    if (mainController != null) {
      this.setMainController(mainController);
    }
    items = FXCollections.observableArrayList();


    if (listView != null) {
      listView.setItems(items);
    }
  }

  /**
   * add items to the shopping list.
   */
  public void addItems() {
    // Add items using database
  }



  /**
   * Set the main controller for this class and its toolbar controller.
   *
   * @param mainController the main controller.
   */
  @FXML public void setMainController(MainController mainController) {
    this.mainController = mainController;

    if (toolbarController != null) {
      toolbarController.setMainController(mainController);
    }
  }

}
