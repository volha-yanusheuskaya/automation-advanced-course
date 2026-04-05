package com.epam.automation.tests.launches_testng;

import com.epam.automation.business.components.ToastComponent;
import com.epam.automation.tests.launches_testng.base.BaseTest;
import com.epam.automation.business.pages.DashboardPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void shouldLoginSuccessfully_WhenValidCredentialsProvided() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();
        ToastComponent toastComponent = new ToastComponent();

        String actualToastMessage = toastComponent.getToastMessage();
        String expectedToastMessage = "Signed in successfully";

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(toastComponent.isToastComponentDisplayed(), "Toast component is not displayed");
        softAssert.assertEquals(actualToastMessage, expectedToastMessage, "Toast message is not correct");
        softAssert.assertTrue(dashboardPage.isDashboardPageTitle(), "Dashboard page title is not correct");
        softAssert.assertTrue(dashboardPage.isDashboardPageUrl(), "Dashboard page URL is not correct");
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Verify displaying of the Dashboard page after login")
    public void shouldDisplayDashboardPage_WhenUserIsLoggedIn() {
        DashboardPage dashboardPage = loginWithDefaultCredentials();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(dashboardPage.isAllDashboardsTitleDisplayed(), "All Dashboards title is not displayed");
        softAssert.assertTrue(dashboardPage.redirectToDemoDashboard().isDemoDashboardDisplayed(), "Demo Dashboard is not displayed");
        softAssert.assertAll();
    }
}
