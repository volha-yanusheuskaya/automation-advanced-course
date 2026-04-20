package com.epam.automation.tests.api.launches;

import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(0)
public class LaunchesListTest extends BaseApiTest {

    private static final String EXPECTED_LAUNCH_NAME = "Demo Api Tests";
    private static final String ERROR_NOT_FOUND      = "Not Found";

    @Test
    @DisplayName("GET /launch get all launches")
    public void getLaunchesListTest() {
        api.launches.getList()
                .statusCode(OK)
                .body("content", hasSize(greaterThan(1)))
                .body("content.name", hasItem(EXPECTED_LAUNCH_NAME));
    }

    @Test
    @DisplayName("GET /launches (wrong path) → 404 Not Found")
    public void getLaunchesListFromWrongPathTest() {
        api.launches.getListFromWrongPath()
                .statusCode(NOT_FOUND)
                .body("error", equalTo(ERROR_NOT_FOUND));
    }
}
