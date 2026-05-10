package com.epam.automation.tests.ui.launches_junit5;

import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.tests.ui.launches_junit5.base.JunitUiTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches comparison test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(4)
class LaunchesComparisonTest extends JunitUiTestBase {

    @ParameterizedTest(name = "{0}")
    @MethodSource("com.epam.automation.tests.ui.launches_junit5.data_provider.LaunchTestDataProvider#provideTwoLaunchesComparisonData")
    @DisplayName("Verify that two launches can be compared")
    void shouldVerifyTwoLaunchesComparison(String datasetName, String[][] expectedLaunchesToCompare, int[] launchIndexes) {
        assertThat(launchIndexes).as("Expected 2 launches to compare").hasSize(2);

        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        verifyComparison(launchesPage, launchesService, expectedLaunchesToCompare, launchIndexes);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("com.epam.automation.tests.ui.launches_junit5.data_provider.LaunchTestDataProvider#provideThreeLaunchesComparisonData")
    @DisplayName("Verify that three launches can be compared")
    void shouldVerifyThreeLaunchesComparison(String datasetName, String[][] expectedLaunchesToCompare, int[] launchIndexes) {
        assertThat(launchIndexes).as("Expected 3 launches to compare").hasSize(3);

        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        verifyComparison(launchesPage, launchesService, expectedLaunchesToCompare, launchIndexes);
    }

    private void verifyComparison(LaunchesPage launchesPage, LaunchesService launchesService, String[][] expectedLaunchesToCompare, int[] launchIndexes) {
        for (int index : launchIndexes) {
            launchesPage.selectLaunchByIndex(index);
        }
        assertThat(launchesService.verifySelectedLaunches(expectedLaunchesToCompare))
                .as("Selected launches should match expected launches")
                .isTrue();

        launchesService.clickCompareLaunches();
        assertThat(launchesPage.isCompareLaunchesModalWindowDisplayed())
                .as("Compare launches modal window should be displayed")
                .isTrue();
    }

}
