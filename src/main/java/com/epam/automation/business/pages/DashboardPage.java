package com.epam.automation.business.pages;

import com.epam.automation.core.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.epam.automation.core.utils.ElementUtil.isDisplayed;

public class DashboardPage extends BasePage {

    @FindBy(css = "span[title='All Dashboards']")
    private WebElement allDashboardsTitle;

    @FindBy(xpath = "//div[starts-with(@class,'dashboardTable')] //a[@href='#default_personal/dashboard/14']")
    private WebElement demoDashboard;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getDashboardPageTitle() {
        return getPageTitle();
    }

    public String getDashboardPageUrl() {
        return getCurrentUrl();
    }

    public boolean isAllDashboardsTitleDisplayed() {
        return isDisplayed(allDashboardsTitle);
    }

    public boolean isDemoDashboardDisplayed() {
        return isDisplayed(demoDashboard);
    }
}
