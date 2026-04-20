package com.epam.automation.tests.api.launches;

import com.epam.automation.api.business.model.domain.Launch;
import com.epam.automation.api.business.model.dto.PostLaunchRequestDto;
import com.epam.automation.api.business.model.managers.LaunchManager;
import com.epam.automation.api.business.model.mapper.LaunchesMapper;
import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(4)
public class LaunchForceFinishTest extends BaseApiTest {

    private static final String LAUNCH_NAME = "Demo Api Tests - In progress";
    private static final String LAUNCH_START_TIME = "2026-03-31T16:48:04Z";
    private static final String LAUNCH_END_TIME = "2026-03-31T16:48:08Z";

    private String launchUuid;

    @BeforeEach
    void createInProgressLaunch() {
        launchUuid = startLaunch(LAUNCH_NAME, LAUNCH_START_TIME);
        launchId = resolveLaunchId(launchUuid);
    }

    @Test
    @DisplayName("PUT /launch/{launchId}/finish to force finish launch")
    public void forceFinishLaunchByIdTest() {
        Launch launch = LaunchManager.getLaunchByEndTime(LAUNCH_END_TIME);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch, PostLaunchRequestDto.class);

        api.launches.stop(launchId, dto)
                .statusCode(OK);

        api.launches.getList()
                .statusCode(OK)
                .body("content.find { it.id == %d }.status", withArgs(launchId), equalTo("STOPPED"))
                .body("content.find { it.id == %d }.endTime", withArgs(launchId), notNullValue());
    }

    @Test
    @DisplayName("PUT /launch/{launchId}/stop returns 404 for a non-existent launch")
    void forceFinishNonExistentLaunchTest() {
        int invalidLaunchId = 0;

        Launch launch = LaunchManager.getLaunchByEndTime(LAUNCH_END_TIME);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch, PostLaunchRequestDto.class);

        api.launches.stop(invalidLaunchId, dto)
                .statusCode(NOT_FOUND)
                .body("message", equalTo(
                        "Launch '" + invalidLaunchId + "' not found. Did you use correct Launch ID?"));

        api.launches.finish(launchUuid, dto)
                .statusCode(OK);
    }

    @Test
    @DisplayName("PUT /launch/{launchId}/stop returns 400 when the launch is already stopped")
    void forceFinishAlreadyStoppedLaunchTest() {
        Launch launch = LaunchManager.getLaunchByEndTime(LAUNCH_END_TIME);
        PostLaunchRequestDto dto = LaunchesMapper.map(launch, PostLaunchRequestDto.class);

        api.launches.stop(launchId, dto).statusCode(OK);

        api.launches.stop(launchId, dto)
                .statusCode(NOT_ACCEPTABLE)
                .body("message", equalTo("Finish launch is not allowed. Launch '" + launchId + "' already finished with status 'STOPPED'"));
    }

}
