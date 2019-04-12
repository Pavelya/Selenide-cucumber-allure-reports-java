package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.SearchFlight;
import com.qa.common.pageobject.SearchHotel;

import cucumber.api.java.en.Given;

public class SearchFlightStepDefs {

    private SearchFlight searchFlight = page(SearchFlight.class);

    @Given("^search flight form title is displayed with valid content$")
    public void searchFormTitleValidation() throws Throwable {
        searchFlight.searchFormTitleValidation();

    }

    @Given("^search flight form is displayed with valid content$")
    public void validateSearchFlightForm() throws Throwable {
        searchFlight.validateSearchFlightForm();
    }

    @Given("^user clicks of flight switch selector$")
    public void clickOnFlightSwitch() throws Throwable {
        searchFlight.clickOnFlightSwitch();
    }

    @Given("^user selects (.*) as origin$")
    public void searchOriginByCityName(String cityName) throws Throwable {
        searchFlight.searchOriginByCityName(cityName);
        searchFlight.clickOnFirstOriginSearchResult();
    }

    @Given("^user selects (.*) as destination$")
    public void searchDestinationByCityName(String cityName) throws Throwable {
        searchFlight.searchDestinationByCityName(cityName);
        searchFlight.clickOnFirstDestinationSearchResult();
    }

    @Given("^user selects valid departure date value$")
    public void selectDepartDate() throws Throwable {
        searchFlight.clickOnDepartDateBox();
        searchFlight.clickOnDepartureDate();
    }

    @Given("^user selects valid return date value$")
    public void selectReturnDate() throws Throwable {
        searchFlight.clickOnReturnDate();
    }

    @Given("^user updates number of passengers$")
    public void updatePassengers() throws Throwable {
        searchFlight.clickOnPassengerBox();
        searchFlight.increaseNumberOfPassengers();
    }
    
    @Given("^user clicks on flight search submit button")
    public void submitSearch() throws Throwable {
        searchFlight.submitSearch();
    }
    
    @Given("^flight search results are displayed")
    public void validateSearchResults() throws Throwable {
        searchFlight.validateSearchResults();
    }

}
