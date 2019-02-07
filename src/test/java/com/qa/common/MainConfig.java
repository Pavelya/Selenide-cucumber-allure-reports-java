package com.qa.common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "file:src/test/resources/MainConfig.properties" })
public interface MainConfig extends Config {

    String bs_user();

    String bs_key();

    String bs_server();

    String bs_project();

    String bs_build();

    String bs_debug();
    
    String bs_hub();

    String allure_results_folder();

    String allure_screenshots_folder();
    
    String bs_devices_config();

}
