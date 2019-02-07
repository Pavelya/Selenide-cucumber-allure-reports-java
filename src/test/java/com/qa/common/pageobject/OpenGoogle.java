package com.qa.common.pageobject;

import com.codeborne.selenide.SelenideElement;
import static com.qa.common.PropertiesConfig.getEnvironmentProperty;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;

public class OpenGoogle {

    private static Logger logger = LoggerFactory.getLogger(OpenGoogle.class);

    @FindBy(className = "whg-c-sidebar")
    protected SelenideElement section;

    public void userCanSearch() {
        logger.info("Here");
        open(getEnvironmentProperty("preProdUrl"));
        section.click();
    }

}