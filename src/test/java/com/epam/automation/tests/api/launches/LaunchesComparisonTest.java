package com.epam.automation.tests.api.launches;

import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(3)
public class LaunchesComparisonTest extends BaseApiTest {

    private static final int FIRST_LAUNCH_ID_TO_COMPARE = 9751086;
    private static final int SECOND_LAUNCH_ID_TO_COMPARE = 9751087;

    @Test
    @DisplayName("GET /launch/compare to compare launches")
    public void launchesComparisonTest() {
        api.launches.compare(FIRST_LAUNCH_ID_TO_COMPARE, SECOND_LAUNCH_ID_TO_COMPARE)
                .statusCode(OK)
                .body("result[0].id", equalTo(FIRST_LAUNCH_ID_TO_COMPARE))
                .body("result[1].id", equalTo(SECOND_LAUNCH_ID_TO_COMPARE));
    }
}
