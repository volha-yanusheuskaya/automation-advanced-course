package com.epam.automation.business.pages;

import com.epam.automation.core.base.BasePage;
import com.epam.automation.core.config.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.epam.automation.core.utils.ElementUtil.isDisplayed;

public class DashboardPage extends BasePage {

    public static final String DASHBOARD_PAGE_TITLE = "Report Portal";
    private static final String baseURL = ConfigurationReader.getBaseUrl();
    public static final String DEFAULT_DASHBOARD_URL = baseURL + "/ui/#volha_yanusheuskaya_personal/dashboard";
    private static final String DEMO_DASHBOARD_URL = baseURL + "/ui/#yanusheuskaya_personal/dashboard";

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
}
