package no.ntnu.idatt1005.plate.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import no.ntnu.idatt1005.plate.controller.MainController;
import no.ntnu.idatt1005.plate.controller.toolbar.ToolbarController;

/**
 * Controller class for the inventory view
 */
public class UiSettingsController {

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
  private RadioButton carnivoreButton;

  @FXML
  private RadioButton vegetarianButton;

  @FXML
  private RadioButton veganButton;

  @FXML
  private RadioButton pescetarianButton;

  /**
   * Initialize the controller.
   */
  public void initialize() {
    this.setMainController(mainController);

    // TODO: load the user's settings from the database or user file or something and set the buttons accordingly
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


  // methods for buttons when pressed
  // TODO: store the value of the button in the database or user file or something
  public void carnivoreButtonPressed() {
    boolean isCarnivore = carnivoreButton.isSelected();
    System.out.println("carnivore: " + isCarnivore);
  }

  public void vegetarianButtonPressed() {
    boolean isVegetarian = vegetarianButton.isSelected();
    System.out.println("vegetarian: " + isVegetarian);
  }

  public void veganButtonPressed() {
    boolean isVegan = veganButton.isSelected();
    System.out.println("vegan: " + isVegan);
  }

  public void pescetarianButtonPressed() {
    boolean isPescetarian = pescetarianButton.isSelected();
    System.out.println("pescetarian: " + isPescetarian);
  }



}