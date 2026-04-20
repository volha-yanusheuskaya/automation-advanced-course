package com.epam.automation.tests.ui.launches_junit5.data_provider;

import com.epam.automation.tests.ui.data.LaunchTestData;
import org.junit.jupiter.params.provider.Arguments;

public class LaunchTestDataProvider {

    static Arguments[] provideLaunchesDataSetsByDefault() {
        return wrapWithIndexes(
                "Launch 1 (Most Recent)", LaunchTestData.getFirstSortedLaunchByMostRecent(),
                "Launch 2 (Most Recent)", LaunchTestData.getSecondSortedLaunchByMostRecent(),
                "Launch 3 (Most Recent)", LaunchTestData.getThirdSortedLaunchByMostRecent(),
                "Launch 4 (Most Recent)", LaunchTestData.getFourthSortedLaunchByMostRecent(),
                "Launch 5 (Most Recent)", LaunchTestData.getFifthSortedLaunchByMostRecent()
        );
    }

    static Arguments[] provideLaunchesDataSetsByName() {
        return wrapWithIndexes(
                "Launch 1 (By Name)", LaunchTestData.getFirstLaunchByName(),
                "Launch 2 (By Name)", LaunchTestData.getSecondLaunchByName(),
                "Launch 3 (By Name)", LaunchTestData.getThirdLaunchByName(),
                "Launch 4 (By Name)", LaunchTestData.getFourthLaunchByName(),
                "Launch 5 (By Name)", LaunchTestData.getFifthLaunchByName()
        );
    }

    static Arguments[] provideLaunchTestDataSets() {
        return wrapWithIndexes(
                "First launch test count data", LaunchTestData.getFirstLaunch(),
                "Second launch test count data", LaunchTestData.getSecondLaunch(),
                "Third launch test count data", LaunchTestData.getThirdLaunch(),
                "Fourth launch test count data", LaunchTestData.getFourthLaunch(),
                "Fifth launch test count data", LaunchTestData.getFifthLaunch()
        );
    }

    static Arguments[] provideTwoLaunchesComparisonData() {
        return new Arguments[]{
                Arguments.of("Two launches comparison",
                        combineLaunches(LaunchTestData.getFifthLaunchByName(),
                                LaunchTestData.getFourthLaunchByName()),
                        new int[]{1, 2})
        };
    }

    static Arguments[] provideThreeLaunchesComparisonData() {
        return new Arguments[]{
                Arguments.of("Three launches comparison",
                        combineLaunches(LaunchTestData.getFifthLaunchByName(),
                                LaunchTestData.getFourthLaunchByName(),
                                LaunchTestData.getThirdLaunchByName()),
                        new int[]{1, 2, 3})
        };
    }

    /**
     * Wraps launch data with auto-generated indices into Arguments for JUnit5.
     * Eliminates hardcoded index values and ensures consistency across data sets.
     *
     * @param items variable number of arguments alternating between String display names and String[][] data
     * @return array of Arguments with auto-generated indices
     */
    private static Arguments[] wrapWithIndexes(Object... items) {
        int datasetCount = items.length / 2;
        Arguments[] arguments = new Arguments[datasetCount];

        for (int i = 0; i < datasetCount; i++) {
            String displayName = (String) items[i * 2];
            Object launchData = items[i * 2 + 1];
            arguments[i] = Arguments.of(displayName, launchData, i);
        }

        return arguments;
    }

    /**
     * Combines multiple launch data arrays into a single 2D array.
     * Extracts the first row from each launch dataset.
     *
     * @param launchDataSets variable number of 2D string arrays
     * @return combined 2D string array with launch data
     */
    private static String[][] combineLaunches(String[][]... launchDataSets) {
        String[][] result = new String[launchDataSets.length][10];
        for (int i = 0; i < launchDataSets.length; i++) {
            result[i] = launchDataSets[i][0];
        }
        return result;
    }

}
