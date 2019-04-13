package com.qa.common.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openqa.selenium.By;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class UserSettings {

    WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);

    private static Logger logger = LoggerFactory.getLogger(UserSettings.class);

    @FindBy(css = "div.user-settings-informer")
    protected SelenideElement settingsDropdown;

    @FindBy(css = "span.user-settings-informer__currency.currency_font.currency_font--eur")
    protected SelenideElement currencySymbol;
    
    @FindBy(css = "li.user-settings-selector-currencies-list__item")
    protected ElementsCollection currencyBlock;
    
    
    
    

    public void clickOnUserSettings() {
        logger.info("Click on user setting dropdown");
        settingsDropdown.click();
    }

    public void changeLanguage(String language) {
        logger.info("Change language to: " + language);
        executeJavaScript("arguments[0].scrollIntoView();", $(By.partialLinkText(language)));
        $(By.partialLinkText(language)).click();
    }

    public void changeCurrency() throws InterruptedException {
        logger.info("Change currency to: EUR");
        currencyBlock.get(1).click();
        Thread.sleep(15000);
    }

    public void verifyLanguageChanged(String locale) {
        logger.info("Verify language is changed and contains: " + locale);
        assertTrue("URL after change the language is wrong", url().contains(locale));
    }

    public void verifyCurrencyChanged() {
        logger.info("Verify currency is changed to: EUR");
        currencySymbol.shouldBe(visible);
    }

}