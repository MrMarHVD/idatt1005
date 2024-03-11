package no.ntnu.idatt1005.plate.model;

import no.ntnu.idatt1005.plate.model.json.JsonReader;

/**
 * Temporary class for instantiating some recipes. This will not be part of the final app.
 */
public class CookbookMaker {

  /**
   * Create a cookbook with some recipes.
   * @return a cookbook with some recipes.
   */
  public static CookBook createCookBook() {

    return JsonReader.getCookBookById(1);
  }

}
