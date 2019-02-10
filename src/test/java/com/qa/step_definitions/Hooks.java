package com.qa.step_definitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Allure;
import io.restassured.http.ContentType;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
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

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

import com.browserstack.local.Local;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.common.BrowserStackDeviceConfig.DeviceConfig;
import com.qa.common.MainConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import static com.qa.common.BrowserStackDeviceConfig.createDeviceConfig;

public class Hooks {

    protected static Logger logger = LoggerFactory.getLogger(Hooks.class);
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);
    private DeviceConfig deviceConf = createDeviceConfig();

    private static Map<String, Boolean> tagFlags = new HashMap<>();

    DesiredCapabilities caps = new DesiredCapabilities();

    private static final String DESKTOP_TAG = "@desktop";
    private static final String MOBILE_TAG = "@mobile";
    private static final String ALLURE_SNAPSHOTS_FOLDER = config.allure_screenshots_folder();
    private static final String USERNAME = config.bs_user();
    private static final String PROJECT = config.bs_project();
    private static final String AUTOMATE_KEY = config.bs_key();
    private static final String HUB_URL = config.bs_hub();
    private static final String DEBUG_MODE = config.bs_debug();
    private static final String BS_API_URL = config.bs_api();
    private static final String BS_API_BUILDS_URL = BS_API_URL + "builds.json";
    private static final String BS_API_SESSIONS_URL = BS_API_URL + "sessions/";
    private static final String BS_AUTOMATION_URL = config.bs_automation();
    private String LOCAL_IDENTIFIER = UUID.randomUUID().toString();
    private WebDriver driver;
    private Local l;

    @Before
    public void before(Scenario scenario) throws Exception {
        logger.info("Scenario: " + scenario.getName() + " started");

        tagFlags.put("desktopTest", scenario.getSourceTagNames().contains(DESKTOP_TAG));
        tagFlags.put("mobileTest", scenario.getSourceTagNames().contains(MOBILE_TAG));
        String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + HUB_URL;
        if (!isMobile()) {
            caps.setCapability("os", deviceConf.os());
            caps.setCapability("os_version", deviceConf.os_version());
            caps.setCapability("browser", deviceConf.browser());
            caps.setCapability("browser_version", deviceConf.browser_version());
        } else {
            caps.setCapability("device", deviceConf.device());
            caps.setCapability("realMobile", deviceConf.realMobile());
            caps.setCapability("os_version", deviceConf.os_version());
        }
        caps.setCapability("build", "Test Build");
        caps.setCapability("project", PROJECT);
        caps.setCapability("browserstack.debug", DEBUG_MODE);
        caps.setCapability("browserstack.localIdentifier", LOCAL_IDENTIFIER);
        caps.setCapability("name", scenario.getName());

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
        addReportAttachment(screenshotFileName);

        markBrowserStackSession(scenario.getStatus());
        logger.info("Link to BrowserStack session: " + generateSessionUrl());

        // close();
        driver.quit();
        if (l != null)
            l.stop();
        logger.info("Scenario: " + scenario.getName() + " finished");
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

    private boolean bsLocal() {
        return System.getProperty("bs_local", "true").contains("true") ? true : false;
    }

    private boolean isMobile() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("win") >= 0 || OS.indexOf("mac") >= 0) ? false : true;
    }

    private String getBuildId() {
        String response = given().auth().basic(USERNAME, AUTOMATE_KEY).when().get(BS_API_BUILDS_URL).then().extract()
                .response().asString();

        String buildName = caps.getCapability("build").toString();

        for (Object build : new JSONArray(response)) {
            JSONObject buildObject = ((JSONObject) build).getJSONObject("automation_build");
            if (buildObject.getString("name").equals(buildName)) {
                return buildObject.getString("hashed_id");
            }
        }
        return null;
    }

    private String generateSessionStatusUrl() {
        return BS_API_SESSIONS_URL + getSessionId() + ".json";
    }

    private String generateSessionUrl() {
        String sessionId = getSessionId();
        String buildId = getBuildId();

        if (buildId != null && sessionId != null) {
            return BS_AUTOMATION_URL + "builds/" + buildId + "/sessions/" + sessionId;
        } else {
            return "Could not create session Url";
        }
    }

    private void markBrowserStackSession(String status) {
        String body = new JSONObject().put("status", status).toString();
        given().auth().basic(USERNAME, AUTOMATE_KEY).contentType(ContentType.JSON).body(body).when()
                .put(generateSessionStatusUrl());
    }

    private static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }
}
