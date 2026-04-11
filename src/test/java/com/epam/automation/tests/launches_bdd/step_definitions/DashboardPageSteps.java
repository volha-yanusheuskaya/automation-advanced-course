package com.epam.automation.tests.launches_bdd.step_definitions;

import com.epam.automation.business.pages.DashboardPage;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class DashboardPageSteps {
    private final ScenarioContext scenarioContext;

    public DashboardPageSteps(ScenarioContext context) {
        this.scenarioContext = context;
    }

    @Then("Dashboard page should be displayed with correct title")
    public void dashboardPageShouldBeDisplayedWithCorrectTitle() {
        DashboardPage dashboardPage = scenarioContext.getDashboardPage();
        assertThat(dashboardPage.isDashboardPageTitle())
                .as("Dashboard page title is not correct")
                .isTrue();
    }

    @Then("Dashboard page should be displayed with correct URL")
    public void dashboardPageShouldBeDisplayedWithCorrectUrl() {
        DashboardPage dashboardPage = scenarioContext.getDashboardPage();
        assertThat(dashboardPage.isDashboardPageUrl())
                .as("Dashboard page URL is not correct")
                .isTrue();
    }

    @Then("All Dashboards heading should be displayed on the Dashboard page")
    public void defaultDashboardsHeadingShouldBeDisplayed() {
        DashboardPage dashboardPage = scenarioContext.getDashboardPage();
        assertThat(dashboardPage.isAllDashboardsHeadingDisplayed())
                .as("All Dashboards heading is not displayed")
                .isTrue();
    }

    @Then("User should switch to the Demo Dashboard dashboard")
    public void userShouldSwitchToTheDemoDashboardDashboard() {
        DashboardPage dashboardPage = scenarioContext.getDashboardPage();
        assertThat(dashboardPage.redirectToDemoDashboard().isDemoDashboardDisplayed())
                .as("Demo Dashboard is not displayed")
                .isTrue();
    }
}
