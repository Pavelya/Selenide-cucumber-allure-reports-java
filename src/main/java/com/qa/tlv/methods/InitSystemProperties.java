package com.qa.tlv.methods;

import com.qa.tlv.environment.BaseTest;
import com.qa.tlv.environment.ChromeDriverSetup;
import com.qa.tlv.environment.FireFoxDriverSetup;

public class InitSystemProperties implements BaseTest{

	ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup();
	FireFoxDriverSetup fireFoxDriverSetup = new FireFoxDriverSetup();

	public void setWebdriverSystemProperty() {

		System.setProperty("webdriver.chrome.driver", chromeDriverSetup.getChromeDriverPath());
		System.setProperty("webdriver.gecko.driver", fireFoxDriverSetup.getFireFoxDriverPath());
	}

}