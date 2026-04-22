package com.epam.automation.tests.ui.data;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.common.core.data_reader.JsonDataReader;
import java.util.List;

public class LaunchTestData {
    private static final String JSON_FILE_PATH = "src/test/resources/test_data/launches.json";
    private static final JsonDataReader reader = new JsonDataReader();

    public static String[][] getFirstLaunchByName() {
        return getLaunchByNameFromKey("launches1");
    }

    public static String[][] getSecondLaunchByName() {
        return getLaunchByNameFromKey("launches2");
    }

    public static String[][] getThirdLaunchByName() {
        return getLaunchByNameFromKey("launches3");
    }

    public static String[][] getFourthLaunchByName() {
        return getLaunchByNameFromKey("launches4");
    }

    public static String[][] getFifthLaunchByName() {
        return getLaunchByNameFromKey("launches5");
    }

    public static String[][] getFirstSortedLaunchByMostRecent() {
        return getLaunchByNameFromKey("sortedByMostRecent1");
    }

    public static String[][] getSecondSortedLaunchByMostRecent() {
        return getLaunchByNameFromKey("sortedByMostRecent2");
    }

    public static String[][] getThirdSortedLaunchByMostRecent() {
        return getLaunchByNameFromKey("sortedByMostRecent3");
    }

    public static String[][] getFourthSortedLaunchByMostRecent() {
        return getLaunchByNameFromKey("sortedByMostRecent4");
    }

    public static String[][] getFifthSortedLaunchByMostRecent() {
        return getLaunchByNameFromKey("sortedByMostRecent5");
    }

    public static Launch getFirstLaunch() {
        return getLaunchFromKey("launches1");
    }

    public static Launch getSecondLaunch() {
        return getLaunchFromKey("launches2");
    }

    public static Launch getThirdLaunch() {
        return getLaunchFromKey("launches3");
    }

    public static Launch getFourthLaunch() {
        return getLaunchFromKey("launches4");
    }

    public static Launch getFifthLaunch() {
        return getLaunchFromKey("launches5");
    }

    /**
     * Retrieves launch data as a 2D string array from the JSON file.
     *
     * @param key the JSON key to retrieve data for
     * @return a 2D string array containing launch information
     */
    private static String[][] getLaunchByNameFromKey(String key) {
        return convertToStringArray(reader.readDataByKey(JSON_FILE_PATH, key));
    }

    /**
     * Retrieves the first launch object from the JSON file for a given key.
     *
     * @param key the JSON key to retrieve data for
     * @return the first Launch object, or null if no launches found
     */
    private static Launch getLaunchFromKey(String key) {
        List<Launch> launches = reader.readDataByKey(JSON_FILE_PATH, key);
        return launches.getFirst();
    }

    /**
     * Converts a list of Launch objects into a 2D string array.
     *
     * @param launches the list of Launch objects to convert
     * @return a 2D string array with launch details
     */
    private static String[][] convertToStringArray(List<Launch> launches) {
        String[][] result = new String[launches.size()][10];
        for (int i = 0; i < launches.size(); i++) {
            Launch l = launches.get(i);
            result[i][0] = l.getName();
            result[i][1] = l.getDate();
            result[i][2] = String.valueOf(l.getTotalSteps());
            result[i][3] = String.valueOf(l.getPassedSteps());
            result[i][4] = String.valueOf(l.getFailedSteps());
            result[i][5] = String.valueOf(l.getSkippedSteps());
            result[i][6] = String.valueOf(l.getProductBugCount());
            result[i][7] = String.valueOf(l.getAutoBugCount());
            result[i][8] = String.valueOf(l.getSystemIssueCount());
            result[i][9] = String.valueOf(l.getToInvestigateCount());
        }
        return result;
    }
}
