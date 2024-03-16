package no.ntnu.idatt1005.plate.controller.calendar;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the DayBlock object in the user interface.
 */
public class DayBlockController {

  @FXML
  private Label dayLabel;

  @FXML
  private Label dateLabel;

  @FXML
  private Label recipeLabel;

  @FXML
  private ImageView imageView;

  public String getDate() {
    return this.dateLabel.getText();
  }

  public void setRecipe(String recipeName) {
    this.recipeLabel.setText(recipeName);
  }

  public void setDay(String day) {
    this.dayLabel.setText(day);
  }

  public void setDate(String date) {
    this.dateLabel.setText(date);
  }


  // TODO: fix this method, currently throwing error, don't know whats wrong...
  public void setImage(String imagePath) {
    URL url = getClass().getResource(imagePath);
    if (url == null) {
      System.err.println("Resource not found: " + imagePath);
    } else {
      Image image = new Image(url.toExternalForm());
      this.imageView.setImage(image);
    }
  }

}
