package no.ntnu.idatt1005.plate.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {
  
  
  private static final String DARK_MODE = "dark_mode";
  
  private static final String VEGETARIAN = "vegetarian";

  private final Path configFile;

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

  public boolean getDarkMode() {
    Properties prop = new Properties();
    try (InputStream fis = Files.newInputStream(configFile)) {
      prop.load(fis);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Boolean.parseBoolean(prop.getProperty(DARK_MODE));
  }

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