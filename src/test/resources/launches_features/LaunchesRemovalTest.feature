Feature: Launch removal feature
  The purpose of this feature is to test the launch removal functionality in the Report Portal application.

  Background: Login and navigate to Launches page
    Given User login with default credentials
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario: Verify that a launch can be removed
    When User selects "1" launch
    Then The following launch should be selected
      | launch            |
      | Demo Api Tests #5 |
    When User clicks on the Remove button
    Then Delete launches modal window should display