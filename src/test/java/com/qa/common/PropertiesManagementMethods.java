package com.qa.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qa.automation.BaseConfig;

/**
 * @author pavely Utility to control environment
 * 
 */

public class PropertiesManagementMethods implements BaseConfig {

    protected static Logger logger = LoggerFactory.getLogger(PropertiesManagementMethods.class);

    static String propertiesFilePath = "src/test/resources/config.properties";
    static String environmentPropertiesFilePath = "src/test/resources/ENV.environment.properties";
    static String environment = System.getProperty("env").toLowerCase();

    public void setProperty(String propName, String propValue) {

        // create temp test file if not exists
        File file = new File(propertiesFilePath);

        try {
            if (file.createNewFile()) {
                logger.info("File: " + propertiesFilePath + " is created");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // load config file
        Properties props = null;
        try {
            FileInputStream in = new FileInputStream(propertiesFilePath);
            props = new Properties();
            props.load(in);
            in.close();
        } catch (Exception e) {

        }
        // edit config file
        try {
            FileOutputStream out = new FileOutputStream(propertiesFilePath);
            props.setProperty(propName, propValue);
            props.store(out, null);
            out.close();
        } catch (Exception e) {
        }
    }

    public static String getProperty(String propName) {
        Properties prop = new Properties();

        InputStream is;
        try {
            is = new FileInputStream(propertiesFilePath);

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

    // UTILS
    public static String getOS() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS;
    }

    public boolean isWindows() {
        String OS = getOS();
        return (OS.indexOf("win") >= 0);
    }

    public boolean isMac() {
        String OS = getOS();
        return (OS.indexOf("mac") >= 0);
    }

    public boolean isUnix() {
        String OS = getOS();
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }
}
