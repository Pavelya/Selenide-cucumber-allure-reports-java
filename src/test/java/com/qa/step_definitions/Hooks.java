package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static com.qa.common.Browserstack.start;
import static com.qa.common.Browserstack.takeDown;
import static com.qa.webdriver_utils.WebDriverSetup.initCustomChrome;

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

import com.qa.common.MainConfig;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Allure;

import com.qa.webdriver_utils.WebDriverSetup;

public class Hooks {
    protected static Logger logger = LoggerFactory.getLogger(Hooks.class);
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);
    private static Map<String, Boolean> tagFlags = new HashMap<>();

    private static final String DESKTOP_TAG = "@desktop";
    private static final String MOBILE_TAG = "@mobile";
    private static final String ALLURE_SNAPSHOTS_FOLDER = config.allure_screenshots_folder();

    @Before
    public void before(final Scenario scenario) throws Exception {
        logger.info("Scenario: " + scenario.getName() + " has been started");
        tagFlags.put("desktopTest", scenario.getSourceTagNames().contains(DESKTOP_TAG));
        tagFlags.put("mobileTest", scenario.getSourceTagNames().contains(MOBILE_TAG));

        if (runOnBrowserStack()) {
            start(scenario);
        } else if (isLocalMobile()) {
            System.setProperty("webdriver.chrome.driver",
                    config.resourcesFolder() + WebDriverSetup.getOS().toLowerCase().replace(" ", "_") + "/"
                            + WebDriverSetup.getUnzipedFileName());
            initCustomChrome();
            setWebDriver(getMobileBrowser());
            open("");
        } else {
            open("");
        }
    }

    @After
    public void after(final Scenario scenario) throws Exception {
        takeSnapshot(scenario);

        if (runOnBrowserStack()) {
            takeDown(scenario);
        }
        if (isLocalMobile()) {
            getWebDriver().quit();
        }
        logger.info("Scenario: " + scenario.getName() + " has been finished");
    }

    private void takeSnapshot(final Scenario scenario) {
        // generate screenshot name
        final String screenshotFileName = generateScreenshotName();

        final byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");

        writeBytesToFile(screenshot, ALLURE_SNAPSHOTS_FOLDER + screenshotFileName);
        addReportAttachment(screenshotFileName);

    }

    public void addReportAttachment(final String screenshotFileName) {
        final Path content = Paths.get(ALLURE_SNAPSHOTS_FOLDER + screenshotFileName);
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(screenshotFileName, is);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeBytesToFile(final byte[] bFile, final String fileDest) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileDest);
            fileOutputStream.write(bFile);

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String generateScreenshotName() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        final Calendar cal = Calendar.getInstance();
        return "screenshot_" + dateFormat.format(cal.getTime()) + ".png";
    }

    private boolean runOnBrowserStack() {
        return System.getProperty("useBS", "false").contains("true") ? true : false;
    }

    private static WebDriver getMobileBrowser() {
        final Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", config.mobileEmulationDevice());

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        final WebDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    private boolean isLocalMobile() {
        return System.getProperty("mobileLocal", "false").contains("true");
    }
}
