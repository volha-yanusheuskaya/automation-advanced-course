package com.epam.automation.tests.launches_testng;

import com.epam.automation.business.components.ToastComponent;
import com.epam.automation.tests.launches_testng.base.BaseTest;
import com.epam.automation.business.pages.DashboardPage;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void shouldLoginSuccessfully_WhenValidCredentialsProvided() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();
        ToastComponent toastComponent = new ToastComponent();

        String actualToastMessage = toastComponent.getToastMessage();
        String expectedToastMessage = "Signed in successfully";

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(toastComponent.isToastComponentDisplayed())
                .as("Toast component is not displayed")
                .isTrue();
        softly.assertThat(actualToastMessage)
                .as("Toast message is not correct")
                .isEqualTo(expectedToastMessage);
        softly.assertThat(dashboardPage.isDashboardPageTitle())
                .as("Dashboard page title is not correct")
                .isTrue();
        softly.assertThat(dashboardPage.isDashboardPageUrl())
                .as("Dashboard page URL is not correct")
                .isTrue();
        softly.assertAll();
    }

    @Test(priority = 1, description = "Verify displaying of the Dashboard page after login")
    public void shouldDisplayDashboardPage_WhenUserIsLoggedIn() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(dashboardPage.isAllDashboardsHeadingDisplayed())
                .as("All Dashboards heading is not displayed")
                .isTrue();
        softly.assertThat(dashboardPage.redirectToDemoDashboard().isDemoDashboardDisplayed())
                .as("Demo Dashboard is not displayed")
                .isTrue();
        softly.assertAll();
    }
}
