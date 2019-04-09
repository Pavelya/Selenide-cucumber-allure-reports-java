package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.open;
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
}
