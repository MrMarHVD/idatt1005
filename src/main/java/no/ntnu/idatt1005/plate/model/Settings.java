package no.ntnu.idatt1005.plate.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class handles the settings of the application.
 */
public class Settings {

  private static final String CONFIG_DIR =
      System.getProperty("user.home") + File.separator + ".plate";
  private static final String CONFIG_FILE = CONFIG_DIR + File.separator + "config.properties";

  public Settings() {
    File dir = new File(CONFIG_DIR);
    if (!dir.exists()) {
      dir.mkdirs();
    }

    File configFile = new File(CONFIG_FILE);
    if (!configFile.exists()) {
      try {
        configFile.createNewFile();
        Properties prop = new Properties();
        prop.setProperty("darkMode", "false");
        prop.setProperty("vegetarian", "false");
        try (FileOutputStream fos = new FileOutputStream(configFile)) {
          prop.store(fos, null);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void saveSettings(boolean darkMode, boolean vegetarian) {
    Properties prop = new Properties();
    prop.setProperty("darkMode", String.valueOf(darkMode));
    prop.setProperty("vegetarian", String.valueOf(vegetarian));

    try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
      prop.store(fos, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean getDarkMode() {
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream(CONFIG_FILE));
    } catch (IOException e) {
      e.printStackTrace();
    }
    boolean darkMode = Boolean.parseBoolean(prop.getProperty("darkMode"));
    return darkMode;
  }

  public boolean getVegetarian() {
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream(CONFIG_FILE));
    } catch (IOException e) {
      e.printStackTrace();
    }
    boolean vegetarian = Boolean.parseBoolean(prop.getProperty("vegetarian"));
    return vegetarian;
  }
}
