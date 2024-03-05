package no.ntnu.idatt1002.demo.model;

import no.ntnu.idatt1005.plate.model.ShoppingListItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ShoppingListItemTest {

  @Test
  @DisplayName("Test constructor")
  void testConstructor() {
    ShoppingListItem item = new ShoppingListItem(2, 2);
    assertEquals(2, item.getIngredientId());
    assertEquals(2, item.getAmount());
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    ShoppingListItem item = new ShoppingListItem(2, 2);
    assertEquals("flour, 2 grams", item.toString());
  }



}
