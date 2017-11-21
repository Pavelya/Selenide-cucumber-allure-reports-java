package com.qa.tlv.pageObjects;

import static com.codeborne.selenide.Selenide.$;

import java.io.IOException;

import org.openqa.selenium.By;

import com.qa.tlv.environment.BaseTest;
import com.qa.tlv.logger.Log;
import com.qa.tlv.methods.BrowserUtils;

/**
 * Template Base class.
 * 
 * @author Pavel Yampolsky
 *
 */

public class LoginWindowPageObject implements BaseTest {
	
	//BrowserUtils browser = new BrowserUtils();
	

	
	// environment
	String environment;

	// elements
	String usernameId;
	String username;
	String password;

	public LoginWindowPageObject() {

		// elements
		usernameId = "username";
		username = propertiesObj.getProperty("username");
		password = propertiesObj.getProperty("password");

	}

	public LoginWindowPageObject enterUsername() throws IOException {

		Log.INFO("User Enter username to page");
		browserObj.enterTextById(usernameId, username);
		return this;
	}

	public LoginWindowPageObject enterPassword() throws IOException {

		Log.INFO("Enter password");
		browserObj.enterTextById(password, password);
		
		return this;
	}
	
}
