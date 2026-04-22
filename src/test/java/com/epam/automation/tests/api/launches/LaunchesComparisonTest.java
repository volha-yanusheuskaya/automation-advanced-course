package com.epam.automation.tests.api.launches;

import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

public class LaunchesComparisonTest extends BaseApiTest {

    private static final String FIRST_LAUNCH_NAME = "Demo Api Tests - Compare 1";
    private static final String FIRST_LAUNCH_START_TIME = "2026-03-31T16:48:04Z";
    private static final String FIRST_LAUNCH_END_TIME = "2026-03-31T16:48:08Z";
    private static final String FIRST_ITEM_NAME = "Test Case 1";
    private static final String FIRST_ITEM_START_TIME = "2026-03-31T16:48:05Z";
    private static final String FIRST_ITEM_END_TIME = "2026-03-31T16:48:06Z";

    private static final String SECOND_LAUNCH_NAME = "Demo Api Tests - Compare 2";
    private static final String SECOND_LAUNCH_START_TIME = "2026-03-31T17:48:04Z";
    private static final String SECOND_LAUNCH_END_TIME = "2026-03-31T17:48:08Z";
    private static final String SECOND_ITEM_NAME = "Test Case 2";
    private static final String SECOND_ITEM_START_TIME = "2026-03-31T17:48:05Z";
    private static final String SECOND_ITEM_END_TIME = "2026-03-31T17:48:06Z";

    private Integer firstLaunchId;
    private Integer secondLaunchId;

    @BeforeEach
    void createLaunchesUnderComparison() {
        firstLaunchId = createFinishedLaunch(FIRST_LAUNCH_NAME, FIRST_LAUNCH_START_TIME, FIRST_LAUNCH_END_TIME,
                FIRST_ITEM_NAME, FIRST_ITEM_START_TIME, FIRST_ITEM_END_TIME);
        secondLaunchId = createFinishedLaunch(SECOND_LAUNCH_NAME, SECOND_LAUNCH_START_TIME, SECOND_LAUNCH_END_TIME,
                SECOND_ITEM_NAME, SECOND_ITEM_START_TIME, SECOND_ITEM_END_TIME);
    }

    @AfterEach
    void cleanupComparedLaunches() {
        deleteLaunch(firstLaunchId);
        deleteLaunch(secondLaunchId);
    }

    @Test
    @DisplayName("GET /launch/compare – returns comparison results for two launches")
    public void launchesComparisonTest() {
        api.launches.compare(firstLaunchId, secondLaunchId)
                .statusCode(OK)
                .body("result", hasSize(2))
                .body("result.id", containsInAnyOrder(firstLaunchId, secondLaunchId));
    }

    @Test
    @DisplayName("GET /launch/compare – ignores invalid ids")
    public void launchesComparisonWithInvalidIdTest() {
        int invalidLaunchId = Integer.MAX_VALUE;

        api.launches.compare(firstLaunchId, invalidLaunchId)
                .statusCode(OK)
                .body("result", hasSize(1))
                .body("result[0].id", equalTo(firstLaunchId));
    }

    private int createFinishedLaunch(String launchName, String launchStart, String launchEnd,
                                     String itemName, String itemStart, String itemEnd) {
        String uuid = startLaunch(launchName, launchStart);
        addFinishedItem(itemName, itemStart, itemEnd, uuid);
        finishLaunch(launchEnd, uuid);
        return resolveLaunchId(uuid);
    }
}
