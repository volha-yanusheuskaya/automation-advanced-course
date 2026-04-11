package com.epam.automation.tests.launches_junit5;

import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.tests.launches_junit5.base.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches sorting test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
public class LaunchesSortingTest extends BaseTest {

    @ParameterizedTest(name = "Dataset {index}: {0}")
    @MethodSource("com.epam.automation.tests.launches_junit5.data_provider.LaunchTestDataProvider#provideLaunchesDataSetsByDefault")
    @DisplayName("Verify launches sorted by most recent")
    public void shouldVerifyLaunchesSortedByMostRecentWithDataSets(String datasetName, String[][] expectedLaunches, int launchIndex) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByMostRecent(expectedLaunches,  launchIndex);

        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by most recent (" + datasetName + ")")
                .isTrue();
    }

    @ParameterizedTest(name = "Dataset {index}: {0}")
    @MethodSource("com.epam.automation.tests.launches_junit5.data_provider.LaunchTestDataProvider#provideLaunchesDataSetsByName")
    @DisplayName("Verify launches sorted by name")
    public void shouldVerifyLaunchesSortedByNameWithDataSets(String datasetName, String[][] expectedLaunches, int launchIndex) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        launchesService.sortLaunchesByName();

        boolean isLaunchesListSorted = launchesService.isLaunchesListSortedByName(expectedLaunches,   launchIndex);

        assertThat(isLaunchesListSorted)
                .as("Launches should be sorted by name (" + datasetName + ")")
                .isTrue();
    }
}
