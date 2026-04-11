package com.epam.automation.tests.launches_testng;

import com.epam.automation.business.pages.LaunchesPage;
import com.epam.automation.tests.launches_testng.base.BaseTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LaunchesViewTest extends BaseTest {

    @Test(priority = 6, description = "Verify that the launch view can be opened by click launch name")
    public void shouldOpenLaunchViewWhenClickingLaunchName() {
        LaunchesPage launchesPage = new LaunchesPage();

        loginWithDefaultCredentials().closeToastComponent();

        launchesPage.redirectToLaunchesPage();
        launchesPage.clickFirstLaunch();

        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

    @Test(priority = 6, description = "Verify that the launch view can be opened by click total steps")
    public void shouldOpenLaunchViewWhenClickingTotalSteps() {
        LaunchesPage launchesPage = new LaunchesPage();

        loginWithDefaultCredentials().closeToastComponent();

        launchesPage.redirectToLaunchesPage();
        launchesPage.clickTotalStepsForFirstLaunch();

        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

    @Test(priority = 6, description = "Verify that the launch view can be opened by click passed steps")
    public void shouldOpenLaunchViewWhenClickingPassedSteps() {
        LaunchesPage launchesPage = new LaunchesPage();

        loginWithDefaultCredentials().closeToastComponent();

        launchesPage.redirectToLaunchesPage();
        launchesPage.clickPassedStepsForFirstLaunch();

        assertThat(launchesPage.isListViewDisplayed())
                .as("List view should be displayed")
                .isTrue();
    }

}
