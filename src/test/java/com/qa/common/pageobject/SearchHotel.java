package com.qa.common.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchHotel {

    WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);

    private static Logger logger = LoggerFactory.getLogger(SearchHotel.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    @FindBy(css = "div.TPWL-header-content__label")
    protected SelenideElement searchFormTitle;

    @FindBy(css = "div.user-settings-informer")
    protected SelenideElement settingsDropdown;

    @FindBy(css = "ul.mewtwo-tabs_list")
    protected SelenideElement flightsHotelsSwitcher;

    @FindBy(css = "div.mewtwo-hotels-container")
    protected SelenideElement searchConteiner;

    @FindBy(css = "#hotels-destination-whitelabel_en")
    protected SelenideElement searchInput;

    @FindBy(css = "div.mewtwo-hotels-dates-checkin")
    protected SelenideElement checkInSelection;

    @FindBy(css = "div.mewtwo-hotels-dates-checkout")
    protected SelenideElement checkOutSelection;

    @FindBy(css = "div.mewtwo-hotels-guests")
    protected SelenideElement numberOfGuestsSelection;

    @FindBy(css = "div.mewtwo-hotels-submit_button")
    protected SelenideElement searchSubmit;

    @FindBy(css = "li.mewtwo-autocomplete-list-item.mewtwo-autocomplete-list-item--one_line")
    protected ElementsCollection hotelSearchResults;

    @FindBy(css = "span.search-results-title__total")
    protected SelenideElement searchResultsCounter;

    @FindBy(css = "div.mewtwo-modal.mewtwo-modal--whitelabel_en")
    protected SelenideElement datesCalendarSection;

    @FindBy(css = "span.mewtwo-popup-ages-counter__plus")
    protected ElementsCollection plusGuestButtons;

    @FindBy(css = "span.mewtwo-popup-ages-counter__minus")
    protected ElementsCollection minusGuestButtons;

    @FindBy(css = "span.mewtwo-popup-ages-counter__amount")
    protected ElementsCollection numberOfGuests;

    @FindBy(css = "div.main_gate-button")
    protected ElementsCollection bookButton;

    @FindBy(css = "a.card-gates_list-item-deeplink")
    protected ElementsCollection partnerPrices;

    protected String calendarDateSelectorBase = "td#mewtwo-datepicker-";

    public void searchFormTitleValidation() {
        logger.info("Check if search form tile is displayed");
        logger.info("Check if search form tile content is valid");
        searchFormTitle.waitUntil(visible, 30000);
        searchFormTitle.shouldBe(visible).shouldHave(text(testConf.searchFormTitle()));
    }

    public void settingsDropdownValidation() {
        logger.info("Check if settings dropdown element is dispalyed");
        settingsDropdown.waitUntil(visible, 30000);
        settingsDropdown.shouldBe(visible);
    }

    public void flightsHotelsSwitcherValidation() {
        logger.info("Check if switcher between flighs and hotels is displayed");
        flightsHotelsSwitcher.waitUntil(visible, 30000);
        flightsHotelsSwitcher.shouldBe(visible);
    }

    public void validateSearchHotelForm() {
        logger.info("Check if search hotels form is displayed by default");
        logger.info("Validate search hotels form content");
        searchConteiner.waitUntil(visible, 30000);
        searchConteiner.shouldBe(visible);
        searchInput.shouldBe(visible);
        checkInSelection.shouldBe(visible);
        checkOutSelection.shouldBe(visible);
        numberOfGuestsSelection.shouldBe(visible);
        searchSubmit.shouldBe(visible);

    }

    public void searchHotelByCityName(String cityName) {
        logger.info("Search hotel by valid city name: " + cityName);
        searchInput.clear();
        searchInput.click();
        searchInput.setValue(cityName);
        logger.info("Verify that at least one result is returned");
        hotelSearchResults.shouldHave(sizeGreaterThan(0));
        logger.info(hotelSearchResults.size() + " results were found for city: " + cityName);
        assertTrue("Search results does not contains expected city",
                hotelSearchResults.get(0).getText().contains(cityName));
    }

    public void clickOnCheckInBox() {
        logger.info("Click on check in box");
        checkInSelection.click();
    }

    public void clickOnCheckInDate() {
        String date = getCurentDate();
        logger.info("Select check in date: " + date);
        SelenideElement currentDayInCalendar = $(calendarDateSelectorBase + date);
        currentDayInCalendar.click();
    }

    public void clickOnCheckOutDate() {
        String date = getCurentDatePlusOneWeek();
        logger.info("Select check out date: " + date);
        SelenideElement currentDayPlusWeekInCalendar = $(calendarDateSelectorBase + date);
        currentDayPlusWeekInCalendar.click();
    }

    public void clickOnGuestsBox() {
        logger.info("Click on guests box");
        numberOfGuestsSelection.click();
    }

    public String getNumberOfGuests() {
        String guests = numberOfGuests.get(0).getAttribute("innerHTML");
        logger.info("Number of guests in the box: " + guests);
        return guests;
    }

    public void increaseNumberOfGuests() {
        clickOnGuestsBox();
        String numberOfGuestsBeforeChange = getNumberOfGuests();
        logger.info("Increase mumber of guests");
        plusGuestButtons.get(0).click();
        String numberOfGuestsAfterChange = getNumberOfGuests();
        assertNotEquals("Number of guests was not changed", numberOfGuestsBeforeChange, numberOfGuestsAfterChange);
    }

    public void decreaseNumberOfGuests() {
        logger.info("Decrease mumber of guests");
        minusGuestButtons.get(0).click();
    }

    public void clickOnFirstSearchResult() {
        logger.info("Click on first search result");
        hotelSearchResults.get(0).click();
    }

    public void submitSearch() {
        logger.info("Click on submit search");
        searchSubmit.click();
    }

    public void validateSearchResults() {
        logger.info("Validate search results");
        searchResultsCounter.waitUntil(visible, 30000);
        bookButton.get(0).waitUntil(visible, 30000);
        bookButton.shouldHave(sizeGreaterThan(0));
        logger.info(bookButton.size() + " hotels were found");
    }

    public void clickOnBookButton() {
        logger.info("Click on Book button");
        bookButton.get(0).click();
    }

    public void clickOnPartnerPriceLink() {
        logger.info("Click on partner price link");
        partnerPrices.get(0).waitUntil(visible, 30000);
        partnerPrices.get(0).click();
    }

    public String getCurentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-d");
        return format1.format(cal.getTime());
    }

    public String getCurentDatePlusOneWeek() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-d");
        return format1.format(cal.getTime());
    }
}