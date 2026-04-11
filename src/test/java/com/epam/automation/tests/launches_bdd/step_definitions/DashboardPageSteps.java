package com.epam.automation.tests.launches_bdd.step_definitions;

import com.epam.automation.business.components.ToastComponent;
import com.epam.automation.business.cucumber.BaseStepDefinitions;
import com.epam.automation.business.pages.DashboardPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class DashboardPageSteps extends BaseStepDefinitions {

    private final DashboardPage dashboardPage;

    public DashboardPageSteps() {
        this.dashboardPage = new DashboardPage();
    }

    @When("User closes the Toast component")
    public void userClosesTheToastComponent() {
        new ToastComponent().clickCloseToast();
    }

    @Then("Dashboard page should be displayed with correct title")
    public void dashboardPageShouldBeDisplayedWithCorrectTitle() {
        assertThat(dashboardPage.isDashboardPageTitle())
                .as("Dashboard page title is not correct")
                .isTrue();
    }

    @Then("Dashboard page should be displayed with correct URL")
    public void dashboardPageShouldBeDisplayedWithCorrectUrl() {
        assertThat(dashboardPage.isDashboardPageUrl())
                .as("Dashboard page URL is not correct")
                .isTrue();
    }

    @Then("All Dashboards heading should be displayed on the Dashboard page")
    public void defaultDashboardsHeadingShouldBeDisplayed() {
        assertThat(dashboardPage.isAllDashboardsHeadingDisplayed())
                .as("All Dashboards heading is not displayed")
                .isTrue();
    }

    @Then("User should switch to the Demo Dashboard dashboard")
    public void userShouldSwitchToTheDemoDashboardDashboard() {
        assertThat(dashboardPage.redirectToDemoDashboard().isDemoDashboardDisplayed())
                .as("Demo Dashboard is not displayed")
                .isTrue();
    }
}
