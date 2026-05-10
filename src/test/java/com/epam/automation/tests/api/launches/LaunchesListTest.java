package com.epam.automation.tests.api.launches;

import com.epam.automation.tests.api.launches.request.GetLaunchesListWrongPathRequest;
import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

class LaunchesListTest extends BaseApiTest {

    private static final String EXPECTED_LAUNCH_NAME = "Demo Api Tests";
    private static final String ERROR_NOT_FOUND      = "Not Found";

    @Test
    @DisplayName("GET /launch – returns the list of launches")
    void getLaunchesListTest() {
        api.launches.getList()
                .statusCode(OK)
                .body("content", not(empty()))
                .body("content.name", hasItem(EXPECTED_LAUNCH_NAME));
    }

    @Test
    @DisplayName("GET /launches – returns 404 for a wrong path")
    void getLaunchesListFromWrongPathTest() {
        api.execute(new GetLaunchesListWrongPathRequest())
                .statusCode(NOT_FOUND)
                .body("error", equalTo(ERROR_NOT_FOUND));
    }
}
