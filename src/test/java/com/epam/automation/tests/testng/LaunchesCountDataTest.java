package com.epam.automation.tests.testng;

import com.epam.automation.business.models.Launch;
import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.tests.data.LaunchTestData;
import com.epam.automation.tests.testng.base.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class LaunchesCountDataTest extends BaseTest {

    @Test(priority = 1, description = "Verify that each launch contains tests count data")
    public void verifyLaunchesCountData() {
        loginWithDefaultCredentials();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();
        launchesService.sortLaunchesByName();

        List<Launch> expectedLaunches = LaunchTestData.getLaunches();
        List<Launch> actualLaunches = launchesPage.getLaunchesCountDataFromPage();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualLaunches.size(), expectedLaunches.size(),
                "Number of launches on page does not match expected count");

        for (int i = 0; i < expectedLaunches.size(); i++) {
            Launch expected = expectedLaunches.get(i);
            Launch actual = actualLaunches.get(i);
            String launchIndex = "Launch #" + (i + 1) + " (" + actual.getName() + ")";

            softAssert.assertEquals(actual.getTotalSteps(), expected.getTotalSteps(),
                    launchIndex + ": Total tests count mismatch");
            softAssert.assertEquals(actual.getPassedSteps(), expected.getPassedSteps(),
                    launchIndex + ": Passed tests count mismatch");
            softAssert.assertEquals(actual.getFailedSteps(), expected.getFailedSteps(),
                    launchIndex + ": Failed tests count mismatch");
            softAssert.assertEquals(actual.getSkippedSteps(), expected.getSkippedSteps(),
                    launchIndex + ": Skipped tests count mismatch");

            softAssert.assertEquals(actual.getProductBugCount(), expected.getProductBugCount(),
                    launchIndex + ": Product bug count mismatch");
            softAssert.assertEquals(actual.getAutoBugCount(), expected.getAutoBugCount(),
                    launchIndex + ": Auto bug count mismatch");
            softAssert.assertEquals(actual.getSystemIssueCount(), expected.getSystemIssueCount(),
                    launchIndex + ": System issue count mismatch");
            softAssert.assertEquals(actual.getToInvestigateCount(), expected.getToInvestigateCount(),
                    launchIndex + ": To investigate count mismatch");

            int calculatedTotal = actual.getPassedSteps() + actual.getFailedSteps() + actual.getSkippedSteps();
            softAssert.assertEquals(actual.getTotalSteps(), calculatedTotal,
                    launchIndex + ": Total should equal sum of passed, failed, and skipped");
        }

        softAssert.assertAll();

    }

}
