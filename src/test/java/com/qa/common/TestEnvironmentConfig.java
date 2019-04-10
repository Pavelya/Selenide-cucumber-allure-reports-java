package com.qa.common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

public class TestEnvironmentConfig {

    @Sources({ "${pathToEnvConfig}" })
    public interface TestEnvConfig extends Config {
        String googleUrl();

        String searchFormTitle();

        String mainPage();

        String mainLogoText();

        String descritionText();

        String copyrightSectionText();

        String termsPage();

        String privacyPolicyPage();

        String cookiePolicyPage();
    }

    public static TestEnvConfig createTestEnvConfig() {
        String pathToEnvConfig = "file:src/test/resources/" + getEnvConfig() + ".environment.properties";
        ConfigFactory.setProperty("pathToEnvConfig", pathToEnvConfig);
        return ConfigFactory.create(TestEnvConfig.class);
    }

    private static String getEnvConfig() {
        return System.getProperty("env", "prod");
    }
}