package com.epam.automation.business.pages;

import com.epam.automation.core.base.BasePage;
import com.epam.automation.core.config.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.epam.automation.core.utils.ElementUtil.isDisplayed;

public class DashboardPage extends BasePage {

    public static final String DASHBOARD_PAGE_TITLE = "Report Portal";

    private static final String baseURL = ConfigurationReader.getBaseUrl();
    private static final String DEFAULT_PROJECT = ConfigurationReader.getProperty("dashboard.project");
    private static final String DEMO_PROJECT = ConfigurationReader.getProperty("demo.project");

    public static final String DEFAULT_DASHBOARD_URL = configureDashboardUrl(DEFAULT_PROJECT);
    private static final String DEMO_DASHBOARD_URL = configureDashboardUrl(DEMO_PROJECT);

    @FindBy(css = "span[title='All Dashboards']")
    private WebElement allDashboardsTitle;

    @FindBy(xpath = "//div[contains(@class,'dashboardTable')]//a[contains(@href,'dashboard')]")
    private WebElement demoDashboard;

    public DashboardPage() {
        super();
    }

    public boolean isDashboardPageTitle() {
        return getPageTitle().equals(DASHBOARD_PAGE_TITLE);
    }

    public boolean isDashboardPageUrl() {
        return getCurrentUrl().equals(DEFAULT_DASHBOARD_URL);
    }

    public boolean isAllDashboardsTitleDisplayed() {
        return isDisplayed(allDashboardsTitle);
    }

    public boolean isDemoDashboardDisplayed() {
        return isDisplayed(demoDashboard);
    }

    public DashboardPage redirectToDemoDashboard() {
        driver.navigate().to(DEMO_DASHBOARD_URL);
        return this;
    }

    private static String configureDashboardUrl(String projectName) {
        return baseURL + "/ui/#" + projectName + "/dashboard";
    }
}
