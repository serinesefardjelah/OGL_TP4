package com.example.config;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConfig {
    private static final Logger logger = Logger.getLogger(AppConfig.class.getName());


    //this is a util class to get the db credentials from the config.properties
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = loadConfig();

    private static Properties loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Exception",e);
        }
        return properties;
    }

    public static String getDbUser() {
        return properties.getProperty("db.user");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}
