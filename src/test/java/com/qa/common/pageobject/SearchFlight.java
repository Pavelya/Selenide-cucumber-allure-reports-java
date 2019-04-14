package com.qa.common.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import static org.junit.Assert.assertNotEquals;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchFlight {

    WebDriverWait wait = new WebDriverWait(getWebDriver(), 60);

    private static Logger logger = LoggerFactory.getLogger(SearchFlight.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    @FindBy(css = "div.ie-fixMinHeight")
    protected SelenideElement mainPageDiv;

    @FindBy(css = "li.mewtwo-tabs-tabs_list__item.mewtwo-tabs-tabs_list__item--count2.mewtwo-tabs-tabs_list__item--flights")
    protected SelenideElement flightsSwitchSelector;

    @FindBy(css = "ul.mewtwo-tabs_list")
    protected SelenideElement flightsHotelsSwitcher;

    @FindBy(css = "div.mewtwo-flights-container")
    protected SelenideElement flightsConteiner;

    @FindBy(css = "input#flights-origin-prepop-whitelabel_en")
    protected SelenideElement origin;

    @FindBy(name = "destination_name")
    protected SelenideElement destination;

    @FindBy(css = "div.mewtwo-flights-dates-depart")
    protected SelenideElement departDateSelection;

    @FindBy(css = ".mewtwo-flights-trip_class-wrapper.mewtwo-flights-trip_class-wrapper")
    protected SelenideElement numberOfPassengerselection;

    @FindBy(css = "div.mewtwo-flights-submit_button")
    protected SelenideElement searchSubmit;

    @FindBy(css = "li.mewtwo-autocomplete-list-item.mewtwo-autocomplete-list-item--shifted")
    protected ElementsCollection originFlightSearchResults;

    @FindBy(css = "li.mewtwo-autocomplete-list-item")
    protected ElementsCollection destinationFlightSearchResults;

    @FindBy(css = "div.search_results-wrapper.js-results-inner-wrapper")
    protected SelenideElement searchResultsCounter;

    @FindBy(css = "div.mewtwo-modal.mewtwo-modal--whitelabel_en")
    protected SelenideElement datesCalendarSection;

    @FindBy(css = "span.mewtwo-popup-ages-counter__plus")
    protected ElementsCollection plusGuestButtons;

    @FindBy(css = "span.mewtwo-popup-ages-counter__amount")
    protected ElementsCollection numberOfPassengeres;

    @FindBy(css = "div.mewtwo-flights-submit_button")
    protected ElementsCollection searchButton;

    @FindBy(css = "div.ticket-action-button.ticket-action-button--")
    protected ElementsCollection flightBookButtons;

    @FindBy(css = "li.ticket-action-proposals-item")
    protected ElementsCollection specialPriceLinks;

    protected String calendarDateSelectorBase = "td#mewtwo-datepicker-DATE_PLACEHOLDER.mewtwo-datepicker-current-date.mewtwo-datepicker-current";

    public void clickOnFlightSwitch() {
        logger.info("Click on flights switch selector");
        flightsSwitchSelector.click();
    }

    public void validateSearchFlightForm() {
        logger.info("Check if search flight form is displayed by default");
        logger.info("Validate search flight form content");
        flightsConteiner.shouldBe(visible);
        origin.shouldBe(visible);
        destination.shouldBe(visible);
        departDateSelection.shouldBe(visible);
        numberOfPassengerselection.shouldBe(visible);
        searchSubmit.shouldBe(visible);
    }

    public void searchOriginByCityName(String cityName) {
        logger.info("Search origin by valid city name: " + cityName);
        origin.click();
        origin.setValue(cityName);
        logger.info("Verify that at least one result is returned");
        originFlightSearchResults.shouldHave(sizeGreaterThan(0));
        logger.info(originFlightSearchResults.size() + " results were found for city: " + cityName);
    }

    public void searchDestinationByCityName(String cityName) {
        logger.info("Search destination by valid city name: " + cityName);
        destination.click();
        destination.setValue(cityName);
        logger.info("Verify that at least one result is returned");
        destinationFlightSearchResults.shouldHave(sizeGreaterThan(0));
        logger.info(destinationFlightSearchResults.size() + " results were found for city: " + cityName);
    }

    public void clickOnDepartDateBox() {
        logger.info("Click on depart date box");
        departDateSelection.click();
    }

    public void clickOnDepartureDate() {
        String date = getCurentDate();
        logger.info("Select departure date: " + date);
        SelenideElement currentDayInCalendar = $(calendarDateSelectorBase.replace("DATE_PLACEHOLDER", date));
        currentDayInCalendar.click();
    }

    public void clickOnReturnDate() {
        String date = getCurentDatePlusOneWeek();
        logger.info("Select return date: " + date);
        SelenideElement currentDayPlusWeekInCalendar = $(calendarDateSelectorBase.replace("DATE_PLACEHOLDER", date));
        currentDayPlusWeekInCalendar.click();
    }

    public void clickOnPassengerBox() {
        logger.info("Click on passenger box");
        numberOfPassengerselection.click();
    }

    public String getNumberOfPassengeres() {
        String Passengeres = numberOfPassengeres.get(0).getAttribute("innerHTML");
        logger.info("Number of Passengeres in the box: " + Passengeres);
        return Passengeres;
    }

    public void increaseNumberOfPassengers() {
        clickOnPassengerBox();
        String numberOfPassengeresBeforeChange = getNumberOfPassengeres();
        logger.info("Increase mumber of passengers");
        plusGuestButtons.get(0).click();
        String numberOfPassengeresAfterChange = getNumberOfPassengeres();
        assertNotEquals("Number of Passengeres was not changed", numberOfPassengeresBeforeChange,
                numberOfPassengeresAfterChange);
    }

    public void clickOnFirstOriginSearchResult() {
        logger.info("Click on first origin search result");
        originFlightSearchResults.get(0).click();
    }

    public void clickOnFirstDestinationSearchResult() {
        logger.info("Click on first destination search result");
        destinationFlightSearchResults.get(0).click();
    }

    public void submitSearch() {
        logger.info("Click on submit search");
        searchSubmit.click();
        logger.info("Wait till search will be completed");
        wait.until(ExpectedConditions
                .not(ExpectedConditions.textToBePresentInElement(mainPageDiv, testConf.searchResultsLoader())));
    }

    public void validateSearchResults() {
        logger.info("Validate search results");
        searchResultsCounter.waitUntil(visible, 30000);
        flightBookButtons.get(0).waitUntil(visible, 30000);
        flightBookButtons.shouldHave(sizeGreaterThan(0));
        logger.info(flightBookButtons.size() + " flights were found");
    }

    public void clickOnBookButton() {
        logger.info("Click on book button for first located flight");
        flightBookButtons.get(0).click();
    }

    public void clickOnSpecialPriceLink() throws InterruptedException {
        logger.info("Click on special price button in flight details frame");
        if (specialPriceLinks.size() > 0) {
            logger.info(specialPriceLinks.size() + " special price flights were found");
            for (int i = 0; i < specialPriceLinks.size(); i++) {
                // click if special price link element is visible
                if (specialPriceLinks.get(i).is(visible)) {
                    specialPriceLinks.get(i).click();
                    validateRedirectToPartnerSite();
                    break;
                }
            }
        } else {
            logger.info("No special price links found");
        }
    }

    public void validateRedirectToPartnerSite() {
        logger.info("Validate redirect to partner site");
        // navigate to new tab
        switchTo().window(1);
        logger.info("User is on: " + url() + " site");
        assertNotEquals("User is not redirected to external provider site", url(), testConf.mainPage());
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