package com.qa.common.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

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

public class SearchHotelsForm {

    WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);

    private static Logger logger = LoggerFactory.getLogger(SearchHotelsForm.class);
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

    public void searchFormTitleValidation() {
        logger.info("Check if search form tile is displayed");
        logger.info("Check if search form tile content is valid");
        searchFormTitle.shouldBe(visible).shouldHave(text(testConf.searchFormTitle()));
    }

    public void settingsDropdownValidation() {
        logger.info("Check if settings dropdown element is dispalyed");
        settingsDropdown.shouldBe(visible);
    }

    public void flightsHotelsSwitcherValidation() {
        logger.info("Check if switcher between flighs and hotels is displayed");
        flightsHotelsSwitcher.shouldBe(visible);
        // TODO: add functionality of click. Not part of POC
    }

    public void validateSearchHotelForm() {
        logger.info("Check if search hotels form is displayed by default");
        logger.info("Validate search hotels form content");
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
        logger.info(hotelSearchResults.size() + " results were found");
        assertTrue("Search results does not contains expected city",
                hotelSearchResults.get(0).getText().contains(cityName));
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
        // TO DO: extent this test. For POC only checking that element is exists
        searchResultsCounter.waitUntil(visible, 30000);
    }

}