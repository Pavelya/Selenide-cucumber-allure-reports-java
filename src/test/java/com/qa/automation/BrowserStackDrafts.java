package com.qa.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BrowserStackDrafts {

    public static final String USERNAME = "pavelyampolsky1";
    public static final String AUTOMATE_KEY = "PyrAvcDs4XBiW6soEZPp";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static WebDriver getBS() throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "android");
        caps.setCapability("device", "Samsung Galaxy S8");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "7.0");
        caps.setCapability("build", "testBuild");
        caps.setCapability("project", "testProject");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
        return driver;

    }
    

}