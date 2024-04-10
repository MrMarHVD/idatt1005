package no.ntnu.idatt1005.plate.model;

import no.ntnu.idatt1005.plate.controller.global.MainController;
import no.ntnu.idatt1005.plate.controller.global.PopupManager;

/**
 * This is a class for managing SQL-queries relating to the recipe view.
 */
public class Recipe {

  /**
   * Update the instructions in the given recipe to the user's new input.
   *
   * @param recipeName the name of the recipe whose instructions are to be changed.
   * @param instructions the new instructions to update with.
   */
  public static void updateInstructions(String recipeName, String instructions) {
    try {
      MainController.sqlConnector.executeSqlUpdate(
          "UPDATE recipe SET instruction = '" + instructions + "' WHERE name = '" + recipeName + "';");
    } catch (Exception e) {
      PopupManager.displayError("Update error", "Could not update instructions");
    }
  }

  /**
   * Get the instructions for the given recipe.
   *
   * @param recipeName the name of the recipe.
   * @return the instructions for the recipe.
   */
  public static String getInstructions(String recipeName) {
    String instructions = null;
    System.out.println(recipeName);
    try {
      instructions = MainController.sqlConnector.executeSqlSelect(
          "SELECT instruction FROM recipe WHERE name = '" + recipeName + "';").getString("instruction");
    } catch (Exception e) {
      PopupManager.displayError("Selection error", e.getMessage());
    }
    return instructions;
  }

}
