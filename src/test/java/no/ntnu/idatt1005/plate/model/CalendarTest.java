package no.ntnu.idatt1005.plate.model;

import no.ntnu.idatt1005.plate.data.SqlConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    private static final String dbFileName = "calendar_test.db";

    private static final Date date = Date.valueOf(LocalDate.now());

    private static final Date date1 = Date.valueOf(LocalDate.now().plusDays(1));

    private static final SqlConnector sqlConnector = new SqlConnector(dbFileName);

    private static final Calendar calendar = new Calendar(sqlConnector);

    @Test
    @DisplayName("constructor test")
    void testConstructor() {
        //assert
        assertNotNull(calendar);
    }


    @Test
    @DisplayName("insert day test")
    void testInsertDay() {

        //act
        calendar.insertDay(date, false);

        //assert
      assertTrue(calendar.dayExists(date));
    }


    @Test
    @DisplayName("day exists negative test")
    void testDayExistsNegative() {
        //act
        boolean exists = calendar.dayExists(date1);

        //assert
        assertFalse(exists);
    }

    @Test
    @DisplayName("get recipe test")
    void testGetRecipe() {

        //act
        String recipe = calendar.getRecipe(date);

        //assert
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("get recipe negative test")
    void testGetRecipeNegative() {

        //act
        String recipe = calendar.getRecipe(date1);

        //assert
        assertNull(recipe);
    }

    @Test
    @DisplayName("change recipe test")
    void testChangeRecipe() {
        //arrange
        String newRecipe = "Margherita Pizza";

        //act
        calendar.changeRecipe(date, newRecipe);

        //assert
        assertEquals(newRecipe, calendar.getRecipe(date));
    }

    @Test
    @DisplayName("get day recipes test")
    void testGetDayRecipes() {

        //act
        String recipe = calendar.getRecipe(date);

        //assert
        assertEquals(recipe, calendar.getDayRecipes().get(date.toString()));
    }

    @Test
    @DisplayName("search recipes test")
    void testSearchRecipes() {
        //arrange
        String search = "Pizza";

        //act
        String recipe = calendar.searchRecipes(search, false).get(0);

        //assert
        assertTrue(recipe.contains(search));
    }

    @Test
    @DisplayName("search recipes negative test")
    void testSearchRecipesNegative() {
        //arrange
        String search = "Nonexistent";

        //act
        int size = calendar.searchRecipes(search, false).size();

        //assert
        assertEquals(0, size);
    }



    @AfterAll
    static void cleanUp() {
        try {
            Files.deleteIfExists(Paths.get("src/main/resources/" + dbFileName));
            System.out.println(dbFileName + " deleted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}