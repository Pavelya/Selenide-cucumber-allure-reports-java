@functional_tests_test 
@pack_selenide 
Feature: Test page login 
	As a user I should able to login to selenium test page
 
Scenario: User login with valid credential 
	Given User navigate to http://the-internet.herokuapp.com/login 
	And user enter username 
	Then User should see page title as "AAA" 
	
Scenario: User login with valid credential II
	Given User navigate to http://the-internet.herokuapp.com/login 
	And user enter username 
	Then User should see page title as "BBB"