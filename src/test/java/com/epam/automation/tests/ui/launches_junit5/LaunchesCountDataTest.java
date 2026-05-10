package com.epam.automation.tests.ui.launches_junit5;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.tests.ui.launches_junit5.base.JunitUiTestBase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches count data display test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(3)
class LaunchesCountDataTest extends JunitUiTestBase {

    @ParameterizedTest(name = "Launch #{index} - {0}")
    @MethodSource("com.epam.automation.tests.ui.launches_junit5.data_provider.LaunchTestDataProvider#provideLaunchTestDataSets")
    @DisplayName("Verify that each launch contains correct test count data")
    void shouldVerifyLaunchesCountData(String datasetName, Launch expectedLaunch, int launchIndex) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        launchesService.sortLaunchesByName();

        List<Launch> actualLaunches = launchesPage.getLaunchesCountDataFromPage();
        Launch actual = actualLaunches.get(launchIndex);
        String launchInfo = "Launch #" + (launchIndex + 1) + " (" + actual.getName() + ")";

        assertThat(actual.getTotalSteps())
                .as(launchInfo + ": Total tests count mismatch")
                .isEqualTo(expectedLaunch.getTotalSteps());

        assertThat(actual.getPassedSteps())
                .as(launchInfo + ": Passed tests count mismatch")
                .isEqualTo(expectedLaunch.getPassedSteps());
        assertThat(actual.getFailedSteps())
                .as(launchInfo + ": Failed tests count mismatch")
                .isEqualTo(expectedLaunch.getFailedSteps());
        assertThat(actual.getSkippedSteps())
                .as(launchInfo + ": Skipped tests count mismatch")
                .isEqualTo(expectedLaunch.getSkippedSteps());

        assertThat(actual.getProductBugCount())
                .as(launchInfo + ": Product bug count mismatch")
                .isEqualTo(expectedLaunch.getProductBugCount());
        assertThat(actual.getAutoBugCount())
                .as(launchInfo + ": Auto bug count mismatch")
                .isEqualTo(expectedLaunch.getAutoBugCount());
        assertThat(actual.getSystemIssueCount())
                .as(launchInfo + ": System issue count mismatch")
                .isEqualTo(expectedLaunch.getSystemIssueCount());
        assertThat(actual.getToInvestigateCount())
                .as(launchInfo + ": To investigate count mismatch")
                .isEqualTo(expectedLaunch.getToInvestigateCount());

        int calculatedTotal = actual.getPassedSteps() + actual.getFailedSteps() + actual.getSkippedSteps();
        assertThat(actual.getTotalSteps())
                .as(launchInfo + ": Total should equal sum of passed, failed, and skipped")
                .isEqualTo(calculatedTotal);
    }

}
