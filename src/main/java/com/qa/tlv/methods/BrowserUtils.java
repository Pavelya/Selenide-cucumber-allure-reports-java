package com.qa.tlv.methods;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.ClassRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.tlv.environment.BaseTest;
import com.qa.tlv.environment.ChromeDriverSetup;
import com.qa.tlv.logger.Log;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import static com.codeborne.selenide.Selenide.*;

public class BrowserUtils extends SelectElementByType implements BaseTest {
	private String urlToNavigate = null;
	private WebElement dropdown = null;
	private Select selectList = null;

	private WebDriver chrome;


	static ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup();

	////////////////////
	// NAVIGATION METHODS
	////////////////////

	/**
	 * Method to open link
	 * 
	 * @param url
	 *            : String : URL for navigation
	 */

	public void navigateTo(String url) {

		System.setProperty("webdriver.chrome.driver", chromeDriverSetup.getChromeDriverPath());

		chrome = new ChromeDriver();
		WebDriverRunner.setWebDriver(chrome);

		// get url from feature file
		if (url.contains("http")) {
			urlToNavigate = url;
		}

		// get url value from prop file
		else {
			urlToNavigate = propertiesObj.getProperty(url);
		}

		Log.INFO("Navigate to: " + urlToNavigate);
		open(urlToNavigate);

	}

	/**
	 * Method to navigate back & forward
	 * 
	 * @param direction
	 *            : String : Navigate to forward or backward
	 */
	public void navigate(String direction) {
		if (direction.equals("back"))
			back();
		else
			forward();
	}
	
	/**
	 * Method to refresh
	 * 
	 */
	public void refresh() {
			refresh();
	}

	/** Method to quite webdriver instance */
	public void closeDriver() {
		close();

	}

	///////////////////
	// ASSERTION METHIDS
	///////////////////

	/**
	 * Method to get page title
	 * 
	 * @return String
	 */
	public String getPageTitle() {
		String pageTitle = title();
		Log.INFO("Page title: " + pageTitle);
		return pageTitle;
	}

	/**
	 * Method to verify page title
	 * 
	 * @param title
	 *            : String : expected title
	 */
	public void checkTitle(String title) throws TestCaseFailed {
		String pageTitle = title();

		if (!pageTitle.equals(title)) {
			throw new TestCaseFailed(
					"Page Title Not matched, Expected Title: " + title + ", Actual Page Title : " + pageTitle);
		} else {
			Log.INFO("Page Title matched, Actual Page Title : " + pageTitle);
		}

	}

	/**
	 * Method to verify partial page title
	 * 
	 * @param partialTitle
	 *            : String : partial title string
	 */
	public void checkPartialTitle(String partialTitle) throws TestCaseFailed {
		String pageTitle = title();

		if (!pageTitle.contains(partialTitle)) {
			throw new TestCaseFailed(
					"Partial Page Title: " + partialTitle + " Not Present, Actual Page Title : " + pageTitle);
		} else {
			Log.INFO("Page Title matched, Actual Page Title : " + pageTitle);
		}
	}

	/**
	 * Method to get element text by id
	 * 
	 * @param id
	 *            : String : id value
	 * @return String
	 */
	public String getElementTextById(String id) {
		return $(By.id(id)).getText();
	}

	/**
	 * Method to get element text by cssSelector
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 * @return String
	 */
	public String getElementTextByCSS(String cssSelector) {
		return $(cssSelector).getText();
	}

	/**
	 * Method to get element text by className
	 * 
	 * @param className
	 *            : String : className value
	 * @return String
	 */
	public String getElementTextByClassName(String className) {
		return $(By.className(className)).getText();
	}

	/**
	 * Method to get element text by xpathExpression
	 * 
	 * @param xpathExpression
	 *            : String : xpathExpression value
	 * @return String
	 */
	public String getElementTextByxpathExpression(String xpathExpression) {
		return $(By.xpath(xpathExpression)).getText();
	}

