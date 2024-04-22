package no.ntnu.idatt1005.plate.controller.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class handles all popups in the application.
 */
public class PopupManager {

  /**
   * Display a basic error.
   *
   * @param header the header of the popup message.
   * @param content the content of the popup message.
   */
  public static void displayError(String header,  String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Show an alert indicating an error.
   *
   * @param title title of the alert
   * @param header header of the alert
   * @param content content of the alert
   */
  public static void displayErrorFull(String title, String header, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
