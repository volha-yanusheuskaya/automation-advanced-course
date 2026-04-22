@ui_bdd
Feature: Launches count data display feature
  The purpose of this feature is to test the display of launches count data in the Report Portal application.

  Background: Navigate to the Launches page
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario Outline: Verify that each launch contains correct test count data
    When User sorts launches by name in ascending order
    Then <index> launch should contain correct totals steps: <total>
    And <index> launch should contain correct passed steps: <passed>
    And <index> launch should contain correct failed steps: <failed>
    And <index> launch should contain correct skipped steps: <skipped>
    And <index> launch should contain correct product bugs: <productBugs>
    And <index> launch should contain correct automation bugs: <automationBugs>
    And <index> launch should contain correct system issues: <systemIssues>
    And <index> launch should contain correct to investigate issues: <noDefects>
    And <index> launch should have total steps equal sum of passed, failed, and skipped

    Examples:
      | index | total | passed | failed | skipped | productBugs | automationBugs | systemIssues | noDefects |
      | 1     | 10    | 1      | 9      | 0       | 0           | 1              | 10           | 2         |
      | 2     | 15    | 5      | 9      | 1       | 1           | 5              | 6            | 4         |
      | 3     | 20    | 10     | 8      | 2       | 4           | 4              | 1            | 7         |
      | 4     | 25    | 20     | 5      | 0       | 4           | 1              | 0            | 1         |
      | 5     | 30    | 30     | 0      | 0       | 0           | 0              | 0            | 0         |