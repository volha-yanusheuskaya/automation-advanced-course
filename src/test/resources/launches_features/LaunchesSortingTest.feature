@ui_bdd
Feature: Launches sorting feature
  The purpose of this feature is to test the sorting functionality of the launches in the Report Portal application.

  Background: Navigate to the Launches page
    When User closes the Toast component
    And User navigates to the Launches page

  Scenario: Verify launches sorted by most recent (by default)
    Then Launches should be sorted by most recent with the following data
      | launchName        | launchTime          |
      | Demo Api Tests #5 | 2026-03-31 16:47:04 |
      | Demo Api Tests #4 | 2026-03-31 16:47:00 |
      | Demo Api Tests #3 | 2026-03-31 16:46:57 |
      | Demo Api Tests #2 | 2026-03-31 16:46:54 |
      | Demo Api Tests #1 | 2026-03-31 16:46:51 |

  Scenario: Verify launches sorted by name
    When User sorts launches by name in ascending order
    Then Launches should be sorted by name with the following data
      | launchName        |
      | Demo Api Tests #1 |
      | Demo Api Tests #2 |
      | Demo Api Tests #3 |
      | Demo Api Tests #4 |
      | Demo Api Tests #5 |