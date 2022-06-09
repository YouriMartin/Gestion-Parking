package fr.eni.gestion_parking.dal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private static Properties properties;

    static {
        try (InputStream input = Settings.class.getClassLoader().getResourceAsStream(".properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
