package com.epam.automation.tests.ui.launches_bdd.step_definitions;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.ui.business.service.LaunchesService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LaunchesPageSteps {
    private final ScenarioContext scenarioContext;

    public LaunchesPageSteps(ScenarioContext context) {
        this.scenarioContext = context;
    }

    @When("User navigates to the Launches page")
    public void userNavigatesToTheLaunchesPage() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        launchesPage.redirectToLaunchesPage();
    }

    @When("User sorts launches by name in ascending order")
    public void userSortsLaunchesByNameInAscendingOrder() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        new LaunchesService(launchesPage).sortLaunchesByName();
    }

    @When("User selects the following launches")
    public void userSelectsLaunches(DataTable launchesTable) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        List<Integer> launchIndexes = launchesTable.asList(Integer.class);
        for (Integer index : launchIndexes) {
            launchesPage.selectLaunchByIndex(index);
        }
    }

    @When("User clicks on the Compare button")
    public void userClicksOnTheButton() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesService.clickCompareLaunches();
    }

    @When("User clicks on the Remove button")
    public void userClicksOnTheRemoveButton() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesService.removeSelectedLaunch();
    }

    @When("User clicks on {} for the first launch")
    public void userClicksElementForTheFirstLaunch(String element) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        switch (element.toLowerCase()) {
            case "launch name":
                launchesPage.clickFirstLaunch();
                break;
            case "total steps":
                launchesPage.clickTotalStepsForFirstLaunch();
                break;
            case "passed steps":
                launchesPage.clickPassedStepsForFirstLaunch();
                break;
            default:
                throw new IllegalArgumentException("Unknown element: " + element);
        }
    }

    @Then("Launches should be sorted by most recent with the following data")
    public void launchesShouldBeSortedByMostRecent(DataTable expectedData) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        String[][] expectedLaunchTimes = convertToArray(expectedData);

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByMostRecent(expectedLaunchTimes, 0);

        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by most recent")
                .isTrue();
    }

    @Then("Launches should be sorted by name with the following data")
    public void launchesShouldBeSortedByName(DataTable expectedData) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        String[][] expectedLaunches = convertToArray(expectedData);

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByName(expectedLaunches, 0);

        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by name")
                .isTrue();
    }

    @Then("{int} launch should contain correct totals steps: {int}")
    public void indexLaunchShouldContainCorrectTotalsStepsTotal(int launchIndex, int expectedTotalSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getTotalSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Total tests count mismatch")
                .isEqualTo(expectedTotalSteps);
    }

    @Then("{int} launch should contain correct passed steps: {int}")
    public void indexLaunchShouldContainCorrectPassedStepsPassed(int launchIndex, int expectedPassedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getPassedSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Passed tests count mismatch")
                .isEqualTo(expectedPassedSteps);
    }

    @Then("{int} launch should contain correct failed steps: {int}")
    public void indexLaunchShouldContainCorrectFailedStepsFailed(int launchIndex, int expectedFailedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getFailedSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Failed tests count mismatch")
                .isEqualTo(expectedFailedSteps);
    }

    @Then("{int} launch should contain correct skipped steps: {int}")
    public void indexLaunchShouldContainCorrectSkippedStepsSkipped(int launchIndex, int expectedSkippedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getSkippedSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Skipped tests count mismatch")
                .isEqualTo(expectedSkippedSteps);
    }

    @Then("{int} launch should contain correct product bugs: {int}")
    public void indexLaunchShouldContainCorrectProductBugsProductBugs(int launchIndex, int expectedProductBugs) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getProductBugCount())
                .as(createLaunchInfo(launchIndex, actual) + ": Product bug count mismatch")
                .isEqualTo(expectedProductBugs);
    }

    @Then("{int} launch should contain correct automation bugs: {int}")
    public void indexLaunchShouldContainCorrectAutomationBugsAutomationBugs(int launchIndex, int expectedAutomationBugs) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getAutoBugCount())
                .as(createLaunchInfo(launchIndex, actual) + ": Auto bug count mismatch")
                .isEqualTo(expectedAutomationBugs);
    }

    @Then("{int} launch should contain correct system issues: {int}")
    public void indexLaunchShouldContainCorrectSystemIssuesSystemIssues(int launchIndex, int expectedSystemIssues) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getSystemIssueCount())
                .as(createLaunchInfo(launchIndex, actual) + ": System issue count mismatch")
                .isEqualTo(expectedSystemIssues);
    }

    @Then("{int} launch should contain correct to investigate issues: {int}")
    public void indexLaunchShouldContainCorrectToInvestigateIssuesNoDefects(int launchIndex, int expectedToInvestigateIssues) {
        Launch actual = getLaunchAtIndex(launchIndex);
        assertThat(actual.getToInvestigateCount())
                .as(createLaunchInfo(launchIndex, actual) + ": To investigate count mismatch")
                .isEqualTo(expectedToInvestigateIssues);
    }

    @Then("{int} launch should have total steps equal sum of passed, failed, and skipped")
    public void totalStepsShouldEqualSumOfPassedFailedAndSkippedSteps(int launchIndex) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        Launch actual = getLaunchAtIndex(launchIndex);
        LaunchesService launchesService = new LaunchesService(launchesPage);
        assertThat(launchesService.isTotalStepsEqualToSum(actual))
                .as(createLaunchInfo(launchIndex, actual) + ": Total should equal sum of passed, failed, and skipped")
                .isTrue();
    }

    @Then("The following launch(es) should be selected")
    public void launchesShouldBeSelected(DataTable launchesTable) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        String[][] expectedLaunches = convertToArray(launchesTable);
        LaunchesService launchesService = new LaunchesService(launchesPage);
        assertThat(launchesService.verifySelectedLaunches(expectedLaunches))
                .as("Selected launches should match expected launches")
                .isTrue();
    }

    @Then("Compare launches modal window should display")
    public void compareLaunchesModalWindowShouldDisplay() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        assertThat(launchesPage.isCompareLaunchesModalWindowDisplayed())
                .as("Compare launches modal window should be displayed")
                .isTrue();
    }

    @Then("Delete launches modal window should display")
    public void deleteLaunchesModalWindowShouldDisplay() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        assertThat(launchesPage.isDeleteLaunchModalWindowDisplayed())
                .as("Delete launches modal window should be displayed")
                .isTrue();
    }

    @Then("List view of the launch should be opened")
    public void listViewOfTheLaunchShouldBeOpened() {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

    /**
     * Retrieves a Launch object at the specified 1-based index from the page.
     * Converts 1-based index to 0-based for list access.
     *
     * @param oneBasedIndex 1-based index (1 = first launch)
     * @return Launch object at the specified position
     * @throws IndexOutOfBoundsException if index is out of range
     */
    private Launch getLaunchAtIndex(int oneBasedIndex) {
        LaunchesPage launchesPage = scenarioContext.getLaunchesPage();
        List<Launch> actualLaunches = launchesPage.getLaunchesCountDataFromPage();
        return actualLaunches.get(oneBasedIndex - 1);
    }

    /**
     * Creates a formatted info string for a Launch for assertion messages.
     * Format: "Launch #[index] ([launchName])"
     * Used in assertion descriptions for better test failure reporting.
     *
     * @param oneBasedIndex 1-based launch position
     * @param launch        Launch object containing data
     * @return Formatted info string for logging/assertions
     */
    private String createLaunchInfo(int oneBasedIndex, Launch launch) {
        return "Launch #" + oneBasedIndex + " (" + launch.getName() + ")";
    }

    /**
     * Converts Cucumber DataTable to 2D String array.
     * Each row becomes one array element with column values as array elements.
     *
     * @param launchesTable DataTable from Cucumber step (header row + data rows)
     * @return 2D String array where each row represents a DataTable row
     */
    private String[][] convertToArray(DataTable launchesTable) {
        List<Map<String, String>> launches = launchesTable.asMaps(String.class, String.class);
        String[][] result = new String[launches.size()][];
        for (int i = 0; i < launches.size(); i++) {
            result[i] = launches.get(i).values().toArray(new String[0]);
        }
        return result;
    }

}
