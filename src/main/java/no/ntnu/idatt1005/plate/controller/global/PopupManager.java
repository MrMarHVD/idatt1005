package no.ntnu.idatt1005.plate.controller.global;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PopupManager {

  /**
   * Show an alert indicating something other than an error.
   *
   * @param title title of the alert
   * @param header header of the alert
   * @param content content of the alert
   */
  public static void displayPopup(String title, String header, String content) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public static void displayError(String title,  String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
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
