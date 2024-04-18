package no.ntnu.idatt1005.plate.model;

import java.util.ArrayList;
import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private static final SqlConnector sqlConnector = new SqlConnector();
    
    private static final String ingredientName = "Apple";

    @BeforeEach
    void setUp() {
        sqlConnector.resetDatabase();
    }

    @Test
    void testSelectIngredient() {
        int ingredientId = 1;
        assertEquals("Apple", Inventory.selectIngredient(ingredientId));
    }

    @Test
    void testIngredientExists() {
        assertTrue(Inventory.ingredientExists(ingredientName));
    }

    @Test
    void testSelectAllInventoryIngredients() throws SQLException {
        ResultSet rs = Inventory.selectAllInventoryIngredients();
        assertTrue(rs.next());
    }

    @Test
    void testSearchIngredients() throws SQLException {
        String search = ingredientName; // Replace with actual ingredient name
        ArrayList<String> apple = Inventory.searchIngredients(search);
        assertEquals(apple.get(0), ingredientName);
    }

    @Test
    void testSortByName() throws SQLException {
        ResultSet rs = Inventory.sortByName();
        assertTrue(rs.next());
    }

    @Test
    void testSortByCategory() throws SQLException {
        ResultSet rs = Inventory.sortByCategory();
        assertTrue(rs.next());
    }

    @Test
    void testDeleteIngredient() {
        int ingredientId = 1; // Replace with actual ingredient id
        Inventory.deleteIngredient(ingredientId);
        assertFalse(Inventory.ingredientExistsInInventory(Inventory.selectIngredient(ingredientId)));
    }

    @Test
    void testUpdateIngredient() throws SQLException {
        ResultSet rs1 = sqlConnector.executeSqlSelect("SELECT * FROM inventory_ingredient WHERE id = 1;");
        float oldQuantity = rs1.getFloat("quantity");
        float additionalQuantity = 2.0f;
        Inventory.updateIngredient("Milk", additionalQuantity);
        ResultSet rs2 = sqlConnector.executeSqlSelect("SELECT * FROM inventory_ingredient WHERE id = 1;");
        float actualQuantity = rs2.getFloat("quantity");
        assertEquals(oldQuantity + additionalQuantity, actualQuantity);
    }

    @Test
    void testAddNewIngredient() {
        Inventory.addNewIngredient("Apple", 2.0f);
        assertTrue(Inventory.ingredientExistsInInventory("Apple"));
    }

    @AfterAll
    static void tearDown() {
        sqlConnector.resetDatabase();
    }


}