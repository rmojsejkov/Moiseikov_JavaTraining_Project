package app.util;

import java.io.*;
import java.util.Properties;

public class ConfigurateManager {
    private static ConfigurateManager instance;

    private ConfigurateManager(){}

    public synchronized static ConfigurateManager getInstance(){ // Singleton
        if (instance == null){
            instance = new ConfigurateManager();
        }
        return instance;
    }

    public String getProperty(String key, String config) {
        if (config.equalsIgnoreCase("mysql")) {
            try (InputStream fileReader = getClass().getClassLoader().getResourceAsStream("mysql.properties")) {
                Properties properties = new Properties();
                properties.load(fileReader);
                return properties.getProperty(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(config.equalsIgnoreCase("config")){
            try(InputStream fileReader = getClass().getClassLoader().getResourceAsStream("config.properties")){
                Properties properties = new Properties();
                properties.load(fileReader);
                return properties.getProperty(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
