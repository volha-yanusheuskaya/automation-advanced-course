package com.epam.automation.tests.ui.launches_testng;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.tests.ui.launches_testng.base.TestNgUiTestBase;
import com.epam.automation.tests.ui.launches_testng.data_provider.LaunchTestDataProvider;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

public class LaunchesCountDataTest extends TestNgUiTestBase {

    @Test(
            dataProviderClass = LaunchTestDataProvider.class,
            dataProvider = "launchCountData",
            priority = 3,
            description = "Verify that each launch contains correct test count data"
    )
    public void shouldVerifyLaunchesCountData(Launch expectedLaunch, int launchIndex) {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();
        launchesService.sortLaunchesByName();

        List<Launch> actualLaunches = launchesPage.getLaunchesCountDataFromPage();
        Launch actual = actualLaunches.get(launchIndex);
        String launchInfo = "Launch #" + (launchIndex + 1) + " (" + actual.getName() + ")";

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(actual.getTotalSteps())
                .as(launchInfo + ": Total tests count mismatch")
                .isEqualTo(expectedLaunch.getTotalSteps());

        softly.assertThat(actual.getPassedSteps())
                .as(launchInfo + ": Passed tests count mismatch")
                .isEqualTo(expectedLaunch.getPassedSteps());

        softly.assertThat(actual.getFailedSteps())
                .as(launchInfo + ": Failed tests count mismatch")
                .isEqualTo(expectedLaunch.getFailedSteps());

        softly.assertThat(actual.getSkippedSteps())
                .as(launchInfo + ": Skipped tests count mismatch")
                .isEqualTo(expectedLaunch.getSkippedSteps());

        softly.assertThat(actual.getProductBugCount())
                .as(launchInfo + ": Product bug count mismatch")
                .isEqualTo(expectedLaunch.getProductBugCount());

        softly.assertThat(actual.getAutoBugCount())
                .as(launchInfo + ": Auto bug count mismatch")
                .isEqualTo(expectedLaunch.getAutoBugCount());

        softly.assertThat(actual.getSystemIssueCount())
                .as(launchInfo + ": System issue count mismatch")
                .isEqualTo(expectedLaunch.getSystemIssueCount());

        softly.assertThat(actual.getToInvestigateCount())
                .as(launchInfo + ": To investigate count mismatch")
                .isEqualTo(expectedLaunch.getToInvestigateCount());

        int calculatedTotal = actual.getPassedSteps() + actual.getFailedSteps() + actual.getSkippedSteps();
        softly.assertThat(actual.getTotalSteps())
                .as(launchInfo + ": Total should equal sum of passed, failed, and skipped")
                .isEqualTo(calculatedTotal);

        softly.assertAll();

    }
}
