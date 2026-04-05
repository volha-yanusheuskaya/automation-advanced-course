package com.epam.automation.tests.data;

import com.epam.automation.business.models.Launch;
import com.epam.automation.business.builder.LaunchBuilder;

import java.util.ArrayList;
import java.util.List;

public class LaunchTestData {

    private static final Object[][] BASE_LAUNCH_DATA = {
            {"Demo Api Tests #1", "2026-03-31 16:46:51", 10, 1, 9, 0, 0, 1, 10, 2},
            {"Demo Api Tests #2", "2026-03-31 16:46:54", 15, 5, 9, 1, 1, 5, 6, 4},
            {"Demo Api Tests #3", "2026-03-31 16:46:57", 20, 10, 8, 2, 4, 4, 1, 7},
            {"Demo Api Tests #4", "2026-03-31 16:47:00", 25, 20, 5, 0, 4, 1, 0, 1},
            {"Demo Api Tests #5", "2026-03-31 16:47:04", 30, 30, 0, 0, 0, 0, 0, 0},
    };

    public static String[][] getLaunchesSortedByMostRecent() {
        return reverseOrder(toStringArray(BASE_LAUNCH_DATA));
    }

    public static String[][] getLaunchesSortedByName() {
        return toStringArray(BASE_LAUNCH_DATA.clone());
    }

    public static List<Launch> getLaunches() {
        List<Launch> launches = new ArrayList<>();
        for (Object[] data : BASE_LAUNCH_DATA) {
            Launch launch = new LaunchBuilder()
                    .name((String) data[0])
                    .date((String) data[1])
                    .totalSteps((int) data[2])
                    .passedSteps((int) data[3])
                    .failedSteps((int) data[4])
                    .skippedSteps((int) data[5])
                    .productBugCount((int) data[6])
                    .autoBugCount((int) data[7])
                    .systemIssueCount((int) data[8])
                    .toInvestigateCount((int) data[9])
                    .build();
            launches.add(launch);
        }
        return launches;
    }

    private static String[][] toStringArray(Object[][] data) {
        String[][] result = new String[data.length][2];
        for (int i = 0; i < data.length; i++) {
            result[i][0] = (String) data[i][0];
            result[i][1] = (String) data[i][1];
        }
        return result;
    }

    private static String[][] reverseOrder(String[][] data) {
        String[][] reversed = new String[data.length][2];
        for (int i = 0; i < data.length; i++) {
            reversed[i] = data[data.length - 1 - i];
        }
        return reversed;
    }
}
