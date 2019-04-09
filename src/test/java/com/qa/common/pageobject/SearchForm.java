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

    public void searchFormTitleValidation() {
        logger.info("Check if search form tile is displayed");
        logger.info("Check if search form tile content is valid");
        searchFormTitle.shouldBe(visible).shouldHave(text(testConf.searchFormTitle()));
    }
}