	/**
	 * Method to check element text with expected one by cssSelector
	 * 
	 * @param expectedText
	 *            : String : Expected element text
	 * @param cssSelector
	 *            : String : cssSelector
	 */
	public void checkElementText(String expectedText, String cssSelector) throws TestCaseFailed {

		String actualText = getElementTextByCSS(cssSelector);
		if (!expectedText.equals(actualText)) {
			throw new TestCaseFailed("Expected Text: " + expectedText + " not matched the actual one: " + actualText);
		} else {
			Log.INFO("Text matched");
		}
	}

	/**
	 * Method to check partial element text with expected one by cssSelector
	 * 
	 * @param actualValue
	 *            : String : Expected element text
	 * @param accessName
	 *            : String : Locator value
	 */
	public void checkElementPartialText(String expectedPatrialText, String cssSelector) throws TestCaseFailed {
		String actualText = getElementTextByCSS(cssSelector);

		if (!actualText.contains(expectedPatrialText)) {
			throw new TestCaseFailed(
					"Expected Text: " + expectedPatrialText + " not matched the actual one: " + actualText);
		} else {
			Log.INFO("Text matched");
		}
	}

	/**
	 * Method to check if element enabled checking by cssSelector
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void checkIfElementEnabledByCssSelector(String cssSelector) throws TestCaseFailed {
		boolean result = $(cssSelector).isEnabled();
		if (!result) {
			throw new TestCaseFailed("Element: with selector" + cssSelector + " not enabled");
		} else {
			Log.INFO("Element enabled");
		}
	}

	/**
	 * method to get attribute value by cssSeletor
	 * 
	 * @param accessName
	 *            : String : Locator value
	 * @param attributeName
	 *            : String : attribute name
	 * @return String
	 */
	public String getElementAttributeByCssSelector(String cssSeletor, String attributeName) {
		return $(cssSeletor).getAttribute(attributeName);

	}

	/**
	 * method to check if element displayed by cssSelector
	 * 
	 * @param accessName
	 *            : String : Locator value
	 * @return Boolean
	 */
	public void checkIfElementDisplayedByCssSelector(String cssSelector) throws TestCaseFailed {
		boolean result = $(cssSelector).isDisplayed();
		if (!result) {
			throw new TestCaseFailed("Element: with selector" + cssSelector + " not displayed");
		} else {
			Log.INFO("Element displayed");
		}
	}

	/**
	 * method to check if element not displayed by cssSelector
	 * 
	 * @param accessName
	 *            : String : Locator value
	 * @return Boolean
	 */
	public void checkIfElementNotDisplayedByCssSelector(String cssSelector) throws TestCaseFailed {
		boolean result = $(cssSelector).isDisplayed();
		if (result) {
			throw new TestCaseFailed("Element: with selector" + cssSelector + " displayed");
		} else {
			Log.INFO("Element not displayed");
		}
	}

	/**
	 * method to assert checkbox check/uncheck by cssSelector
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 * @param shouldBeChecked
	 */
	public void isCheckboxChecked(String cssSelector, boolean shouldBeChecked) throws TestCaseFailed {
		boolean ifCheckboxSelected = $(cssSelector).isSelected();

		if ((!ifCheckboxSelected) && shouldBeChecked) {
			throw new TestCaseFailed("Checkbox / radio button is not checked");
		} else if (ifCheckboxSelected && !shouldBeChecked) {
			throw new TestCaseFailed("Checkbox / radio button is checked");
		}
	}

	/**
	 * method to get javascript pop-up alert text
	 * 
	 * @return String
	 */
	public String getAlertText() {
		return chrome.switchTo().alert().getText();
	}

	/**
	 * method to check javascript pop-up alert text
	 * 
	 * @param text
	 *            : String : Text to verify in Alert
	 * @throws TestCaseFailed
	 */
	public void checkAlertText(String text) throws TestCaseFailed {
		if (!getAlertText().equals(text))
			throw new TestCaseFailed("Text on alert pop up not matched");
	}

	///////////////////////////
	// CLICK ON ELEMENTS METHODS
	///////////////////////////

