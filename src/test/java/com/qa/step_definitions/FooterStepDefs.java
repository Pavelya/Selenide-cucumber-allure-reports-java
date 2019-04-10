package com.qa.step_definitions;

import static com.codeborne.selenide.Selenide.page;

import com.qa.common.pageobject.Footer;
import cucumber.api.java.en.Given;

public class FooterStepDefs {

    private Footer footer = page(Footer.class);

    @Given("^footer is displayed with valid content$")
    public void footerValidation() throws Throwable {
        footer.footerSectionValidation();
        footer.copyrightSectionValidation();
        footer.termsLinksValidation();
        footer.validatePresenceOfCookieBanner(true);
        footer.closeCookieBannerIfExists();
        footer.validatePresenceOfCookieBanner(false);
        footer.openTermsPage();
    }
}
