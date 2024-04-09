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

class SettingsTest {
  private static FileSystem fileSystem;
  private static Path configDir;
  private static Settings settings;

  @BeforeEach
  void setUp() {
    fileSystem = Jimfs.newFileSystem(Configuration.unix());
    configDir = fileSystem.getPath("/home/.plate");
    settings = new Settings(configDir);
  }

  @Test
  void testSaveSettings() throws IOException {
      settings.saveSettings(false, false);

      assertTrue(Files.exists(configDir.resolve("config.properties")));
      String properties = new String(Files.readAllBytes(configDir.resolve("config.properties")));
      assertTrue(properties.contains("dark_mode=false"));
      assertTrue(properties.contains("vegetarian=false"));
  }

  @Test
  void testGetDarkMode() {
      settings.saveSettings(true, false);

      assertTrue(settings.getDarkMode());
  }

  @Test
  void testGetVegetarian() {
      settings.saveSettings(false, true);

      assertTrue(settings.getVegetarian());
  }

  @AfterAll
  static void tearDown() throws IOException {
    fileSystem.close();
  }
}