Feature: Launch removal feature
  The purpose of this feature is to test the launch removal functionality in the Report Portal application.

  Background: Navigate to Launches page
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario: Verify that a launch can be removed
    When User selects the following launches
      | 1 |
    Then The following launch should be selected
      | launch            |
      | Demo Api Tests #5 |
    When User clicks on the Remove button
    Then Delete launches modal window should display
