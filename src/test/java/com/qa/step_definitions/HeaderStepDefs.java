package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.Header;
import cucumber.api.java.en.Given;

public class HeaderStepDefs {

    private Header header = page(Header.class);

    @Given("^header is displayed with valid content$")
    public void headerValidation() throws Throwable {
        header.headerSectionValidation();
        header.logoValidation();
        header.descritionValidation();
    }
}
