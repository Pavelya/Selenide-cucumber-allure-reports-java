package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.SearchHotel;

import cucumber.api.java.en.Given;

public class SearchHotelStepDefs {

    private SearchHotel searchHotel = page(SearchHotel.class);

    @Given("^search form title is displayed with valid content$")
    public void searchFormTitleValidation() throws Throwable {
        searchHotel.searchFormTitleValidation();
    }

    @Given("^user settings dropdown is displayed$")
    public void settingsDropdownValidation() throws Throwable {
        searchHotel.settingsDropdownValidation();
    }

    @Given("^switcher between flighs and hotels is displayed$")
    public void flightsHotelsSwitcherValidation() throws Throwable {
        searchHotel.flightsHotelsSwitcherValidation();
    }

    @Given("^search hotels form is displayed with valid content$")
    public void validateSearchHotelForm() throws Throwable {
        searchHotel.validateSearchHotelForm();
    }

    @Given("^user search for (.*) city in search hotels form$")
    public void searchHotel(String cityName) throws Throwable {
        searchHotel.searchHotelByCityName(cityName);
        searchHotel.clickOnFirstSearchResult();
    }

    @Given("^user selects valid checkin date value")
    public void selectCheckInDate() throws Throwable {
        searchHotel.clickOnCheckInBox();
        searchHotel.clickOnCheckInDate();
    }

    @Given("^user selects valid checkout date value")
    public void selectCheckoutDate() throws Throwable {
        searchHotel.clickOnCheckOutDate();
    }

    @Given("^user updates number of guests")
    public void increaseNumberOfGuests() throws Throwable {
        searchHotel.increaseNumberOfGuests();
    }

    @Given("^user clicks on search submit button")
    public void submitSearch() throws Throwable {
        searchHotel.submitSearch();
    }

    @Given("^search results are displayed")
    public void validateSearchResults() throws Throwable {
        searchHotel.validateSearchResults();
    }

    @Given("^user clicks on Book button for the first suggested hotel")
    public void clickOnBookButton() throws Throwable {
        searchHotel.clickOnBookButton();
    }
    
    @Given("^user clicks on partner price link for the first suggested hotel")
    public void clickOnPartnerPriceLink() throws Throwable {
        searchHotel.clickOnPartnerPriceLink();
    }
    

    @Given("^the user is redirected to partner page")
    public void validateRedirectToPartnerSite() throws Throwable {
        searchHotel.validateRedirectToPartnerSite();
    }

}
