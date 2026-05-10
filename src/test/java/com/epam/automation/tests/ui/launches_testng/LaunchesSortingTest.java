package com.epam.automation.tests.ui.launches_testng;

import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.tests.ui.launches_testng.data_provider.LaunchTestDataProvider;
import com.epam.automation.tests.ui.launches_testng.base.TestNgUiTestBase;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LaunchesSortingTest extends TestNgUiTestBase {

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "launchDataByDefault",
            priority = 2,
            description = "Verify that launches are sorted by most recent by default"
    )
    public void shouldVerifyLaunchesAreSortedByMostRecent(String[][] expectedSortedLaunches, int launchIndex) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByMostRecent(expectedSortedLaunches, launchIndex);
        assertThat(isLaunchesListSorted)
                .as("Start times should be sorted in descending order (most recent first)")
                .isTrue();
    }

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "launchDataByName",
            priority = 2,
            description = "Verify that launches can be are sorted by name"
    )
    public void shouldVerifyLaunchesAreSortedByName(String[][] expectedSortedLaunches, int launchIndex) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        launchesService.sortLaunchesByName();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByName(expectedSortedLaunches,  launchIndex);
        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by name in ascending order")
                .isTrue();
    }
}
