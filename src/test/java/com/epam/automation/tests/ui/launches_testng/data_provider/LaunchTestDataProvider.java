package com.epam.automation.tests.ui.launches_testng.data_provider;

import com.epam.automation.tests.ui.data.LaunchTestData;
import org.testng.annotations.DataProvider;

public class LaunchTestDataProvider {

    @DataProvider(name = "launchDataByName")
    public Object[][] provideLaunchDataByName() {
        return wrapWithIndexes(
                LaunchTestData.getFirstLaunchByName(),
                LaunchTestData.getSecondLaunchByName(),
                LaunchTestData.getThirdLaunchByName(),
                LaunchTestData.getFourthLaunchByName(),
                LaunchTestData.getFifthLaunchByName()
        );
    }

    @DataProvider(name = "launchDataByDefault")
    public Object[][] provideDefaultLaunchData() {
        return wrapWithIndexes(
                LaunchTestData.getFirstSortedLaunchByMostRecent(),
                LaunchTestData.getSecondSortedLaunchByMostRecent(),
                LaunchTestData.getThirdSortedLaunchByMostRecent(),
                LaunchTestData.getFourthSortedLaunchByMostRecent(),
                LaunchTestData.getFifthSortedLaunchByMostRecent()
        );
    }

    @DataProvider(name = "launchCountData")
    public Object[][] provideLaunchCountData() {
        return wrapWithIndexes(
                LaunchTestData.getFirstLaunch(),
                LaunchTestData.getSecondLaunch(),
                LaunchTestData.getThirdLaunch(),
                LaunchTestData.getFourthLaunch(),
                LaunchTestData.getFifthLaunch()
        );
    }

    @DataProvider(name = "twoLaunchesComparisonData")
    public Object[][] provideTwoLaunchesComparisonData() {
        return new Object[][]{
                {new String[][]{
                        LaunchTestData.getFifthLaunchByName()[0],
                        LaunchTestData.getFourthLaunchByName()[0]
                }}
        };
    }

    @DataProvider(name = "threeLaunchesComparisonData")
    public Object[][] provideThreeLaunchesComparisonData() {
        return new Object[][]{
                {new String[][]{
                        LaunchTestData.getFifthLaunchByName()[0],
                        LaunchTestData.getFourthLaunchByName()[0],
                        LaunchTestData.getThirdLaunchByName()[0]
                }}
        };
    }

    /**
     * Wraps an array of launch data objects with their corresponding indices.
     * <p>
     * This helper method takes variable number of launch data arrays and combines each
     * with an auto-generated index (starting from 0), creating a 2D array suitable for
     * TestNG data providers. This eliminates the need for hardcoded indices throughout
     * the data provider methods.
     *
     * @param launches variable number of Object arrays, each containing launch test data.
     *                 Each array is expected to represent a single launch's data.
     * @return a 2D Object array where each row contains two elements:
     * [0] - the original launch data array
     * [1] - the auto-generated index (Integer) for that launch
     * // Result: {{launchData1, 0}, {launchData2, 1}, {launchData3, 2}}
     */
    private Object[][] wrapWithIndexes(Object... launches) {
        Object[][] result = new Object[launches.length][2];
        for (int i = 0; i < launches.length; i++) {
            result[i] = new Object[]{launches[i], i};
        }
        return result;
    }

}
