package com.epam.automation.tests.api.launches;

import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

class LaunchRemovalTest extends BaseApiTest {

    private static final String LAUNCH_NAME = "Demo Api Tests - To remove";
    private static final String LAUNCH_START_TIME = "2026-03-31T16:48:04Z";
    private static final String LAUNCH_END_TIME = "2026-03-31T16:48:08Z";

    private static final String ITEM_NAME = "Test Case 1";
    private static final String ITEM_START_TIME = "2026-03-31T16:48:05Z";
    private static final String ITEM_END_TIME = "2026-03-31T16:48:06Z";

    private String launchUuid;
    private Integer launchId;

    @BeforeEach
    void createLaunchWithFinishedItem() {
        launchUuid = startLaunch(LAUNCH_NAME, LAUNCH_START_TIME);
        addFinishedItem(ITEM_NAME, ITEM_START_TIME, ITEM_END_TIME, launchUuid);
        finishLaunch(LAUNCH_END_TIME, launchUuid);
        launchId = resolveLaunchId(launchUuid);
    }

    @AfterEach
    void cleanup() {
        deleteLaunch(launchId);
    }

    @Test
    @DisplayName("DELETE /launch/{id} – deletes the launch")
    void deleteLaunchByIdTest() {
        api.launches.delete(launchId)
                .statusCode(OK);

        api.launches.getList()
                .statusCode(OK)
                .body("content.uuid", not(hasItem(launchUuid)));
    }

    @Test
    @DisplayName("DELETE /launch/{id} – returns 404 for an invalid launch")
    void deleteInvalidLaunchTest() {
        int nonExistentLaunchId = Integer.MAX_VALUE;

        api.launches.delete(nonExistentLaunchId)
                .statusCode(NOT_FOUND)
                .body("message", equalTo(
                        "Launch '" + nonExistentLaunchId + "' not found. Did you use correct Launch ID?"));
    }

}
