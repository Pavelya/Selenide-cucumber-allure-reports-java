package com.qa.common.pageobject;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;

import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

public class OpenGoogle {

    private static Logger logger = LoggerFactory.getLogger(OpenGoogle.class);
    private TestEnvConfig testConf = createTestEnvConfig();
  
    @FindBy(name = "q")
    protected SelenideElement searchForm;

    @FindBy(xpath = "//a[@href=\"https://github.com/Pavelya\"]")
    protected SelenideElement expectedSearchResult;

    public void openGooglePage() {
        logger.info("Search for git repo in google");
        open(testConf.googleUrl());
    }

    public void searchGoogle(String searchString) {
        searchForm.setValue(searchString);
        searchForm.submit();
    }

    public void clickOnSearchResult() {
        expectedSearchResult.click();
    }


    public void verifyExpectedUrl() {
        assertTrue(url().contains("Pavelya"));
    }

}