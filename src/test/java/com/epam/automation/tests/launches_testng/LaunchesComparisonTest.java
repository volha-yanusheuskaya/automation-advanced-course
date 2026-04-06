package com.epam.automation.tests.launches_testng;

import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.tests.launches_testng.base.BaseTest;
import com.epam.automation.tests.launches_testng.data_provider.LaunchTestDataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LaunchesComparisonTest extends BaseTest {

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "twoLaunchesComparisonData",
            priority = 4,
            description = "Verify that two launches can be compared"
    )
    public void verifyTwoLaunchesComparison(String[][] expectedLaunchesToCompare) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();
        
        verifyLaunchesComparison(launchesPage, launchesService, expectedLaunchesToCompare, buildLaunchIndexes(2));
    }

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "threeLaunchesComparisonData",
            priority = 4,
            description = "Verify that three launches can be compared"
    )
    public void verifyThreeLaunchesComparison(String[][] expectedLaunchesToCompare) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        verifyLaunchesComparison(launchesPage, launchesService, expectedLaunchesToCompare, buildLaunchIndexes(3));
    }

    /**
     * Helper method to verify launches comparison functionality.
     * Only handles launch selection and comparison verification.
     *
     * @param launchesPage the launches page object
     * @param launchesService the launches service object
     * @param expectedLaunchesToCompare the expected launches to compare
     * @param launchIndexes the indices of launches to select for comparison
     */
    private void verifyLaunchesComparison(LaunchesPage launchesPage, LaunchesService launchesService, 
                                         String[][] expectedLaunchesToCompare, int[] launchIndexes) {
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

    /**
     * Creates a range of launch indices for comparison operations.
     *
     * @param size the number of sequential indices to generate
     * @return an array of sequential integers from 1 to size (inclusive)
     */
    private static int[] buildLaunchIndexes(int size) {
        int[] indexes = new int[size];
        for (int i = 0; i < size; i++) {
            indexes[i] = i + 1;
        }
        return indexes;
    }

}
