package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.SearchHotelsForm;

import cucumber.api.java.en.Given;

public class SearchFormStepDefs {

    private SearchHotelsForm searchForm = page(SearchHotelsForm.class);

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

    @Given("^search hotels form is displayed with valid content$")
    public void validateSearchHotelForm() throws Throwable {
        searchForm.validateSearchHotelForm();
    }

    @Given("^user search for (.*) city in search hotels form$")
    public void searchHotel(String cityName) throws Throwable {
        searchForm.searchHotelByCityName(cityName);
        searchForm.clickOnFirstSearchResult();
    }

    @Given("^user selects valid checkin date value")
    public void selectCheckInDate() throws Throwable {
        searchForm.clickOnCheckInBox();
        searchForm.clickOnCheckInDate();
    }

    @Given("^user selects valid checkout date value")
    public void selectCheckoutDate() throws Throwable {
        searchForm.clickOnCheckOutDate();
    }
    
    @Given("^user updates number of guests")
    public void increaseNumberOfGuests() throws Throwable {
        searchForm.increaseNumberOfGuests();
    }
     

    @Given("^user clicks on search submit button")
    public void submitSearch() throws Throwable {
        searchForm.submitSearch();
    }

    @Given("^search results are displayed")
    public void validateSearchResults() throws Throwable {
        searchForm.validateSearchResults();
    }
}
