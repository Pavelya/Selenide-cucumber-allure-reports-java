@search_form
Feature: Verify main search form founctionality

  As an Internet user
  I want to be able to navigate to main travelpayouts page
  So that I can use search form

  Scenario: Search form content
    Given user opens travelpayouts page
    And search form title is displayed with valid content
    