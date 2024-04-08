package no.ntnu.idatt1005.plate.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class handling the user settings of the application.
 */
public class Settings {

  private static final String CONFIG_DIR =
          System.getProperty("user.home") + File.separator + ".plate";
  private static final String CONFIG_FILE = CONFIG_DIR + File.separator + "config.properties";

  private static final String DARK_MODE = "dark_mode";
  private static final String VEGETARIAN = "vegetarian";

  /**
   * Constructor for the Settings class.
   * Creates the config file if it does not exist.
   */
  public Settings() {
    File dir = new File(CONFIG_DIR);
    if (dir.mkdirs()) {
      File configFile = new File(CONFIG_FILE);
      try {
        if (configFile.createNewFile()) {
          Properties prop = new Properties();
          prop.setProperty(DARK_MODE, "false");
          prop.setProperty(VEGETARIAN, "false");
          try (FileOutputStream fos = new FileOutputStream(configFile)) {
            prop.store(fos, null);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * Saves the settings to the config file.
   *
   * @param darkMode   dark mode setting.
   * @param vegetarian vegetarian setting.
   */
  public void saveSettings(boolean darkMode, boolean vegetarian) {
    Properties prop = new Properties();
    prop.setProperty(DARK_MODE, String.valueOf(darkMode));
    prop.setProperty(VEGETARIAN, String.valueOf(vegetarian));

    try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
      prop.store(fos, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the dark mode setting from the config file.
   *
   * @return boolean value of the dark mode setting.
   */
  public boolean getDarkMode() {
    Properties prop = new Properties();
    try (InputStream stream = new FileInputStream(CONFIG_FILE)) {
      prop.load(stream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Boolean.parseBoolean(prop.getProperty(DARK_MODE));
  }

  /**
   * Get the vegetarian setting from the config file.
   *
   * @return boolean value of the vegetarian setting.
   */
  public boolean getVegetarian() {
    Properties prop = new Properties();
    try (InputStream stream = new FileInputStream(CONFIG_FILE)) {
      prop.load(stream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Boolean.parseBoolean(prop.getProperty(VEGETARIAN));
  }


}
