package no.ntnu.idatt1005.plate.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class handles the settings of the application.
 */
public class Settings {

  private Settings() {}

  public static void saveSettings(boolean darkMode, boolean vegetarian) {
    Properties prop = new Properties();
    prop.setProperty("darkMode", String.valueOf(darkMode));
    prop.setProperty("vegetarian", String.valueOf(vegetarian));

    try (FileOutputStream fos = new FileOutputStream("config.properties")) {
      prop.store(fos, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean[] loadSettings() {
    Properties prop = new Properties();
    try {
      prop.load(Settings.class.getClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    boolean darkMode = Boolean.parseBoolean(prop.getProperty("darkMode"));
    boolean vegetarian = Boolean.parseBoolean(prop.getProperty("vegetarian"));

    return new boolean[] {darkMode, vegetarian};
  }
}
