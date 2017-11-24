package com.qa.tlv.environment;

import com.qa.tlv.methods.PropertiesManagementMethods;

public class FireFoxDriverSetup {

	static PropertiesManagementMethods propertiesObj = new PropertiesManagementMethods();

	static String FireFoxDriverPath;

	public String getFireFoxDriverPath() {

		if (propertiesObj.isWindows()) {
			FireFoxDriverPath = propertiesObj.getSeleniumProperty("winFireFoxDrverPath");
		}

		else if (propertiesObj.isMac()) {
			FireFoxDriverPath = propertiesObj.getSeleniumProperty("macFireFoxDrverPath");
		}

		return FireFoxDriverPath;
	}

}