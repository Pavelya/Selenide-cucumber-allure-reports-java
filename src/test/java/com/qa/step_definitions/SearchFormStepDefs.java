package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.SearchForm;

import cucumber.api.java.en.Given;

public class SearchFormStepDefs {

    private SearchForm searchForm = page(SearchForm.class);
    
    @Given("^search form title is displayed with valid content$")
    public void searchFormTitleValidation() throws Throwable {
        searchForm.searchFormTitleValidation();
    }
    
    @Given("^user settings dropdown is displayed$")
    public void settingsDropdownValidation() throws Throwable {
        searchForm.settingsDropdownValidation();
    }
    
    @Given("^switcher between flighs and hotels is displayed$")
    public void flightsHotelsSwitcherValidation() throws Throwable {
        searchForm.flightsHotelsSwitcherValidation();
    }
}
