package no.ntnu.idatt1005.plate.controller.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import no.ntnu.idatt1005.plate.model.Calendar;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;

/**
 * This class is the controller for the Today view in the homepage.
 */
public class TodayController {

  @FXML
  private Label mealNameLabel;

  @FXML
  private ImageView mealImageView;

  public void initialize() {
    setMealName();
    updateMealImage("/images/pancakes.jpg");
  }

  public void setMealName() {
    LocalDate today = LocalDate.now();
    String mealName = Calendar.getRecipe(Date.valueOf(today));
    this.mealNameLabel.setText(mealName);
  }

  public void updateMealImage(String imagePath) {
  URL url = getClass().getResource(imagePath);
  if (url == null) {
    System.err.println("Resource not found: " + imagePath);
    return;
  }
  Image image = new Image(url.toExternalForm());
  this.mealImageView.setImage(image);
}
}