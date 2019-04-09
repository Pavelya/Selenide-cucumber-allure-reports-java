package com.qa.common.pageobject;

import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;
import static com.codeborne.selenide.Condition.text;

public class SearchForm {

    private static Logger logger = LoggerFactory.getLogger(SearchForm.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    @FindBy(css = "div.TPWL-header-content__label")
    protected SelenideElement searchFormTitle;

    @FindBy(css = "div.user-settings-informer")
    protected SelenideElement settingsDropdown;
    
    @FindBy(css = " ul.mewtwo-tabs_list")
    protected SelenideElement flightsHotelsSwitcher;
    
   

    public void searchFormTitleValidation() {
        logger.info("Check if search form tile is displayed");
        logger.info("Check if search form tile content is valid");
        searchFormTitle.shouldBe(visible).shouldHave(text(testConf.searchFormTitle()));
    }

    public void settingsDropdownValidation() {
        logger.info("Check if settings dropdown element is dispalyed");
        settingsDropdown.shouldBe(visible);
    }
    
    public void flightsHotelsSwitcherValidation() {
        logger.info("Check if switcher between flighs and hotels is displayed");
        flightsHotelsSwitcher.shouldBe(visible);
        // TODO: add functionality of click. Not part of POC 
    }
}