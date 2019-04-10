package com.qa.common.pageobject;

import com.codeborne.selenide.SelenideElement;
import com.qa.common.TestEnvironmentConfig.TestEnvConfig;

import static org.junit.Assert.assertTrue;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.qa.common.TestEnvironmentConfig.createTestEnvConfig;

public class Header {

    private static Logger logger = LoggerFactory.getLogger(Header.class);
    private TestEnvConfig testConf = createTestEnvConfig();

    @FindBy(css = "div.TPWL-header-content")
    protected SelenideElement headerDiv;

    @FindBy(css = "div.TPWL-header-content__logo")
    protected SelenideElement pageLogo;

    @FindBy(css = "div.TPWL-header-content__descrition")
    protected SelenideElement descritionDiv;

    public void headerSectionValidation() {
        logger.info("Verify header is shown");
        headerDiv.shouldBe(visible);
    }

    public void logoValidation() {
        logger.info("Validate logo");
        pageLogo.shouldBe(visible);
        assertTrue("Wrong page logo text", pageLogo.getAttribute("innerHTML").contains(testConf.mainLogoText()));
    }

    public void descritionValidation() {
        logger.info("Validate descrition");
        descritionDiv.shouldBe(visible);
        assertTrue("Wrong page descrition text",
                descritionDiv.getAttribute("innerHTML").contains(testConf.descritionText()));
    }
}