package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.HotelDetails;
import cucumber.api.java.en.Given;

public class HotelDetailsStepDefs {

    private HotelDetails hotelDetails = page(HotelDetails.class);

    @Given("^user clicks on hotel name for the first suggested hotel$")
    public void searchFormTitleValidation() throws Throwable {
        hotelDetails.clickOnHotelLink();
    }

    @Given("^the page with hotel details is displayed with valid content$")
    public void settingsDropdownValidation() throws Throwable {
        hotelDetails.hotelDetailsPageValidation();
    }
}
