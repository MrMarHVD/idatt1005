package no.ntnu.idatt1005.plate.controller.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class handles all popups in the application.
 *
 * @version 1.0
 */
public class PopupManager {

  /**
   * Display a basic error.
   *
   * @param header the header of the alert.
   * @param content the content of the alert.
   */
  public static void displayError(String header,  String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Show an alert indicating an error with both a header and title.
   *
   * @param title title of the alert.
   * @param header header of the alert.
   * @param content content of the alert.
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
