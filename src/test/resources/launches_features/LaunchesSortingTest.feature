Feature: Launches sorting feature
  The purpose of this feature is to test the sorting functionality of the launches in the Report Portal application.

  Background: Login and navigate to the Launches page
    Given User login with default credentials
    When User navigates to the Launches page

  Scenario Outline: Verify launches sorted by most recent (by default)
    Then Launches should be sorted by most recent <launch_time> by <index> position
    Examples:
      | index | launch_time           |
      | 1     | "2026-03-31 19:47:04" |
      | 2     | "2026-03-31 19:47:00" |
      | 3     | "2026-03-31 19:46:57" |
      | 4     | "2026-03-31 19:46:54" |
      | 5     | "2026-03-31 19:46:51" |

  Scenario Outline: Verify launches sorted by name
    When User sorts launches by name in ascending order
    Then Launches should be sorted by <launch_name> name by <index> position
    Examples:
      | index | launch_name         |
      | 1     | "Demo Api Tests #1" |
      | 2     | "Demo Api Tests #2" |
      | 3     | "Demo Api Tests #3" |
      | 4     | "Demo Api Tests #4" |
      | 5     | "Demo Api Tests #5" |