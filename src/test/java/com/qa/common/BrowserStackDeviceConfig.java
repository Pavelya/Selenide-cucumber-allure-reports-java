package com.qa.common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

public class BrowserStackDeviceConfig {

    @Sources({ "${pathToDeviceConfig}" })
    public interface DeviceConfig extends Config {

        // mobile capabilities
        String browserName();

        String device();

        String realMobile();

        String os();

        // desktop capabilities
        String os_version();

        String browser();

        String browser_version();
    }

    public static DeviceConfig createDeviceConfig() {
        String pathToDeviceConfig = "file:src/test/resources/BROWSERSTACK_DEVICES_CONFIG/" + getDeviceConfig()
                + ".properties";
        ConfigFactory.setProperty("pathToDeviceConfig", pathToDeviceConfig);
        return ConfigFactory.create(DeviceConfig.class);
    }

    private static String getDeviceConfig() {
        return System.getProperty("device", "Samsung_Galaxy_S8").replace(" ", "_");
    }
}