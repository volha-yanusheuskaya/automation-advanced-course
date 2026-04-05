package com.epam.automation.tests.launches_junit5;

import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.tests.launches_junit5.base.BaseTest;
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
public class LaunchesComparisonTest extends BaseTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("com.epam.automation.tests.launches_junit5.data_provider.LaunchTestDataProvider#provideTwoLaunchesComparisonData")
    @DisplayName("Verify that two launches can be compared")
    public void verifyTwoLaunchesComparison(String datasetName, String[][] expectedLaunchesToCompare, int[] launchIndexes) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        verifyComparison(launchesPage, launchesService, expectedLaunchesToCompare, launchIndexes);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("com.epam.automation.tests.launches_junit5.data_provider.LaunchTestDataProvider#provideThreeLaunchesComparisonData")
    @DisplayName("Verify that three launches can be compared")
    public void verifyThreeLaunchesComparison(String datasetName, String[][] expectedLaunchesToCompare, int[] launchIndexes) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        verifyComparison(launchesPage, launchesService, expectedLaunchesToCompare, launchIndexes);
    }

    private void verifyComparison(LaunchesPage launchesPage, LaunchesService launchesService, String[][] expectedLaunchesToCompare, int[] launchIndices) {
        for (int index : launchIndices) {
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
