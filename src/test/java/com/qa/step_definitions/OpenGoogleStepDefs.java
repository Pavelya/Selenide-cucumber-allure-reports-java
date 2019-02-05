package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.OpenGoogle;

import cucumber.api.java.en.Given;

public class OpenGoogleStepDefs {

    private OpenGoogle google = page(OpenGoogle.class);
    
    @Given("^open google$")
    public void open_google() throws Throwable {
        google.userCanSearch();
    }
}
