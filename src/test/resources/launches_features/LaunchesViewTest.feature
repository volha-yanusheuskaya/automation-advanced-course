Feature: Launches view display feature
  The purpose of this feature is to test the display of the Launches view in the Report Portal application.

  Background: Login and navigate to Launches page
    Given User login with default credentials
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario: Verify that the launch view can be opened by click launch name
    When User clicks on the first launch name
    Then List view of the launch should be opened

  Scenario: Verify that the launch view can be opened by click total steps
    When User clicks total steps for the first launch
    Then List view of the launch should be opened

  Scenario: Verify that the launch view can be opened by click passed steps
    When User clicks passed steps for the first launch
    Then List view of the launch should be opened