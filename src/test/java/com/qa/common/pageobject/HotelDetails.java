package com.qa.common.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.switchTo;

public class HotelDetails {

    private static Logger logger = LoggerFactory.getLogger(HotelDetails.class);
    @FindBy(css = "address.hotel_page-address")
    protected SelenideElement hotelAddress;

    @FindBy(css = "div.hotel_page-reviews_details")
    protected SelenideElement hotelRatingAndReviewsSection;

    @FindBy(css = "div.main_photo-price_block-button")
    protected SelenideElement lowestPriceButton;

    @FindBy(css = "div.galery_photo-wrapper")
    protected SelenideElement photoGalerySection;

    @FindBy(css = "div.map-photo-wrapper")
    protected SelenideElement mapSection;

    @FindBy(css = "div.card-hotel_name")
    protected ElementsCollection hotelNames;

    @FindBy(css = "span.hotel_page-serp_link__link.hotel_page-serp_link__link--crumbs")
    protected SelenideElement hotelNameInBreadCrumbsNavigation;

    private String hotelName;

    public String getHotelNameAfterSearch() {
        hotelName = hotelNames.get(0).getText();
        return hotelName;
    }

    public void clickOnHotelLink() {
        getHotelNameAfterSearch();
        logger.info("Click on hotel name to open detailed hotel page");
        hotelNames.get(0).click();
    }

    public void hotelDetailsPageValidation() {
        // navigate to new tab
        switchTo().window(1);
        logger.info("Check if hotel details page is dispalyed with valid content");
        hotelNameInBreadCrumbsNavigation.shouldBe(visible);
        assertTrue("Hotel detailed page is not displayed well",
                hotelNameInBreadCrumbsNavigation.getText().equalsIgnoreCase(hotelName));
        logger.info("Validate address displayed");
        hotelAddress.shouldBe(visible);
        logger.info("Validate rating displayed");
        hotelRatingAndReviewsSection.shouldBe(visible);
        logger.info("Validate lowest price displayed");
        lowestPriceButton.shouldBe(visible);
        logger.info("Validate photo galery displayed");
        photoGalerySection.shouldBe(visible);
        logger.info("Validate map displayed");
        mapSection.shouldBe(visible);
    }
}