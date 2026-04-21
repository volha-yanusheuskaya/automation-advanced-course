package com.epam.automation.tests.api.launches;

import com.epam.automation.api.business.model.domain.Launch;
import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.model.managers.LaunchManager;
import com.epam.automation.api.business.model.mapper.LaunchesMapper;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

public class LaunchForceFinishTest extends BaseApiTest {

    private static final ILogger logger = LoggerFactory.getLogger(LaunchForceFinishTest.class);

    private static final String LAUNCH_NAME = "Demo Api Tests - In progress";
    private static final String LAUNCH_START_TIME = "2026-03-31T16:48:04Z";
    private static final String LAUNCH_END_TIME = "2026-03-31T16:48:08Z";

    private String launchUuid;
    private Integer launchId;
    private boolean launchIsRunning;

    @BeforeEach
    void createInProgressLaunch() {
        launchUuid = startLaunch(LAUNCH_NAME, LAUNCH_START_TIME);
        launchId = resolveLaunchId(launchUuid);
        launchIsRunning = true;
    }

    @AfterEach
    void cleanup() {
        if (launchUuid != null && launchIsRunning) {
            Launch launch = LaunchManager.getLaunchByEndTime(LAUNCH_END_TIME);
            try {
                api.launches.finish(launchUuid, LaunchesMapper.map(launch));
            } catch (Exception e) {
                logger.warn("Finishing failed for launch id={} : {}", launchId, e);
            }
        }
        deleteLaunch(launchId);
    }

    @Test
    @DisplayName("PUT /launch/{launchId}/finish to force finish launch")
    public void forceFinishLaunchByIdTest() {
        api.launches.stop(launchId, finishPayload())
                .statusCode(OK);

        launchIsRunning = false;

        api.launches.getList()
                .statusCode(OK)
                .body("content.find { it.id == %d }.status", withArgs(launchId), equalTo("STOPPED"))
                .body("content.find { it.id == %d }.endTime", withArgs(launchId), notNullValue());
    }

    @Test
    @DisplayName("PUT /launch/{launchId}/stop returns 404 for a invalid launch id")
    void forceFinishInvalidLaunchTest() {
        int invalidLaunchId = Integer.MAX_VALUE;

        api.launches.stop(invalidLaunchId, finishPayload())
                .statusCode(NOT_FOUND)
                .body("message", equalTo(
                        "Launch '" + invalidLaunchId + "' not found. Did you use correct Launch ID?"));
    }

    @Test
    @DisplayName("PUT /launch/{launchId}/stop returns 400 when the launch is already stopped")
    void forceFinishAlreadyStoppedLaunchTest() {
        api.launches.stop(launchId, finishPayload()).statusCode(OK);

        api.launches.stop(launchId, finishPayload())
                .statusCode(NOT_ACCEPTABLE)
                .body("message", equalTo("Finish launch is not allowed. Launch '" + launchId + "' already finished with status 'STOPPED'"));
    }

    private PostLaunchRequestDto finishPayload() {
        Launch launch = LaunchManager.getLaunchByEndTime(LAUNCH_END_TIME);
        return LaunchesMapper.map(launch);
    }

}
