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

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.qa.common.Browserstack.start;
import static com.qa.common.Browserstack.takeDown;
import static com.codeborne.selenide.Selenide.open;

import com.qa.common.MainConfig;

public class Hooks {

    protected static Logger logger = LoggerFactory.getLogger(Hooks.class);
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);
    private static Map<String, Boolean> tagFlags = new HashMap<>();

    private static final String DESKTOP_TAG = "@desktop";
    private static final String MOBILE_TAG = "@mobile";
    private static final String ALLURE_SNAPSHOTS_FOLDER = config.allure_screenshots_folder();

    @Before
    public void before(Scenario scenario) throws Exception {
        logger.info("Scenario: " + scenario.getName() + " started");
        tagFlags.put("desktopTest", scenario.getSourceTagNames().contains(DESKTOP_TAG));
        tagFlags.put("mobileTest", scenario.getSourceTagNames().contains(MOBILE_TAG));

        if (runOnBrowserStack()) {
            start(scenario);
        } else {
            open("");
        }
    }

    @After
    public void after(Scenario scenario) throws Exception {
        takeSnapshot(scenario);

        if (runOnBrowserStack()) {
            takeDown(scenario);
        }
        logger.info("Scenario: " + scenario.getName() + " finished");
    }

    private void takeSnapshot(Scenario scenario) {
        // generate screenshot name
        String screenshotFileName = generateScreenshotName();

        byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");

        writeBytesToFile(screenshot, ALLURE_SNAPSHOTS_FOLDER + screenshotFileName);
        addReportAttachment(screenshotFileName);

    }

    public void addReportAttachment(String screenshotFileName) {
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

    private boolean runOnBrowserStack() {
        return System.getProperty("useBS", "false").contains("true") ? true : false;
    }
}
