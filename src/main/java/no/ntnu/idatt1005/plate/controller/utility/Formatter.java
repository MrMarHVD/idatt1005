package no.ntnu.idatt1005.plate.controller.utility;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

/**
 * This class sets formatting constraints for certain UI components.
 */
public class Formatter {


  /**
   * A formatter for text fields that only allows for floating point numbers.
   */
  public static UnaryOperator<Change> floatFormatter = change -> {
    String newText = change.getControlNewText();
    if (newText.matches("-?([0-9]*[.])?[0-9]*")) {
      return change;
    }
    return null;
  };

  /**
   * Get the float formatter as a TextFormatter.
   *
   * @return the float formatter.
   */
  public static TextFormatter<Change> getFloatFormatter() {
    return new TextFormatter<>(floatFormatter);
  }
}

// TODO: add condition in inventory such that negative values are not allowed
