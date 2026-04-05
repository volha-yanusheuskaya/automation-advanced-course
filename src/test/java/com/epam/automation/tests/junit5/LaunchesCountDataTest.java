package com.epam.automation.tests.junit5;

import com.epam.automation.business.models.Launch;
import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.tests.data.LaunchTestData;
import com.epam.automation.tests.junit5.base.BaseTest;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches count data display test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(3)
public class LaunchesCountDataTest extends BaseTest {

    @Test
    @DisplayName("Verify that each launch contains tests count data")
    public void verifyLaunchesCountData() {
        loginWithDefaultCredentials();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();
        launchesService.sortLaunchesByName();

        List<Launch> expectedLaunches = LaunchTestData.getLaunches();
        List<Launch> actualLaunches = launchesPage.getLaunchesCountDataFromPage();

        assertThat(actualLaunches.size()).as("Number of launches on page does not match expected count)")
                .isEqualTo(expectedLaunches.size());

        for (int i = 0; i < expectedLaunches.size(); i++) {
            Launch expected = expectedLaunches.get(i);
            Launch actual = actualLaunches.get(i);
            String launchIndex = "Launch #" + (i + 1) + " (" + actual.getName() + ")";

            assertThat(actual.getTotalSteps()).as(launchIndex + ": Total tests count mismatch")
                    .isEqualTo(expected.getTotalSteps());
            assertThat(actual.getPassedSteps()).as(launchIndex + ": Passed tests count mismatch")
                    .isEqualTo(expected.getPassedSteps());
            assertThat(actual.getFailedSteps()).as(launchIndex + ": Failed tests count mismatch")
                    .isEqualTo(expected.getFailedSteps());
            assertThat(actual.getSkippedSteps()).as(launchIndex + ": Skipped tests count mismatch")
                    .isEqualTo(expected.getSkippedSteps());

            assertThat(actual.getProductBugCount()).as(launchIndex + ": Product bug count mismatch")
                    .isEqualTo(expected.getProductBugCount());
            assertThat(actual.getAutoBugCount()).as(launchIndex + ": Auto bug count mismatch")
                    .isEqualTo(expected.getAutoBugCount());
            assertThat(actual.getSystemIssueCount()).as(launchIndex + ": System issue count mismatch")
                    .isEqualTo(expected.getSystemIssueCount());
            assertThat(actual.getToInvestigateCount()).as(launchIndex + ": To investigate count mismatch")
                    .isEqualTo(expected.getToInvestigateCount());

            int calculatedTotal = actual.getPassedSteps() + actual.getFailedSteps() + actual.getSkippedSteps();
            assertThat(actual.getTotalSteps()).as(launchIndex + ": Total should equal sum of passed, failed, and skipped")
                    .isEqualTo(calculatedTotal);
        }

    }
}
