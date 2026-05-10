package com.epam.automation.tests.ui.launches_junit5;

import com.epam.automation.ui.business.pages.LaunchesPage;
import com.epam.automation.tests.ui.launches_junit5.base.JunitUiTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Launches view display test suite")
@Order(6)
class LaunchesViewTest extends JunitUiTestBase {

    @Test
    @DisplayName("Verify that the launch view can be opened by click launch name")
    void shouldOpenLaunchViewWhenClickingLaunchName() {
        LaunchesPage launchesPage = new LaunchesPage();

        loginWithDefaultCredentials().closeToastComponent();

        launchesPage.redirectToLaunchesPage();
        launchesPage.clickFirstLaunch();

        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

    @Test
    @DisplayName("Verify that the launch view can be opened by click total steps")
    void shouldOpenLaunchViewWhenClickingTotalSteps() {
        LaunchesPage launchesPage = new LaunchesPage();

        loginWithDefaultCredentials().closeToastComponent();

        launchesPage.redirectToLaunchesPage();
        launchesPage.clickTotalStepsForFirstLaunch();

        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

    @Test
    @DisplayName("Verify that the launch view can be opened by click passed steps")
    void shouldOpenLaunchViewWhenClickingPassedSteps() {
        LaunchesPage launchesPage = new LaunchesPage();

        loginWithDefaultCredentials().closeToastComponent();

        launchesPage.redirectToLaunchesPage();
        launchesPage.clickPassedStepsForFirstLaunch();

        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }
}
