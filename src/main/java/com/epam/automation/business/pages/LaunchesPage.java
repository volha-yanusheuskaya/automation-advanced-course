package com.epam.automation.business.pages;

import com.epam.automation.business.models.Launch;
import com.epam.automation.core.base.BasePage;
import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.utils.WaitUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static com.epam.automation.core.utils.ElementUtil.click;

public class LaunchesPage extends BasePage {

    private static final String baseURL = ConfigurationReader.getBaseUrl();
    private static final String DEMO_PROJECT = ConfigurationReader.getProperty("demo.project");
    private static final String LAUNCHES_PAGE_URL = baseURL + "/ui/#" + DEMO_PROJECT + "/launches/all";

    @FindBy(xpath = "//div[contains(@class,'allLatestDropdown__selected-value')]/div[contains(@class,'active')]")
    private WebElement allLaunchesTitle;

    @FindBy(xpath = "//td//div[contains(@class,'itemInfo__name')]//span")
    private List<WebElement> launchName;

    @FindBy(xpath = "//td//span[contains(@class,'itemInfo__number')]")
    private List<WebElement> launchNumber;

    @FindBy(xpath = "//span[contains(@class,'title-full') and text()='name']")
    private WebElement nameColumnHeader;

    @FindBy(xpath = "//div[contains(@class,'grid-row-wrapper')]")
    private List<WebElement> totalLaunches;

    @FindBy(xpath = "//span[contains(@class,'absolute-time')]")
    private List<WebElement> startTimeValue;

    @FindBy(xpath = "//div[contains(@class,'total-col')]")
    private List<WebElement> totalSteps;

    @FindBy(xpath = "//div[contains(@class,'passed-col')]")
    private List<WebElement> passedSteps;

    @FindBy(xpath = "//div[contains(@class,'failed-col')]")
    private List<WebElement> failedSteps;

    @FindBy(xpath = "//div[contains(@class,'skipped-col')]")
    private List<WebElement> skippedSteps;

    @FindBy(xpath = "//div[contains(@class,'pb-col')]")
    private List<WebElement> productBugCount;

    @FindBy(xpath = "//div[contains(@class,'ab-col')]")
    private List<WebElement> autoBugCount;

    @FindBy(xpath = "//div[contains(@class,'si-col')]")
    private List<WebElement> systemIssueCount;

    @FindBy(xpath = "//div[contains(@class,'ti-col')]")
    private List<WebElement> toInvestigateCount;

    public LaunchesPage() {
        super();
    }

    public void redirectToLaunchesPage() {
        driver.get(LAUNCHES_PAGE_URL);
        WaitUtil.waitForElementVisible(allLaunchesTitle);
    }

    private List<WebElement> getLaunchNames() {
        return launchName;
    }

    private List<WebElement> getLaunchNumber() {
        return launchNumber;
    }

    public List<String> getAllLaunchesNames() {
        List<String> allLaunchesNames = new ArrayList<>();
        for (int i = 0; i < getLaunchesCount(); i++) {
            String launchName = getLaunchNames().get(i).getText();
            String launchNumber = getLaunchNumber().get(i).getText();
            String nameWithNumber = launchName + " " + launchNumber;
            allLaunchesNames.add(nameWithNumber);
        }
        return allLaunchesNames;
    }

    public boolean totalLaunchesEmpty() {
        return totalLaunches.isEmpty();
    }

    public boolean totalLaunchesNotEmpty() {
        return !totalLaunches.isEmpty();
    }

    public boolean isLaunchesListDisplayed() {
        return totalLaunches != null && totalLaunchesNotEmpty();
    }

    public List<WebElement> getTotalLaunches() {
        return totalLaunches;
    }

    public int getLaunchesCount() {
        return getTotalLaunches().size();
    }

    public List<String> getAllStartTimes() {
        List<String> times = new ArrayList<>();
        for (WebElement element : startTimeValue) {
            String time = element.getAttribute("innerHTML");
            if (time != null) {
                time = time.trim();
            }
            times.add(time);
        }
        return times;
    }

    public void clickNameColumnHeaderToSort() {
        click(nameColumnHeader);
    }

    public List<Launch> getLaunchesCountDataFromPage() {
        List<Launch> launches = new ArrayList<>();
        int launchesCount = getLaunchesCount();

        for (int i = 0; i < launchesCount; i++) {
            String name = launchName.get(i).getText() + " " + launchNumber.get(i).getText();
            String dateAttr = startTimeValue.get(i).getAttribute("innerHTML");
            String date = (dateAttr != null) ? dateAttr.trim() : "";

            int totalTests = parseIntSafely(totalSteps.get(i).getText());
            int passedTests = parseIntSafely(passedSteps.get(i).getText());
            int failedTests = parseIntSafely(failedSteps.get(i).getText());
            int skippedTests = parseIntSafely(skippedSteps.get(i).getText());
            int productBugValue = parseIntSafely(productBugCount.get(i).getText());
            int autoBugValue = parseIntSafely(autoBugCount.get(i).getText());
            int systemIssueValue = parseIntSafely(systemIssueCount.get(i).getText());
            int toInvestigateValue = parseIntSafely(toInvestigateCount.get(i).getText());

            Launch launch = new Launch(name, date, totalTests, passedTests, failedTests, skippedTests,
                    productBugValue, autoBugValue, systemIssueValue, toInvestigateValue);
            launches.add(launch);
        }
        return launches;
    }

    private int parseIntSafely(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
