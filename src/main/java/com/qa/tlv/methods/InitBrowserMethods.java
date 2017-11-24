package com.qa.tlv.methods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.tlv.environment.BaseTest;
import com.qa.tlv.logger.Log;

public class InitBrowserMethods implements BaseTest {

	private WebDriver driver;

	static String FireFoxDriverPath;

	public WebDriver getDriverBySelectedBrowserType() {
		String browserName = System.getProperty("browser");

		switch (browserName) {
		case "chrome":
			driver = new ChromeDriver();
			Log.INFO("Creating chrome browser");
			break;
		case "firefox":
			driver = new FirefoxDriver();
			Log.INFO("Creating firefox browser");
			break;
		default:
			driver = new ChromeDriver();
			Log.INFO("No browser was selected or wrong browser type was selected, switching to default - chrome");
			break;
		}
		return driver;

	}
}