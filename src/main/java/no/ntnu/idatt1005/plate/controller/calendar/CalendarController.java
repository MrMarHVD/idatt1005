package no.ntnu.idatt1005.plate.controller.calendar;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javax.swing.text.html.ImageView;
import no.ntnu.idatt1005.plate.model.Recipe;
import no.ntnu.idatt1005.plate.model.CookbookMaker;

public class CalendarController {

  public HBox thursday;
  @FXML
  private DayBlockController mondayController;

  @FXML
  private DayBlockController tuesdayController;

  @FXML
  private DayBlockController wednesdayController;

  @FXML
  private DayBlockController thursdayController;

  @FXML
  private DayBlockController fridayController;

  @FXML
  private DayBlockController saturdayController;

  @FXML
  private DayBlockController sundayController;

  // Initialize controllers for each of the days (not currently working)
  /*
  public void initializeControllers() {
    try {
      // Load the DayBlock FXML and get its controller for Monday
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/calendar/DayBlock.fxml"));
      HBox mondayBox = loader.load(); // This HBox is the root of your DayBlock.fxml
      mondayController = loader.getController();

      // Now, you can add mondayBox to the scene or a specific parent if needed
      // For example, if mondayHBox is a placeholder in your Calendar.fxml
      mondayBox.getChildren().add(mondayBox);

      // You can now use mondayController to manipulate the DayBlock
    } catch (Exception e) {
      e.printStackTrace();
    }
  }*/


}
