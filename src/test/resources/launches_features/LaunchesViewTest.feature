@ui_bdd
Feature: Launches view display feature
  The purpose of this feature is to test the display of the Launches view in the Report Portal application.

  Background: Navigate to Launches page
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario Outline: Verify that the launch view can be opened by clicking <element>
    When User clicks on <element> for the first launch
    Then List view of the launch should be opened
    Examples:
      | element      |
      | launch name  |
      | total steps  |
      | passed steps |