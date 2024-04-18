package no.ntnu.idatt1005.plate.model;

import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListTest {


    private static final SqlConnector sqlConnector = new SqlConnector();

    @BeforeEach
    void setUp() {
        sqlConnector.resetDatabase();
    }

    @Test
    @DisplayName("addItem test")
    void testAddItem() {
      // Arrange
      int ingredientId = 1000;
      float quantity = 2.0f;

      // Act
      ShoppingList.addItem(ingredientId, quantity);

      // Assert
      assertTrue(ShoppingList.inShoppingList(ingredientId));
    }

    @Test
    @DisplayName("inShoppingList test")
    void testInShoppingList() {

      // Arrange
      int ingredientId = 1;
      ShoppingList.addItem(ingredientId, 1);

      // Act
      boolean exists = ShoppingList.inShoppingList(ingredientId);

      // Assert
      assertTrue(exists);
    }
}