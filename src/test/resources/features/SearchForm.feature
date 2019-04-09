@search_form 
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
	And user clicks on search submit button
	Then search results are displayed