package com.epam.automation.tests.junit5;

import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.tests.data.LaunchTestData;
import com.epam.automation.tests.junit5.base.BaseTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches sorting test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
public class LaunchesSortingTest extends BaseTest {

    @Test
    @DisplayName("Verify that launches are sorted by most recent by default")
    public void verifyLaunchesAreSortedByMostRecent() {
        String[][] expectedSortedLaunches = LaunchTestData.getLaunchesSortedByMostRecent();

        loginWithDefaultCredentials();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByMostRecent(expectedSortedLaunches);

        assertThat(isLaunchesListSorted).as("Start times should be sorted in descending order (most recent first)").isTrue();
    }

    @Test
    @DisplayName("Verify that launches can be are sorted by name")
    public void verifyLaunchesAreSortedByName() {
        String[][] expectedSortedLaunches = LaunchTestData.getLaunchesSortedByName();

        loginWithDefaultCredentials();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();
        launchesService.sortLaunchesByName();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByName(expectedSortedLaunches);

        assertThat(isLaunchesListSorted).as("Launches should be sorted by name in ascending order").isTrue();
    }
}
