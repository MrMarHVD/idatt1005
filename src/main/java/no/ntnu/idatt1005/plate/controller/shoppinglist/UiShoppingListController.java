package no.ntnu.idatt1005.plate.controller.shoppinglist;

import static java.lang.Integer.parseInt;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;
import no.ntnu.idatt1005.plate.model.JsonReader;
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

    List<ShoppingListItem> shoppingListItemList = JsonReader.getShoppingList();

    items.addAll(shoppingListItemList);

    if (listView != null) {
      listView.setItems(items);
    }
  }

  /**
   * add items to the shopping list.
   */
  public void addItems() {
    items.add(
            new ShoppingListItem(
                    JsonReader.getIngredientIdByName(
                            itemNameField.getText()),
                    parseInt(itemAmountField.getText())
            )
    );
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
