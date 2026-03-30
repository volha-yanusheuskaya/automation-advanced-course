package com.epam.automation.tests;

import com.epam.automation.business.components.ToastComponent;
import com.epam.automation.tests.base.BaseTest;
import com.epam.automation.business.pages.DashboardPage;
import com.epam.automation.business.pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {
    private final SoftAssert softAssert = new SoftAssert();

    private DashboardPage dashboardPage;

    @BeforeMethod
    public void loginStep() {
        dashboardPage = new DashboardPage(driver);

        new LoginPage(driver).login();
    }

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        ToastComponent toastComponent = new ToastComponent(driver);

        String dashboardPageUrl = dashboardPage.getDashboardPageUrl();
        String dashboardPageTitle = dashboardPage.getDashboardPageTitle();
        String actualToastMessage = toastComponent.getToastMessage();

        softAssert.assertTrue(toastComponent.isToastComponentDisplayed(), "Toast was not displayed");
        softAssert.assertEquals(actualToastMessage, "Signed in successfully", "Toast message was not displayed");
        softAssert.assertEquals(dashboardPageTitle, "Report Portal", "Login was not successful");
        softAssert.assertEquals(dashboardPageUrl, "http://localhost:8080/ui/#default_personal/dashboard", "Login was not successful");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify displaying of the Dashboard page after login")
    public void testDashboardPage() {
        softAssert.assertTrue(dashboardPage.isAllDashboardsTitleDisplayed(), "All Dashboards title is not displayed");
        softAssert.assertTrue(dashboardPage.isDemoDashboardDisplayed(), "Demo Dashboard is not displayed");
        softAssert.assertAll();
    }
}
