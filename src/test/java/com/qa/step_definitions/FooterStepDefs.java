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
    }
    
    @Given("^cookie baner is displayed with valid content$")
    public void cookieBannerValidation() throws Throwable {
        footer.validatePresenceOfCookieBanner(true);
    }
    
    @Given("^user can close the cookie banner$")
    public void closeCookieBanner() throws Throwable {
        footer.closeCookieBannerIfExists();
        footer.validatePresenceOfCookieBanner(false);
    }
    @Given("^user can launch terms and condition links$")
    public void openTermsLinks() throws Throwable {
        footer.closeCookieBannerIfExists();
        footer.validatePresenceOfCookieBanner(false);
        footer.openTermsPage();
        footer.openPrivacyPolicyPage();
    }
}
