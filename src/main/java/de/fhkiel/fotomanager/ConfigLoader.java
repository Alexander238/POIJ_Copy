package de.fhkiel.fotomanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * The type Config loader.
 */
public class ConfigLoader {
    /**
     * The Properties.
     */
    private Properties props;

    /**
     * Instantiates a new Config loader.
     */
    public ConfigLoader() {
        props = new Properties();
        loadConfig();
    }

    /**
     * Load config from the config.properties file.
     */
    private void loadConfig() {
        try {
            FileInputStream propsInput = new FileInputStream("src/config.properties");
            props.load(propsInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the value of the given key.
     *
     * @param key the key
     * @return the value
     */
    public String getValue(String key) {
        return props.getProperty(key);
    }
}
