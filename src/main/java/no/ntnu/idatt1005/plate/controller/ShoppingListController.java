package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import no.ntnu.idatt1005.plate.controller.calendar.CalendarController;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

public class ShoppingListController {

  @FXML private MainController mainController;

  @FXML public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

}
