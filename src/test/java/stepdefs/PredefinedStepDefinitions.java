package stepdefs;

import java.io.IOException;

import com.qa.tlv.environment.BaseTest;
import com.qa.tlv.methods.TestCaseFailed;

import cucumber.api.java.en.Then;

public class PredefinedStepDefinitions implements BaseTest {

	// Step to navigate to specified URL
	@Then("^User navigate to (.*)$")
	public void navigate_to(String link) throws IOException {
		browserObj.navigateTo(link);

	}

	// Step to navigate forward
	@Then("^User navigate forward")
	public void navigate_forward() {
		browserObj.navigate("forward");
	}

	// Step to navigate backward
	@Then("^User navigate back")
	public void navigate_back() {
		browserObj.navigate("back");
	}

	// steps to refresh page
	@Then("^User refresh page$")
	public void refresh_page() {
		browserObj.refresh();
	}

	// To interact with browser

	// Step to close the browser
	@Then("^User close browser$")
	public void close_browser() throws IOException {
		browserObj.closeDriver();

	}

	// zoom in/out page

	// Assertion steps
	@Then("^User should see page title as \"(.*)\"$")
	public void check_title(String title) throws TestCaseFailed {
		browserObj.checkTitle(title);
	}

	// step to check element partial text
	@Then("^User should\\s*((?:not)?)\\s+see page title having partial text as \"(.*?)\"$")
	public void check_partial_text(String present, String partialTextTitle) throws TestCaseFailed {
		browserObj.checkPartialTitle(partialTextTitle);
	}

	// Progress methods

	// wait for specific period of time
	@Then("^User wait for (\\d+) sec$")
	public void wait(String time) throws NumberFormatException, InterruptedException {
		browserObj.wait(time);
	}

	// JavaScript handling steps

	// Step to handle java script
	@Then("^User accept alert$")
	public void handle_alert() {
		browserObj.handleAlert("accept");
	}

	// Steps to dismiss java script
	@Then("^User dismiss alert$")
	public void dismiss_alert() {
		browserObj.handleAlert("dismiss");
	}

	// Screen shot methods

	@Then("^User take screenshot$")
	public void take_screenshot() throws IOException {
		browserObj.takeScreenShot();
	}

	// Configuration steps

	// step to print configuration
	@Then("^User print configuration$")
	public void print_config() {
		browserObj.printDesktopConfiguration();
	}
}
