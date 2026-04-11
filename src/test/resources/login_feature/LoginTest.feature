Feature: Login test feature
  The purpose of this feature is to test the login functionality of the Report Portal application.

  Scenario: Successful login when valid credentials are provided
    Then Toast component should be displayed with message "Signed in successfully"
    And Dashboard page should be displayed with correct title
    And Dashboard page should be displayed with correct URL

  Scenario: Dashboard page is displayed after successful login
    Then All Dashboards heading should be displayed on the Dashboard page
    And User should switch to the Demo Dashboard dashboard