	/**
	 * Method to click on an element by cssSelector
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void clickByCssSelector(String cssSelector) {
		$(By.cssSelector(cssSelector)).click();
	}

	/**
	 * Method to click on an element by id
	 * 
	 * @param cssSelector
	 *            : String : is value
	 */
	public void clickById(String id) {
		$(By.id(id)).click();
	}

	/**
	 * Method to click on an element by xpathExpression
	 * 
	 * @param xpathExpression
	 *            : String : xpathExpression value
	 */
	public void clickByXpath(String xpathExpression, String accessName) {
		$(By.xpath(xpathExpression)).click();
	}

	/**
	 * Method to Double click on an element by cssLocator
	 * 
	 * @param accessName
	 *            : String : Locator value
	 */
	public void doubleClick(String cssSelector, String accessValue) {

		Actions action = new Actions(chrome);
		action.moveToElement($(cssSelector)).doubleClick().perform();
	}

	/////////////////////////////////////////
	// PRINT TEST STAND CONFIGURATION METHODS
	////////////////////////////////////////

	/** Method to print desktop configuration */
	public void printDesktopConfiguration() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		Log.INFO("Test started: " + dateFormat.format(cal.getTime()));
	}

	////////////////
	// INPUT METHODS
	///////////////

	/**
	 * Method to enter text into text field by cssSelector
	 * 
	 * @param textToEnter
	 *            : String : Text value to enter in field
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void enterTextByCss(String cssSelector, String textToEnter) {
		$(cssSelector).clear();
		$(cssSelector).setValue(textToEnter);
	}

	/**
	 * Method to enter text into text field by id
	 * 
	 * @param textToEnter
	 *            : String : Text value to enter in field
	 * @param id
	 *            : String : id value
	 */
	public void enterTextById(String id, String textToEnter) {
		$(By.id(id)).clear();
		$(By.id(id)).setValue(textToEnter);
	}

	
	/**
	 * Method to select element from Dropdown by type
	 * 
	 * @param select_list
	 *            : Select : Select variable
	 * @param bytype
	 *            : String : Name of by type
	 * @param option
	 *            : String : Option to select
	 */
	public void selectelementfromdropdownbytype(Select select_list, String bytype, String option) {
		if (bytype.equals("selectByIndex")) {
			int index = Integer.parseInt(option);
			select_list.selectByIndex(index - 1);
		} else if (bytype.equals("value"))
			select_list.selectByValue(option);
		else if (bytype.equals("text"))
			select_list.selectByVisibleText(option);
	}


	/**
	 * Method to unselect all option from dropdwon list by cssSelector
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void unselectAllOptionFromMultiselectDropdown(String cssSelector) {
		dropdown = $(cssSelector);
		selectList = new Select(dropdown);
		selectList.deselectAll();
	}

	/**
	 * Method to unselect option from dropdwon list
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 *            
	 */
	public void deselectOptionFromDropdown(String cssSelector, String optionBy, String option) {
		dropdown = $(cssSelector);
		selectList = new Select(dropdown);

		if (optionBy.equals("selectByIndex"))
			selectList.deselectByIndex(Integer.parseInt(option) - 1);
		else if (optionBy.equals("value"))
			selectList.deselectByValue(option);
		else if (optionBy.equals("text"))
			selectList.deselectByVisibleText(option);
	}

	/**
	 * Method to check check-box
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void checkCheckbox(String cssSelector) {
		WebElement checkbox = $(cssSelector);
		if (!checkbox.isSelected())
			checkbox.click();
	}

	/**
	 * Method to uncheck check-box
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void uncheckCheckbox(String cssSelector) {
		WebElement checkbox = $(cssSelector);
		if (checkbox.isSelected())
			checkbox.click();
	}


	/**
	 * Method to select radio button
	 * 
	 * @param cssSelector
	 *            : String : cssSelector value
	 */
	public void selectRadioButton(String cssSelector) {
		WebElement radioButton = $(cssSelector);
		if (!radioButton.isSelected())
			radioButton.click();
	}

	/**
	 * Method to select option from radio button group
	 * 
	 * @param by
	 *            : String : Name of by type
	 * @param option
	 *            : String : Option to select
	 * @param cssSelector
	 *            : String : cssSelector value
	 * @param accessName2
	 */
	public void selectOptionFromRadioButtonGroup(String cssSelector, String option, String by) {
		List<SelenideElement> radioButtonGroup = $$(cssSelector);
		for (WebElement rb : radioButtonGroup) {
			if (by.equals("value")) {
				if (rb.getAttribute("value").equals(option) && !rb.isSelected())
					rb.click();
			} else if (by.equals("text")) {
				if (rb.getText().equals(option) && !rb.isSelected())
					rb.click();
			}
		}
	}

	/////////////////////////
	// HANDLE ALLERTS METHODS
	////////////////////////

	/**
	 * Method to handle alert
	 * 
	 * @param decision
	 *            : String : Accept or dismiss alert
	 */
	public void handleAlert(String decision) {
		if (decision.equals("accept"))
			chrome.switchTo().alert().accept();
		else
			chrome.switchTo().alert().dismiss();
	}

	////////////////////////
	// DELETE COOKIES METHODS
	////////////////////////
	/**
	 * Method to delete cookie
	 */
	public void deleteCookies() {

		clearBrowserCookies();

	}

	//////////////////////////////
	// WAITING FOR ELEMENTS METHODS
	//////////////////////////////

	/**
	 * Method to wait
	 * 
	 * @param time
	 *            : String : Time to wait
	 * @param method
	 *            : String : wait by sleep or implicit method
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public void wait(String time) throws NumberFormatException, InterruptedException {
		// sleep method takes parameter in milliseconds
		Log.INFO("Wait: " + time + " sec");
		Thread.sleep(Integer.parseInt(time) * 1000);
	}

	
	///////////////////////
	// SCREEN SHOTS METHODS
	///////////////////////

	public String getSnapshotFolderPath() {
		File currentDirFile = new File("Screenshots");
		String path = currentDirFile.getAbsolutePath();

		return path;
	}

	/**
	 * Method to take screen shot and save in ./Screenshots folder
	 * 
	 * @return
	 */
	public String takeScreenShot() throws IOException {

		Log.INFO("Taking snapshot");
		File scrFile = ((TakesScreenshot) chrome).getScreenshotAs(OutputType.FILE);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();

		String snapshotFileName = "screenshot" + dateFormat.format(cal.getTime()) + ".png";
		String pathToSnapshot = getSnapshotFolderPath() + "/" + snapshotFileName;

		FileUtils.copyFile(scrFile, new File(pathToSnapshot));

		return snapshotFileName;

	}

	/**
	 * Method to take screen shot to allure report
	 * 
	 * @return
	 */
	public byte[] embedScreenshotInReport() throws IOException {

		final byte[] screenshot = ((TakesScreenshot) chrome).getScreenshotAs(OutputType.BYTES);

		return screenshot;

	}

	@ClassRule
	public TestWatcher screenshotOnFailure = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			makeScreenshotOnFailure();
		}

		@Attachment("Screenshot on failure")
		public byte[] makeScreenshotOnFailure() {
			Log.INFO("Taking screenshot");
			return ((TakesScreenshot) chrome).getScreenshotAs(OutputType.BYTES);
		}
	};

	@After
	public void tearDown(Scenario scenario) {

		Log.INFO("Scenario: " + scenario + ", failed taking snapshot");

		if (scenario.isFailed()) {
			// Take a screenshot if for failed scenario
			byte[] screenshot = null;
			try {
				screenshot = embedScreenshotInReport();
			} catch (IOException e) {
				e.printStackTrace();
			}

			scenario.embed(screenshot, "image/png");
		}
		chrome.close();
	}

	public void attachSnapshotToReport() {

		Log.INFO("Add snapshot to report");

		Path content = null;
		String snapshotFileName = null;
		try {
			snapshotFileName = takeScreenShot();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		content = Paths.get(getSnapshotFolderPath() + "/" + snapshotFileName);
		try (InputStream is = Files.newInputStream(content)) {
			Allure.addAttachment(snapshotFileName, is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
