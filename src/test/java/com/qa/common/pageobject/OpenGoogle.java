package com.qa.common.pageobject;

import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;

public class OpenGoogle {

    private static Logger logger = LoggerFactory.getLogger(OpenGoogle.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    @FindBy(className = "whg-c-sidebar")
    protected SelenideElement section;

    public void userCanSearch() {
        logger.info("Here");
        open(testConf.googleUrl());
        //section.click();
    }

}