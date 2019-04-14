package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertNotEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;

import com.qa.common.TestEnvironmentConfig.TestEnvConfig;
import cucumber.api.java.en.Given;

public class CommonNavigationStepDefs {
    
    private static Logger logger = LoggerFactory.getLogger(CommonNavigationStepDefs.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    
    @Given("^user opens travelpayouts page$")
    public void openMainPage() throws Throwable {
        String mainPage = testConf.mainPage();
        logger.info("Open main page: " + mainPage);
        open(mainPage);
    }
    
    @Given("^the user is redirected to partner page")
    public void validateRedirectToPartnerSite() throws Throwable {
        logger.info("Validate redirect to partner site");
        // navigate to new tab
        switchTo().window(1);
        logger.info("User is on: " + url() + " site");
        assertNotEquals("User is not redirected to external provider site", url(), testConf.mainPage());
    }
}
