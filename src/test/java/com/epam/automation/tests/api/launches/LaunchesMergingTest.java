package com.epam.automation.tests.api.launches;

import com.epam.automation.api.business.model.domain.MergeLaunch;
import com.epam.automation.api.business.model.dto.PostLaunchMergeRequestDto;
import com.epam.automation.api.business.model.managers.MergeLaunchManager;
import com.epam.automation.api.business.model.mapper.MergeLaunchesMapper;
import com.epam.automation.tests.api.BaseApiTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(5)
public class LaunchesMergingTest extends BaseApiTest {

    private static final String FIRST_LAUNCH_NAME = "Demo Api Tests - Merge 1";
    private static final String FIRST_LAUNCH_START_TIME = "2026-03-31T16:48:04Z";
    private static final String FIRST_LAUNCH_END_TIME = "2026-03-31T16:48:08Z";
    private static final String FIRST_ITEM_NAME = "Test Case 1";
    private static final String FIRST_ITEM_START_TIME = "2026-03-31T16:48:05Z";
    private static final String FIRST_ITEM_END_TIME = "2026-03-31T16:48:06Z";

    private static final String SECOND_LAUNCH_NAME = "Demo Api Tests - Merge 2";
    private static final String SECOND_LAUNCH_START_TIME = "2026-03-31T17:48:04Z";
    private static final String SECOND_LAUNCH_END_TIME = "2026-03-31T17:48:08Z";
    private static final String SECOND_ITEM_NAME = "Test Case 2";
    private static final String SECOND_ITEM_START_TIME = "2026-03-31T17:48:05Z";
    private static final String SECOND_ITEM_END_TIME = "2026-03-31T17:48:06Z";

    private record CreatedLaunch(String uuid, int id) {}

    private CreatedLaunch first;
    private CreatedLaunch second;
    private Integer mergedLaunchId;

    @BeforeEach
    void createSourceLaunches() {
        first  = createFinishedLaunch(FIRST_LAUNCH_NAME,  FIRST_LAUNCH_START_TIME,  FIRST_LAUNCH_END_TIME,
                FIRST_ITEM_NAME,   FIRST_ITEM_START_TIME,    FIRST_ITEM_END_TIME);
        second = createFinishedLaunch(SECOND_LAUNCH_NAME, SECOND_LAUNCH_START_TIME, SECOND_LAUNCH_END_TIME,
                SECOND_ITEM_NAME,  SECOND_ITEM_START_TIME,   SECOND_ITEM_END_TIME);
    }

    @AfterEach
    void cleanup() {
        deleteLaunch(mergedLaunchId);
        if (first  != null) deleteLaunch(first.id());
        if (second != null) deleteLaunch(second.id());
    }

    @Test
    @DisplayName("POST /launch/merge to merge launches")
    public void launchesMergingTest() {
        PostLaunchMergeRequestDto dto = buildMergeDto();

        ExtractableResponse<Response> extract = api.launches.merge(dto)
                .statusCode(OK)
                .extract();

        String mergedUuid = extract.path("uuid");
        mergedLaunchId  = extract.path("id");

        api.launches.getList()
                .statusCode(OK)
                .statusCode(OK)
                .body("content.uuid", not(hasItem(first.uuid())))
                .body("content.uuid", not(hasItem(second.uuid())))
                .body("content.uuid", hasItem(mergedUuid))
                .body(String.format("content.find { it.uuid == '%s' }.name", mergedUuid),
                        equalTo(FIRST_LAUNCH_NAME));
    }

    private CreatedLaunch createFinishedLaunch(String launchName, String launchStart, String launchEnd,
                                               String itemName,   String itemStart,   String itemEnd) {
        String uuid = startLaunch(launchName, launchStart);
        addFinishedItem(itemName, itemStart, itemEnd, uuid);
        finishLaunch(launchEnd, uuid);
        return new CreatedLaunch(uuid, resolveLaunchId(uuid));
    }

    private PostLaunchMergeRequestDto buildMergeDto() {
        MergeLaunch launch = MergeLaunchManager.getMergeLaunch(
                FIRST_LAUNCH_NAME, FIRST_LAUNCH_START_TIME, SECOND_LAUNCH_END_TIME,
                first.id(), second.id());
        return MergeLaunchesMapper.map(launch, PostLaunchMergeRequestDto.class);
    }
}
