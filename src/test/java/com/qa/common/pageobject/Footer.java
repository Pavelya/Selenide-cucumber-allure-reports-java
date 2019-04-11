package com.qa.common.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.net.URL;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;

public class Footer {

    WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);

    private static Logger logger = LoggerFactory.getLogger(Footer.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    @FindBy(css = "div.TPWL-footer-content")
    protected SelenideElement footerDiv;

    @FindBy(css = "div.TPWL-footer-content__copyright")
    protected SelenideElement copyrightSection;

    @FindBy(css = "div.TPWL-footer-content__descrition")
    protected SelenideElement termsLinks;

    @FindBy(css = "div.policy-bar.policy-bar--show")
    protected SelenideElement cookiePolicyBanner;

    @FindBy(css = "p.policy-bar__text")
    protected SelenideElement cookiePolicyBannerText;

    @FindBy(css = "button.policy-bar__close")
    protected SelenideElement cookiePolicyBannerCloseButton;

    private boolean cookieBannerChecked = false;

    public void footerSectionValidation() {
        logger.info("Verify footer is shown");
        footerDiv.shouldBe(visible);
    }

    public void copyrightSectionValidation() {
        logger.info("Validate copyright section");
        copyrightSection.shouldBe(visible);
        assertTrue("Wrong copyright section text",
                copyrightSection.getAttribute("innerHTML").contains(testConf.copyrightSectionText()));
    }

    public void termsLinksValidation() {
        logger.info("Validate terms links");
        termsLinks.shouldBe(visible);
        ElementsCollection footerLinks = termsLinks.$$("a");
        assertEquals(footerLinks.get(0).getAttribute("href"), testConf.termsPage());
        assertEquals(footerLinks.get(1).getAttribute("href"), testConf.privacyPolicyPage());
        assertEquals(footerLinks.get(2).getAttribute("href"), testConf.cookiePolicyPage());
    }

    public void closeCookieBannerIfExists() {
        if (!cookieBannerChecked) {
            try {
                wait.until(elementToBeClickable(cookiePolicyBannerCloseButton));
                logger.info("Click on cookie banner to close");
                cookiePolicyBannerCloseButton.click();
            } catch (Exception e) {
                logger.info("Cookie banner is not displayed, skip and continue to next step");
            } finally {
                cookieBannerChecked = true;
            }
        }
    }

    public void validatePresenceOfCookieBanner(boolean shouldBeVisible) {
        Condition expectedCondition = shouldBeVisible ? visible : hidden;
        cookiePolicyBanner.waitUntil(expectedCondition, 5000);
        if (expectedCondition.equals(visible)) {
            cookiePolicyBannerCloseButton.shouldBe(expectedCondition);
            cookiePolicyBannerText.shouldBe(expectedCondition);
        }
    }

    public void openTermsPage() {
        logger.info("Verify terms page can be launched from footer");
        ElementsCollection footerLinks = termsLinks.$$("a");
        footerLinks.get(0).click();
        switchTo().window(1);
        assertTrue("Terms page is not launched", url().contains(testConf.termsPage()));
    }
}