package no.ntnu.idatt1005.plate.model;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Settings class.
 */
class SettingsTest {

  /**
   * The file system used for testing.
   */
  private static FileSystem fileSystem;

  /**
   * The path to the configuration directory.
   */
  private static Path configDir;

  /**
   * The settings used for testing.
   */
  private static Settings settings;

  /**
   * Set up the test environment by creating a new file system and a new settings object, as well
   * as assigning a path to the configuration directory; namely the user's home directory.
   */
  @BeforeEach
  void setUp() {
    fileSystem = Jimfs.newFileSystem(Configuration.unix());
    configDir = fileSystem.getPath("/home/.plate");
    settings = new Settings(configDir);
  }

  /**
   * Test the saveSettings method by asserting that the configuration file is created and that the
   * created file contains the specified settings.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  void testSaveSettings() throws IOException {
      settings.saveSettings(false, false);

      assertTrue(Files.exists(configDir.resolve("config.properties")));
      String properties = new String(Files.readAllBytes(configDir.resolve("config.properties")));
      assertTrue(properties.contains("dark_mode=false"));
      assertTrue(properties.contains("vegetarian=false"));
  }

  /**
   * Test the getDarkMode method by asserting that the method returns the correct value.
   */
  @Test
  void testGetDarkMode() {
    settings.saveSettings(true, false);

    assertTrue(settings.getDarkMode());
  }

  /**
   * Test the getVegetarian method by asserting that the method returns the correct value.
   */
  @Test
  void testGetVegetarian() {
    settings.saveSettings(false, true);

    assertTrue(settings.getVegetarian());
  }

  /**
   * Tear down the test environment by closing the file system.
   *
   * @throws IOException if an I/O error occurs.
   */
  @AfterAll
  static void tearDown() throws IOException {
    fileSystem.close();
  }
}