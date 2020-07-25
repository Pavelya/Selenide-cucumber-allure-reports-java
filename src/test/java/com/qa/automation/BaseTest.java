package com.qa.automation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.qa.common.AllureReportConfig.prepareAllureResultsFolder;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty",
        "html:target/cucumber/cucumber-html-report",
        "junit:target/cucumber/cucumber-junit-report.xml",
        "json:target/cucumber/cucumber.json",
        "io.qameta.allure.cucumberjvm.AllureCucumberJvm"},
        glue = "com/qa/step_definitions",
        features = "src/test/resources")

public class BaseTest{

    @BeforeClass
    static public void beforeSuite() {
        prepareAllureResultsFolder();
        System.setProperty("selenide.headless", System.getProperty("headless", "true"));
        System.setProperty("selenide.baseUrl", System.getProperty("baseUrl", "https://google.com"));
    }

    @AfterClass
    public static void afterSuite() throws Exception {
    }
}
