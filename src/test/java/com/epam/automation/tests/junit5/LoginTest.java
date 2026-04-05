package com.epam.automation.tests.junit5;

import com.epam.automation.business.components.ToastComponent;
import com.epam.automation.business.pages.DashboardPage;
import com.epam.automation.tests.junit5.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Login test suite")
@TestMethodOrder(OrderAnnotation.class)
@Order(1)
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Verify successful login with valid credentials")
    public void shouldLoginSuccessfully_WhenValidCredentialsProvided() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();
        ToastComponent toastComponent = new ToastComponent();

        String actualToastMessage = toastComponent.getToastMessage();
        String expectedToastMessage = "Signed in successfully";

        assertThat(toastComponent.isToastComponentDisplayed()).as("Toast component is not displayed").isTrue();
        assertThat(actualToastMessage).as("Toast message is not correct").isEqualTo(expectedToastMessage);
        assertThat(dashboardPage.isDashboardPageTitle()).as("Dashboard page title is not correct").isTrue();
        assertThat(dashboardPage.isDashboardPageUrl()).as("Dashboard page URL is not correct").isTrue();
    }

    @Test
    @DisplayName("Verify displaying of the Dashboard page after login")
    public void shouldDisplayDashboardPage_WhenUserIsLoggedIn() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();

        assertThat(dashboardPage.isAllDashboardsTitleDisplayed()).as("All Dashboards title is not displayed").isTrue();
        assertThat(dashboardPage.redirectToDemoDashboard().isDemoDashboardDisplayed()).as("Demo Dashboard is not displayed").isTrue();
    }
}
