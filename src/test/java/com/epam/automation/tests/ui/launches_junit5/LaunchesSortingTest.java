package com.epam.automation.tests.ui.launches_junit5;

import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.tests.ui.launches_junit5.base.JunitUiTestBase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches sorting test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
class LaunchesSortingTest extends JunitUiTestBase {

    @ParameterizedTest(name = "Dataset {index}: {0}")
    @MethodSource("com.epam.automation.tests.ui.launches_junit5.data_provider.LaunchTestDataProvider#provideLaunchesDataSetsByDefault")
    @DisplayName("Verify launches sorted by most recent")
    void shouldVerifyLaunchesSortedByMostRecentWithDataSets(String datasetName, String[][] expectedLaunches, int launchIndex) {
        assertThat(expectedLaunches).as("Expected launches data must not be empty").isNotEmpty();
        assertThat(launchIndex).as("Launch index must be non-negative").isGreaterThanOrEqualTo(0);

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
    @MethodSource("com.epam.automation.tests.ui.launches_junit5.data_provider.LaunchTestDataProvider#provideLaunchesDataSetsByName")
    @DisplayName("Verify launches sorted by name")
    void shouldVerifyLaunchesSortedByNameWithDataSets(String datasetName, String[][] expectedLaunches, int launchIndex) {
        assertThat(expectedLaunches).as("Expected launches data must not be empty").isNotEmpty();
        assertThat(launchIndex).as("Launch index must be non-negative").isGreaterThanOrEqualTo(0);

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
