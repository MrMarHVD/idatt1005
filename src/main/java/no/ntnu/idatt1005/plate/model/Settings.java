package no.ntnu.idatt1005.plate.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * This class manages the settings for the application.
 */
public class Settings {

  /**
   * The key for the dark mode setting.
   */
  private static final String DARK_MODE = "dark_mode";

  /**
   * The key for the vegetarian setting.
   */
  private static final String VEGETARIAN = "vegetarian";

  /**
   * The path to the config file.
   */
  private final Path configFile;

  /**
   * Constructor for the Settings.
   *
   * @param configDir the config directory.
   */
  public Settings(Path configDir) {
    this.configFile = configDir.resolve("config.properties");

    try {
      if (!Files.exists(configDir)) {
        Files.createDirectories(configDir);
      }

      if (!Files.exists(this.configFile)) {
        Files.createFile(this.configFile);
        Properties prop = new Properties();
        prop.setProperty(DARK_MODE, "false");
        prop.setProperty(VEGETARIAN, "false");
        try (OutputStream fos = Files.newOutputStream(this.configFile)) {
          prop.store(fos, null);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Save the settings to the config file.
   *
   * @param darkMode whether or not dark mode is enabled.
   * @param vegetarian whether or not vegetarian mode is enabled.
   */
  public void saveSettings(boolean darkMode, boolean vegetarian) {
    Properties prop = new Properties();
    prop.setProperty(DARK_MODE, String.valueOf(darkMode));
    prop.setProperty(VEGETARIAN, String.valueOf(vegetarian));

    try (OutputStream fos = Files.newOutputStream(configFile)) {
      prop.store(fos, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the dark mode setting.
   *
   * @return whether or not dark mode is enabled.
   */
  public boolean getDarkMode() {
    Properties prop = new Properties();
    try (InputStream fis = Files.newInputStream(configFile)) {
      prop.load(fis);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Boolean.parseBoolean(prop.getProperty(DARK_MODE));
  }

  /**
   * Get the vegetarian setting.
   *
   * @return whether or not vegetarian mode is enabled.
   */
  public boolean getVegetarian() {
    Properties prop = new Properties();
    try (InputStream fis = Files.newInputStream(configFile)) {
      prop.load(fis);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Boolean.parseBoolean(prop.getProperty(VEGETARIAN));
  }
}