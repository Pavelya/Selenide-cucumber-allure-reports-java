package com.qa.common;

import cucumber.api.Scenario;
import io.restassured.http.ContentType;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class Browserstack {

    protected static Logger logger = LoggerFactory.getLogger(Browserstack.class);
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);
    private static DeviceConfig deviceConf = createDeviceConfig();

    static DesiredCapabilities caps = new DesiredCapabilities();

    private static final String USERNAME = config.bs_user();
    private static final String PROJECT = config.bs_project();
    private static final String AUTOMATE_KEY = config.bs_key();
    private static final String HUB_URL = config.bs_hub();
    private static final String DEBUG_MODE = config.bs_debug();
    private static final String BS_API_URL = config.bs_api();
    private static final String BS_API_BUILDS_URL = BS_API_URL + "builds.json";
    private static final String BS_API_SESSIONS_URL = BS_API_URL + "sessions/";
    private static final String BS_AUTOMATION_URL = config.bs_automation();
    private static String LOCAL_IDENTIFIER = UUID.randomUUID().toString();
    private static WebDriver driver;
    private static Local l;

    public static void start(Scenario scenario) throws Exception {
        logger.info("Scenario: " + scenario.getName() + " started");

        String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + HUB_URL;
        
        if (isDesktop()) {
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

    }

    public static void takeDown(Scenario scenario) throws Exception {

        markBrowserStackSession(scenario.getStatus());
        logger.info("Link to BrowserStack session: " + generateSessionUrl());

        driver.quit();
        if (l != null)
            l.stop();

        logger.info("Scenario: " + scenario.getName() + " finished");
    }

    private static boolean bsLocal() {
        return System.getProperty("bs_local", "true").contains("true") ? true : false;
    }
    
    private static boolean isDesktop() {
        String device = System.getProperty("device").toLowerCase();
        return (device.contains("windows") || device.contains("os_x")) ? true : false;
    }

    private static String getBuildId() {
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

    private static String generateSessionStatusUrl() {
        return BS_API_SESSIONS_URL + getSessionId() + ".json";
    }

    private static String generateSessionUrl() {
        String sessionId = getSessionId();
        String buildId = getBuildId();

        if (buildId != null && sessionId != null) {
            return BS_AUTOMATION_URL + "builds/" + buildId + "/sessions/" + sessionId;
        } else {
            return "Could not create session Url";
        }
    }

    private static void markBrowserStackSession(String status) {
        String body = new JSONObject().put("status", status).toString();
        given().auth().basic(USERNAME, AUTOMATE_KEY).contentType(ContentType.JSON).body(body).when()
                .put(generateSessionStatusUrl());
    }

    private static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }
}
