package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.ParseApify;

import cucumber.api.java.en.Given;

public class ParseApifyStepDefs {

    private ParseApify parseApify = page(ParseApify.class);

    @Given("^User parse apify using (.*) file$")
    public void open_apify(String filePath) throws Throwable {
        parseApify.openApify(filePath);
    }
}
