package com.epam.automation.tests.testng;

import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.tests.data.LaunchTestDataProvider;
import com.epam.automation.tests.testng.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LaunchesSortingTest extends BaseTest {

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "launchDataByDefault",
            priority = 1,
            description = "Verify that launches are sorted by most recent by default"
    )
    public void verifyLaunchesAreSortedByMostRecent(String[][] expectedSortedLaunches) {
        loginWithDefaultCredentials();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByMostRecent(expectedSortedLaunches);

        assertTrue(isLaunchesListSorted, "Start times should be sorted in descending order (most recent first)");
    }

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "launchDataByName",
            priority = 2,
            description = "Verify that launches can be are sorted by name"
    )
    public void verifyLaunchesAreSortedByName(String[][] expectedSortedLaunches) {
        loginWithDefaultCredentials();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();
        launchesService.sortLaunchesByName();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByName(expectedSortedLaunches);

        assertTrue(isLaunchesListSorted, "Launches should be sorted by name in ascending order");
    }
}
