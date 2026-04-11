package com.epam.automation.business.service;

import com.epam.automation.business.models.Launch;
import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.epam.automation.core.utils.WaitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for interacting with Launches functionality in Report Portal.
 * <p>
 * Provides business logic for verifying launch list display, sorting operations,
 * and data validation. Works in conjunction with {@link LaunchesPage} to interact
 * with the launches page UI.
 *
 * <p>Primary responsibilities:
 * <ul>
 *   <li>Verify launches are sorted correctly (by name, date, defects, etc.)</li>
 *   <li>Validate launches list display and count</li>
 *   <li>Perform sorting operations via column headers</li>
 *   <li>Extract and validate launch data</li>
 * </ul>
 */
public class LaunchesService {
    private static final ILogger logger = LoggerFactory.getLogger(LaunchesService.class);
    private final LaunchesPage launchesPage;

    /**
     * Constructs a LaunchesService with the provided LaunchesPage instance.
     *
     * @param launchesPage the page object for launches page interactions
     * @throws NullPointerException if launchesPage is null
     */
    public LaunchesService(LaunchesPage launchesPage) {
        this.launchesPage = launchesPage;
    }

    /**
     * Verifies if launches are sorted by most recent (descending by date).
     *
     * <p>Compares actual start times from the page with expected times.
     * When testing with individual data sets, verifies that expected launches appear
     * in the actual list in the correct sorted order.
     *
     * @param expectedSortedLaunches expected launches sorted by date descending
     * @param launchIndex index of the launch to verify in the sorted list
     * @return true if actual sort order matches expected, false otherwise
     */
    public boolean isLaunchesListSortedByMostRecent(String[][] expectedSortedLaunches, int launchIndex) {
        if (launchesPage.totalLaunchesEmpty()) {
            logger.warn("Launches list is empty");
            return false;
        }

        if (!launchesPage.isLaunchesListDisplayed()) {
            logger.error("Launches list is not displayed");
            return false;
        }

        List<String> actualStartTimes = launchesPage.getAllStartTimes();
        String expectedTime = expectedSortedLaunches[0][1];
        String actualTime = actualStartTimes.get(launchIndex);

        logger.info("Expected start time: [{}]", expectedTime);
        logger.info("Actual start time: [{}]", actualTime);

        return actualTime.equals(expectedTime);
    }

    /**
     * Verifies if launches are sorted alphabetically by name.
     *
     * <p>When testing with individual data sets, verifies that the expected launch
     * is present in the list.
     *
     * @param expectedSortedLaunches expected launches sorted alphabetically by name
     * @param launchIndex index of the launch to verify in the sorted list
     * @return true if actual sort order matches expected, false otherwise
     */
    public boolean isLaunchesListSortedByName(String[][] expectedSortedLaunches,  int launchIndex) {
        if (launchesPage.totalLaunchesEmpty()) {
            logger.warn("Launches list is empty");
            return false;
        }

        if (!launchesPage.isLaunchesListDisplayed()) {
            logger.error("Launches list is not displayed");
            return false;
        }

        List<String> actualLaunchesNames = launchesPage.getAllLaunchesNames();
        String expectedLaunchName = expectedSortedLaunches[0][0];
        String actualLaunchName = actualLaunchesNames.get(launchIndex);

        logger.info("Expected launch name: [{}]", expectedLaunchName);
        logger.info("Actual launch name: [{}]", actualLaunchName);

        return actualLaunchName.equals(expectedLaunchName);
    }

    /**
     * Clicks the Name column header to sort launches, then waits for the list to update.
     */
    public void sortLaunchesByName() {
        String oldFirstLaunchName = launchesPage.getTotalLaunches().getFirst().getText();
        launchesPage.clickNameColumnHeaderToSort();
        WaitUtil.waitForElementTextToChange(launchesPage.getTotalLaunches().getFirst(), oldFirstLaunchName);
    }


    /**
     * Validates that the total steps equals the sum of passed, failed, and skipped.
     *
     * @param launch Launch object to validate
     * @return true if total = passed + failed + skipped
     */
    public boolean isTotalStepsEqualToSum(Launch launch) {
        int calculatedTotal = launch.getPassedSteps() + launch.getFailedSteps() + launch.getSkippedSteps();
        return launch.getTotalSteps() == calculatedTotal;
    }

    public boolean verifySelectedLaunches(String[][] expectedSelectedLaunches) {
        List<String> actualSelectedLaunchesList = launchesPage.getSelectedLaunches();

        List<String> expectedLaunchNames = new ArrayList<>();
        for (String[] launch : expectedSelectedLaunches) {
            expectedLaunchNames.add(launch[0]);
        }

        logger.info("Expected selected launches: {}", expectedLaunchNames);
        logger.info("Actual selected launches: {}", actualSelectedLaunchesList);

        return actualSelectedLaunchesList.equals(expectedLaunchNames);
    }

    public void clickCompareLaunches() {
        launchesPage.clickActionsButton();
        launchesPage.clickCompareButton();
    }

    public void removeSelectedLaunch() {
        launchesPage.clickActionsButton();
        launchesPage.clickDeleteButton();
    }

}
