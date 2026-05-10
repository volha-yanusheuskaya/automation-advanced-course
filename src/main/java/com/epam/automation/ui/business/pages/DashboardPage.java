package com.epam.automation.ui.business.pages;

import com.epam.automation.ui.business.components.ToastComponent;
import com.epam.automation.ui.core.base.BasePage;
import com.epam.automation.common.core.config.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.epam.automation.ui.core.utils.ElementUtil.isDisplayed;

public class DashboardPage extends BasePage {

    public static final String DASHBOARD_PAGE_TITLE = "Report Portal";

    private static final String BASE_URL = ConfigurationReader.getBaseUrl();
    private static final String DEFAULT_PROJECT = ConfigurationReader.getProperty("dashboard.project");
    private static final String DEMO_PROJECT = ConfigurationReader.getProperty("demo.project");

    public static final String DEFAULT_DASHBOARD_URL = configureDashboardUrl(DEFAULT_PROJECT);
    private static final String DEMO_DASHBOARD_URL = configureDashboardUrl(DEMO_PROJECT);

    @FindBy(css = "span[title='All Dashboards']")
    private WebElement allDashboardsHeading;

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

    public boolean isAllDashboardsHeadingDisplayed() {
        return isDisplayed(allDashboardsHeading);
    }

    public boolean isDemoDashboardDisplayed() {
        return isDisplayed(demoDashboard);
    }

    public DashboardPage redirectToDemoDashboard() {
        driver.navigate().to(DEMO_DASHBOARD_URL);
        return this;
    }

    private static String configureDashboardUrl(String projectName) {
        return BASE_URL + "/ui/#" + projectName + "/dashboard";
    }

    public void closeToastComponent() {
        new ToastComponent().clickCloseToast();
    }
}
