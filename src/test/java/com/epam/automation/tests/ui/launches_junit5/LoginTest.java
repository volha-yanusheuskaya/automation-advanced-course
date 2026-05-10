package com.epam.automation.tests.ui.launches_junit5;

import com.epam.automation.ui.business.components.ToastComponent;
import com.epam.automation.ui.business.pages.DashboardPage;
import com.epam.automation.tests.ui.launches_junit5.base.JunitUiTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Login test suite")
@TestMethodOrder(OrderAnnotation.class)
@Order(1)
class LoginTest extends JunitUiTestBase {

    @Test
    @DisplayName("Verify successful login with valid credentials")
    void shouldLoginSuccessfully_WhenValidCredentialsProvided() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();
        ToastComponent toastComponent = new ToastComponent();

        String actualToastMessage = toastComponent.getToastMessage();
        String expectedToastMessage = "Signed in successfully";

        assertThat(toastComponent.isToastComponentDisplayed())
                .as("Toast component is not displayed")
                .isTrue();
        assertThat(actualToastMessage)
                .as("Toast message is not correct")
                .isEqualTo(expectedToastMessage);
        assertThat(dashboardPage.isDashboardPageTitle())
                .as("Dashboard page title is not correct")
                .isTrue();
        assertThat(dashboardPage.isDashboardPageUrl())
                .as("Dashboard page URL is not correct")
                .isTrue();
    }

    @Test
    @DisplayName("Verify displaying of the Dashboard page after login")
    void shouldDisplayDashboardPage_WhenUserIsLoggedIn() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();

        assertThat(dashboardPage.isAllDashboardsHeadingDisplayed())
                .as("All Dashboards heading is not displayed")
                .isTrue();
        assertThat(dashboardPage.redirectToDemoDashboard().isDemoDashboardDisplayed())
                .as("Demo Dashboard is not displayed")
                .isTrue();
    }
}
