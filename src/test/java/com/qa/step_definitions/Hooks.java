package com.qa.step_definitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Allure;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.qa.automation.BrowserStackDrafts.getBS;
import com.browserstack.local.Local;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.common.AllureReportConfig;
import com.qa.common.MainConfig;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Hooks {

    protected static Logger logger = LoggerFactory.getLogger(Hooks.class);
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);

    private static Map<String, Boolean> tagFlags = new HashMap<>();

    private static final String DESKTOP_TAG = "@desktop";
    private static final String MOBILE_TAG = "@mobile";
    private static final String ALLURE_SNAPSHOTS_FOLDER = config.allure_screenshots_folder();
    private static final String USERNAME = config.bs_user();
    private static final String PROJECT = config.bs_project();
    private static final String AUTOMATE_KEY = config.bs_key();
    private static final String HUB_URL = config.bs_hub();
    private static final String DEBUG_MODE = config.bs_debug();
    private String LOCAL_IDENTIFIER = UUID.randomUUID().toString();
    private WebDriver driver;
    private Local l;

    @Before
    public void before(Scenario scenario) throws Exception {
        logger.info("Scenario: " + scenario.getName() + " started");

        tagFlags.put("desktopTest", scenario.getSourceTagNames().contains(DESKTOP_TAG));
        tagFlags.put("mobileTest", scenario.getSourceTagNames().contains(MOBILE_TAG));
        String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + HUB_URL;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "android");
        caps.setCapability("device", "Samsung Galaxy S8");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "7.0");
        caps.setCapability("build", scenario.getName());
        caps.setCapability("project", PROJECT);
        caps.setCapability("browserstack.debug", DEBUG_MODE);
        caps.setCapability("browserstack.localIdentifier", LOCAL_IDENTIFIER);

        if (bsLocal()) {
            caps.setCapability("browserstack.local", "true");
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", AUTOMATE_KEY);
            options.put("localIdentifier", LOCAL_IDENTIFIER);
            l.start(options);
        }

        driver = new RemoteWebDriver(new URL(URL), caps);

        WebDriverRunner.setWebDriver(driver);
        // open("");
    }

    @After
    public void after(Scenario scenario) throws Exception {

        // generate screenshot name
        String screenshotFileName = generateScreenshotName();

        byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");

        writeBytesToFile(screenshot, ALLURE_SNAPSHOTS_FOLDER + screenshotFileName);
        addAllureAttachment(screenshotFileName);

        // close();
        driver.quit();
        if (l != null)
            l.stop();
        logger.info("Scenario: " + scenario.getName() + " finished");
    }

    public void addAllureAttachment(String screenshotFileName) {
        Path content = Paths.get(ALLURE_SNAPSHOTS_FOLDER + screenshotFileName);
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(screenshotFileName, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeBytesToFile(byte[] bFile, String fileDest) {

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileDest);
            fileOutputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String generateScreenshotName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();

        return "screenshot_" + dateFormat.format(cal.getTime()) + ".png";
    }

    private boolean bsLocal() {
        return System.getProperty("bs_local", "true").contains("true") ? true : false;
    }
}
