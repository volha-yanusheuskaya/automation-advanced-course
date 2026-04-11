package com.epam.automation.tests.launches_testng;

import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.business.service.LaunchesService;
import com.epam.automation.tests.launches_testng.base.BaseTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LaunchesRemovalTest extends BaseTest {

    @Test(priority = 5, description = "Verify that the launch can be removed")
    public void shouldVerifyLaunchRemoval() {
        loginWithDefaultCredentials().closeToastComponent();

        LaunchesPage launchesPage = new LaunchesPage();
        LaunchesService launchesService = new LaunchesService(launchesPage);
        launchesPage.redirectToLaunchesPage();

        launchesPage.selectLaunchByIndex(1);
        launchesService.removeSelectedLaunch();

        assertThat(launchesPage.isDeleteLaunchModalWindowDisplayed())
                .as("Delete launch modal window should be displayed")
                .isTrue();
    }

}
