package com.epam.automation.tests.launches_bdd.step_definitions;

import com.epam.automation.business.cucumber.BaseStepDefinitions;
import com.epam.automation.business.models.Launch;
import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.business.service.LaunchesService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LaunchesPageSteps extends BaseStepDefinitions {

    private final LaunchesPage launchesPage;
    private final SoftAssertions softly = new SoftAssertions();

    public LaunchesPageSteps() {
        this.launchesPage = new LaunchesPage();
    }

    @After
    public void assertAllSoftAssertions() {
        softly.assertAll();
    }

    @When("User navigates to the Launches page")
    public void userNavigatesToTheLaunchesPage() {
        launchesPage.redirectToLaunchesPage();
    }

    @When("User sorts launches by name in ascending order")
    public void userSortsLaunchesByNameInAscendingOrder() {
        new LaunchesService(launchesPage).sortLaunchesByName();
    }

    @When("User selects {string} launch(es)")
    public void userSelectsLaunches(String launchIndexesStr) {
        String[] launchIndexes = launchIndexesStr.split(",");
        for (String indexStr : launchIndexes) {
            int index = Integer.parseInt(indexStr.trim());
            launchesPage.selectLaunchByIndex(index);
        }
    }

    @When("User clicks on the Compare button")
    public void userClicksOnTheButton() {
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesService.clickCompareLaunches();
    }

    @When("User clicks on the Remove button")
    public void userClicksOnTheRemoveButton() {
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesService.removeSelectedLaunch();
    }

    @When("User clicks on the first launch name")
    public void userClicksOnTheFirstLaunchName() {
        launchesPage.clickFirstLaunch();
    }

    @When("User clicks total steps for the first launch")
    public void userClicksTotalStepsForTheFirstLaunch() {
        launchesPage.clickTotalStepsForFirstLaunch();
    }

    @When("User clicks passed steps for the first launch")
    public void userClicksPassedStepsForTheFirstLaunch() {
        launchesPage.clickPassedStepsForFirstLaunch();
    }

    @Then("Launches should be sorted by most recent {string} by {int} position")
    public void launchesShouldBeSortedByMostRecent(String expectedTime, int launchIndex) {
        LaunchesService launchesService = new LaunchesService(launchesPage);
        String[][] expectedLaunchTimes = buildLaunchDataArray("", expectedTime);

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByMostRecent(expectedLaunchTimes, convertToZeroBasedIndex(launchIndex));

        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by most recent by " + launchIndex + " position")
                .isTrue();
    }

    @Then("Launches should be sorted by {string} name by {int} position")
    public void launchesShouldBeSortedByName(String expectedLaunch, int launchIndex) {
        LaunchesService launchesService = new LaunchesService(launchesPage);
        String[][] expectedLaunches = buildLaunchDataArray(expectedLaunch, "");

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByName(expectedLaunches, convertToZeroBasedIndex(launchIndex));

        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by " + launchIndex + " position")
                .isTrue();
    }

    @Then("{int} launch should contain correct totals steps: {int}")
    public void indexLaunchShouldContainCorrectTotalsStepsTotal(int launchIndex, int expectedTotalSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getTotalSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Total tests count mismatch")
                .isEqualTo(expectedTotalSteps);
    }

    @Then("{int} launch should contain correct passed steps: {int}")
    public void indexLaunchShouldContainCorrectPassedStepsPassed(int launchIndex, int expectedPassedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getPassedSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Passed tests count mismatch")
                .isEqualTo(expectedPassedSteps);
    }

    @Then("{int} launch should contain correct failed steps: {int}")
    public void indexLaunchShouldContainCorrectFailedStepsFailed(int launchIndex, int expectedFailedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getFailedSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Failed tests count mismatch")
                .isEqualTo(expectedFailedSteps);
    }

    @Then("{int} launch should contain correct skipped steps: {int}")
    public void indexLaunchShouldContainCorrectSkippedStepsSkipped(int launchIndex, int expectedSkippedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getSkippedSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Skipped tests count mismatch")
                .isEqualTo(expectedSkippedSteps);
    }

    @Then("{int} launch should contain correct product bugs: {int}")
    public void indexLaunchShouldContainCorrectProductBugsProductBugs(int launchIndex, int expectedProductBugs) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getProductBugCount())
                .as(createLaunchInfo(launchIndex, actual) + ": Product bug count mismatch")
                .isEqualTo(expectedProductBugs);
    }

    @Then("{int} launch should contain correct automation bugs: {int}")
    public void indexLaunchShouldContainCorrectAutomationBugsAutomationBugs(int launchIndex, int expectedAutomationBugs) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getAutoBugCount())
                .as(createLaunchInfo(launchIndex, actual) + ": Auto bug count mismatch")
                .isEqualTo(expectedAutomationBugs);
    }

    @Then("{int} launch should contain correct system issues: {int}")
    public void indexLaunchShouldContainCorrectSystemIssuesSystemIssues(int launchIndex, int expectedSystemIssues) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getSystemIssueCount())
                .as(createLaunchInfo(launchIndex, actual) + ": System issue count mismatch")
                .isEqualTo(expectedSystemIssues);
    }

    @Then("{int} launch should contain correct to investigate issues: {int}")
    public void indexLaunchShouldContainCorrectToInvestigateIssuesNoDefects(int launchIndex, int expectedToInvestigateIssues) {
        Launch actual = getLaunchAtIndex(launchIndex);
        softly.assertThat(actual.getToInvestigateCount())
                .as(createLaunchInfo(launchIndex, actual) + ": To investigate count mismatch")
                .isEqualTo(expectedToInvestigateIssues);
    }

    @Then("{int} launch should have total steps equal sum of passed {int}, failed {int}, and skipped {int} steps")
    public void totalStepsShouldEqualSumOfPassedFailedAndSkippedSteps(int launchIndex, int expectedPassedSteps, int expectedFailedSteps, int expectedSkippedSteps) {
        Launch actual = getLaunchAtIndex(launchIndex);
        int calculatedTotal = expectedPassedSteps + expectedFailedSteps + expectedSkippedSteps;
        softly.assertThat(actual.getTotalSteps())
                .as(createLaunchInfo(launchIndex, actual) + ": Total should equal sum of passed, failed, and skipped")
                .isEqualTo(calculatedTotal);
    }

    @Then("The following launch(es) should be selected")
    public void launchesShouldBeSelected(DataTable launchesTable) {
        String[][] expectedLaunches = convertToArray(launchesTable);
        LaunchesService launchesService = new LaunchesService(launchesPage);
        softly.assertThat(launchesService.verifySelectedLaunches(expectedLaunches))
                .as("Selected launches should match expected launches")
                .isTrue();
    }

    @Then("Compare launches modal window should display")
    public void compareLaunchesModalWindowShouldDisplay() {
        softly.assertThat(launchesPage.isCompareLaunchesModalWindowDisplayed())
                .as("Compare launches modal window should be displayed")
                .isTrue();
    }

    @Then("Delete launches modal window should display")
    public void deleteLaunchesModalWindowShouldDisplay() {
        softly.assertThat(launchesPage.isDeleteLaunchModalWindowDisplayed())
                .as("Delete launches modal window should be displayed")
                .isTrue();
    }

    @Then("List view of the launch should be opened")
    public void listViewOfTheLaunchShouldBeOpened() {
        softly.assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

    private String[][] buildLaunchDataArray(String launchName, String launchTime) {
        return new String[][]{{launchName, launchTime}};
    }

    private int convertToZeroBasedIndex(int oneBasedIndex) {
        return oneBasedIndex - 1;
    }

    private Launch getLaunchAtIndex(int oneBasedIndex) {
        List<Launch> actualLaunches = launchesPage.getLaunchesCountDataFromPage();
        return actualLaunches.get(oneBasedIndex - 1);
    }

    private String createLaunchInfo(int oneBasedIndex, Launch launch) {
        return "Launch #" + oneBasedIndex + " (" + launch.getName() + ")";
    }

    private String[][] convertToArray(DataTable launchesTable) {
        List<Map<String, String>> launches = launchesTable.asMaps(String.class, String.class);
        String[][] result = new String[launches.size()][];
        for (int i = 0; i < launches.size(); i++) {
            result[i] = launches.get(i).values().toArray(new String[0]);
        }
        return result;
    }
}
