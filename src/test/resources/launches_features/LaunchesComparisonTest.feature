@ui_bdd
Feature: Launches comparison feature
  The purpose of this feature is to test the launches comparison functionality in the Report Portal application.

  Background: Navigate to the Launches page
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario: Verify that two launches can be compared
    When User selects the following launches
      | 1 |
      | 2 |
    Then The following launches should be selected
      | launch            |
      | Demo Api Tests #5 |
      | Demo Api Tests #4 |
    When User clicks on the Compare button
    Then Compare launches modal window should display

  Scenario: Verify that three launches can be compared
    When User selects the following launches
      | 1 |
      | 2 |
      | 3 |
    Then The following launches should be selected
      | launch            |
      | Demo Api Tests #5 |
      | Demo Api Tests #4 |
      | Demo Api Tests #3 |
    When User clicks on the Compare button
    Then Compare launches modal window should display