package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.SearchHotel;
import com.qa.common.pageobject.UserSettings;

import cucumber.api.java.en.Given;

public class UserSettingsStepDefs {

    private UserSettings userSettings = page(UserSettings.class);

    @Given("^user changes the languge value to (.*)$")
    public void changeLanguage(String language) throws Throwable {
        userSettings.clickOnUserSettings();
        userSettings.changeLanguage(language);
    }
    
    @Given("^page is reloaded and new language value (.*) is set$")
    public void verifyLanguageChanged(String locale) throws Throwable {
        userSettings.verifyLanguageChanged(locale);
    }
    
    @Given("^user changes the currency value to euro$")
    public void changeCurrency() throws Throwable {
        userSettings.clickOnUserSettings();
        userSettings.changeCurrency();
    }
    
    
    @Given("^Currency is changed to EUR$")
    public void verifyCurrencyChanged() throws Throwable {
        userSettings.verifyCurrencyChanged();
    }
    
     

   }
