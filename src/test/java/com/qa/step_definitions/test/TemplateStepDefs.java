package com.qa.step_definitions.test;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.test.PageObjectTemplate;

import cucumber.api.java.en.Given;

public class TemplateStepDefs {

    private PageObjectTemplate google = page(PageObjectTemplate.class);
    
    @Given("^open google$")
    public void open_google() throws Throwable {
        google.userCanSearch();
    }
}
