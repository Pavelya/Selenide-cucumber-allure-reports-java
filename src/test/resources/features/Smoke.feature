@smoke_tests
Feature: Verify main search form founctionality 

	As an Internet user
  I want to be able to navigate to main travelpayouts page
  So that I can use search form

Background: 
	Given user opens travelpayouts page 
	
Scenario: Search form content validation 
	And search form title is displayed with valid content 
	Then user settings dropdown is displayed 
	And switcher between flighs and hotels is displayed 
	And search hotels form is displayed with valid content 
	
Scenario: Search for hotel 
	And user search for Moscow city in search hotels form 
	And user selects valid checkin date value 
	And user selects valid checkout date value 
	And user update number of guests 
	And user clicks on search submit button 
	Then search results are displayed 
	When user is clicked on Book button for the first sugested hotel 
	Then the user is redirected to external provider 
	
Scenario: Search for flight 
	And user switches to flight search 
	And user selects Leeds as ofigin 
	And user selects Moscow as destination 
	And user selects valid departure date value 
	And user selects valid return date value 
	And user update number of passengers 
	And user clicks on search submit button 
	And search results are displayed 
	When user is clicked on Book button for the first sugested flight 
	Then the user is redirected to external KIWI provider 
	
	
Scenario: Change currency 
	And user changes the currency value to EUR 
	Then page is reloaded and new currency value is set 
	
Scenario: Change language 
	And user changes the languge value to Russian 
	Then page is reloaded and new language value is set 
	
Scenario: Footer validation 
	Then footer is displayed with valid content 
	
Scenario: Header validation 
	Then header is displayed with valid content