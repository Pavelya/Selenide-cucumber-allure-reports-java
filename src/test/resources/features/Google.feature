@google
Feature: Verify google page functionality

  As an Internet user
  I want to be able to navigate to google
  So that I can search in google page

  Scenario: User make search in google
    Given User opens google
    When User search for "Pavelya git"
    And User clicks on search result
    Then User redirected to the expected url


    