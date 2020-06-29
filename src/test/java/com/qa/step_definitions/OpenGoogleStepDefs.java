package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.OpenGoogle;

import cucumber.api.java.en.Given;

public class OpenGoogleStepDefs {

    private OpenGoogle google = page(OpenGoogle.class);

    @Given("^User opens google$")
    public void open_google() throws Throwable {
        google.openGooglePage();
    }

    @Given("^User search for \"(.*)\"$")
    public void search(String searchString) throws Throwable {
        google.searchGoogle(searchString);
    }

    @Given("User clicks on search result")
    public void clickOnSearchResult() throws Throwable {
        google.clickOnSearchResult();
    }

    @Given("User redirected to the expected url")
    public void verifyExpectedUrl() throws Throwable {
        google.verifyExpectedUrl();
    }
}
