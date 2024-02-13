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

/**
 * Main controller class handles the logic across all the views of the application.
 * The purpose of this class is to manage the controllers for each separate view
 * as well as the toolbar on a high level.
 */
public class MainController {

  private void initialize() {

  }

  private void initializeToolbar() {
    FXMLLoader toolbarLoader = new FXMLLoader(getClass().getResource("/fxml/toolbar/Toolbar.fxml"));
  }

  private void goToHome() {
    // Respond to clicking the home button
  }

  private void goToInventory() {
    // Respond to clicking the inventory button
  }

  private void goToShoppingList() {
    // Respond to clicking the shopping list button
  }

  private void goToSettings() {
    // Respond to clicking the settings button
  }

}
