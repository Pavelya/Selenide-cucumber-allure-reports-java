package com.qa.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesConfig {

    protected static Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

    static String environmentPropertiesFilePath = "src/test/resources/ENV.environment.properties";
    static String environment = System.getProperty("env").toLowerCase();

    public static String getEnvironmentProperty(String propName) {
        Properties prop = new Properties();

        InputStream is;
        try {
            is = new FileInputStream(environmentPropertiesFilePath.replace("ENV", environment));

            prop.load(is);
            String propertieValue = prop.getProperty(propName);

            is.close();

            return propertieValue;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
