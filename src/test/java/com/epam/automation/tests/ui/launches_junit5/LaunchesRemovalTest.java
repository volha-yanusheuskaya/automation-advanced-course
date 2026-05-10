package com.epam.automation.tests.ui.launches_junit5;

import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.ui.business.service.LaunchesService;
import com.epam.automation.tests.ui.launches_junit5.base.JunitUiTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launch removal test suite")
@Order(5)
class LaunchesRemovalTest extends JunitUiTestBase {

    @Test
    @DisplayName("Verify that the launch can be removed")
    void shouldVerifyLaunchRemoval() {
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
