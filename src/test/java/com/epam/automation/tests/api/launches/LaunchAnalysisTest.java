package com.epam.automation.tests.api.launches;

import com.epam.automation.api.business.model.domain.AnalyzeLaunch;
import com.epam.automation.api.business.model.dto.PostLaunchAnalyzeRequestDto;
import com.epam.automation.api.business.model.managers.AnalyzeLaunchManager;
import com.epam.automation.api.business.model.mapper.AnalyzeLaunchesMapper;
import com.epam.automation.tests.api.BaseApiTest;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
public class LaunchAnalysisTest extends BaseApiTest {

    private static final String LAUNCH_NAME = "Demo Api Tests - Analyze";
    private static final String LAUNCH_START_TIME = "2026-03-31T16:48:04Z";
    private static final String LAUNCH_END_TIME = "2026-03-31T16:48:08Z";

    private static final String ITEM_NAME = "Failing case";
    private static final String ITEM_START_TIME = "2026-03-31T16:48:05Z";
    private static final String ITEM_END_TIME = "2026-03-31T16:48:06Z";


    @BeforeEach
    void createLaunchWithFinishedItem() {
        String launchUuid = startLaunch(LAUNCH_NAME, LAUNCH_START_TIME);
        addFinishedItem(ITEM_NAME, ITEM_START_TIME, ITEM_END_TIME, launchUuid);
        finishLaunch(LAUNCH_END_TIME, launchUuid);
        launchId = resolveLaunchId(launchUuid);
    }

    @Test
    @DisplayName("POST /launch/analyze to start launch analysis")
    public void launchAnalysisTest() {
        AnalyzeLaunch launch = AnalyzeLaunchManager.getAnalyzeLaunchById(launchId);
        PostLaunchAnalyzeRequestDto dto = AnalyzeLaunchesMapper.map(launch, PostLaunchAnalyzeRequestDto.class);

        api.launches.analyze(dto)
                .statusCode(OK)
                .body("message", equalTo(
                        "autoAnalyzer analysis for launch with ID='" + launchId + "' started."));
    }

    @Test
    @DisplayName("POST /launch/analyze with non-existent launch ID returns error")
    public void launchAnalysisWithInvalidIdTest() {
        int invalidLaunchId = 999999;

        AnalyzeLaunch launch = AnalyzeLaunchManager.getAnalyzeLaunchById(invalidLaunchId);
        PostLaunchAnalyzeRequestDto dto = AnalyzeLaunchesMapper.map(launch, PostLaunchAnalyzeRequestDto.class);

        api.launches.analyze(dto)
                .statusCode(NOT_FOUND)
                .body("message", equalTo(
                        "Launch '" + invalidLaunchId + "' not found. Did you use correct Launch ID?"));
    }

    @Test
    @DisplayName("POST /launch/analyze returns 400 for an unknown analyzer type")
    void analyzeWithInvalidAnalyzerTypeTest() {
        AnalyzeLaunch launch = AnalyzeLaunchManager.getAnalyzeLaunchById(launchId);
        launch.setAnalyzerTypeName("invalid-analyzer");
        PostLaunchAnalyzeRequestDto dto = AnalyzeLaunchesMapper.map(launch, PostLaunchAnalyzeRequestDto.class);

        api.launches.analyze(dto)
                .statusCode(BAD_REQUEST)
                .body("message", equalTo("Incorrect Request. [Value is not allowed for field 'analyzerTypeName'.] "));
    }

}
