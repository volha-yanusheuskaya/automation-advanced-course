package com.epam.automation.ui.business.pages;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.ui.core.base.BasePage;
import com.epam.automation.common.core.config.ConfigurationReader;
import com.epam.automation.ui.core.utils.WaitUtil;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static com.epam.automation.ui.core.utils.ElementUtil.click;

/**
 * Page Object representing the Launches page in Report Portal.
 * <p>
 * Encapsulates interaction with the launches list view, providing methods
 * for navigation, sorting, and data extraction. Uses Page Object Model pattern
 * with Selenium WebDriver for element interaction.
 *
 * <p>Key functionality:
 * <ul>
 *   <li>Navigate to launches page</li>
 *   <li>Extract launch data (names, dates, test counts, defect counts)</li>
 *   <li>Click column headers to sort launches</li>
 *   <li>Verify launches list is displayed</li>
 * </ul>
 */
public class LaunchesPage extends BasePage {

    private static final String baseURL = ConfigurationReader.getBaseUrl();
    private static final String DEMO_PROJECT = ConfigurationReader.getProperty("demo.project");
    private static final String LAUNCHES_PAGE_URL = baseURL + "/ui/#" + DEMO_PROJECT + "/launches/all";
    private static final String LAUNCH_SELECTION_ELEMENT_PATTERN = "//div[contains(@class,'grid-row-wrapper')][%d]//div[contains(@class,'checkIcon')]";

    @FindBy(xpath = "//div[contains(@class,'allLatestDropdown__selected-value')]/div[contains(@class,'active')]")
    private WebElement allLaunchesTitle;

    @FindBy(xpath = "//td//div[contains(@class,'itemInfo__name')]//span")
    private List<WebElement> launchName;

    @FindBy(xpath = "//td//span[contains(@class,'itemInfo__number')]")
    private List<WebElement> launchNumber;

    @Getter
    @FindBy(xpath = "//div[contains(@class,'grid-row-wrapper')]")
    private List<WebElement> totalLaunches;

    @FindBy(xpath = "//span[contains(@class,'title-full') and text()='name']")
    private WebElement nameColumnHeader;

    @FindBy(xpath = "//span[contains(@class,'absolute-time')]")
    private List<WebElement> startTimeValue;

    @FindBy(xpath = "//div[contains(@class,'total-col')]")
    private List<WebElement> totalSteps;

    @FindBy(xpath = "//a[@statuses='PASSED,FAILED,SKIPPED,INTERRUPTED']")
    private List<WebElement> allTotalSteps;

    @FindBy(xpath = "//div[contains(@class,'passed-col')]")
    private List<WebElement> passedSteps;

    @FindBy(xpath = "//a[@statuses='PASSED']")
    private List<WebElement> allPassedSteps;

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

    @FindBy(xpath = "//div[contains(@class,'selectedItems__item')]")
    private List<WebElement> selectedLaunches;

    @FindBy(xpath = "//div[contains(@class,'actionPanel')]//i[contains(@class,'toggle-icon')]")
    private WebElement actionsButton;

    @FindBy(xpath = "//div[contains(@class,'actionPanel')]//span[text()='Compare']")
    private WebElement compareButton;

    @FindBy(xpath = "//div[contains(@class,'actionPanel')]//span[text()='Delete']")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[contains(@class,'launch-compare-modal')]")
    private WebElement compareLaunchesModalWindow;

    @FindBy(xpath = "//div[contains(@class,'modal-window')]//span[text()='Delete launch']")
    private WebElement deleteLaunchModalWindow;

    @FindBy(xpath = "//a[contains(@class,'viewTabs__active')]")
    private WebElement listViewTab;

    public LaunchesPage() {
        super();
    }

    /**
     * Redirects to the launches page in Report Portal.
     *
     * <p>Waits for the "All Launches" title to be visible before returning.
     *
     * @see WaitUtil#waitForElementVisible(WebElement)
     */
    public void redirectToLaunchesPage() {
        driver.get(LAUNCHES_PAGE_URL);
        WaitUtil.waitForElementVisible(allLaunchesTitle);
    }

    private List<WebElement> getLaunchNames() {
        return launchName;
    }

    public void clickFirstLaunch() {
        WebElement firstLaunch = getLaunchNames().getFirst();
        click(firstLaunch);
    }

    private List<WebElement> getLaunchNumber() {
        return launchNumber;
    }

    private List<WebElement> getTotalStepsPassed() {
        return allTotalSteps;
    }

    public void clickTotalStepsForFirstLaunch() {
        WebElement firstLaunch = getTotalStepsPassed().getFirst();
        click(firstLaunch);
    }

    private List<WebElement> getPassedStepsPassed() {
        return allPassedSteps;
    }

    public void clickPassedStepsForFirstLaunch() {
        WebElement firstLaunch = getPassedStepsPassed().getFirst();
        click(firstLaunch);
    }

    /**
     * Gets a list of all launch names with their corresponding numbers.
     *
     * <p>Example: ["Demo Api Tests #1", "Demo Api Tests #2"]
     *
     * @return list of formatted launch names with numbers, or empty list if no launches
     */
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

    /**
     * Checks if the total launches container is empty.
     *
     * @return true if no launches are present, false otherwise
     */
    public boolean totalLaunchesEmpty() {
        return totalLaunches.isEmpty();
    }

    public boolean totalLaunchesNotEmpty() {
        return !totalLaunches.isEmpty();
    }

    /**
     * Checks if launches list is displayed on the page.
     *
     * @return true if launches container is visible with content, false otherwise
     */
    public boolean isLaunchesListDisplayed() {
        return totalLaunches != null && totalLaunchesNotEmpty();
    }

    /**
     * Gets the count of launches displayed on the page.
     *
     * @return number of launch rows in the table
     */
    public int getLaunchesCount() {
        return getTotalLaunches().size();
    }

    /**
     * Gets all launch start times from the page.
     *
     * @return list of start times in format "yyyy-MM-dd HH:mm:ss"
     */
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

    /**
     * Clicks the Name column header to sort launches.
     *
     * <p>Toggles between ascending and descending order on repeated clicks.
     */
    public void clickNameColumnHeaderToSort() {
        click(nameColumnHeader);
    }

    /**
     * Extracts launch data from the page and converts to Launch objects.
     *
     * <p>Parses all visible launches and creates Launch instances with
     * complete test statistics.
     *
     * @return list of {@link Launch} objects with data from the page
     * @see Launch
     */
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

    /**
     * Safely parses an integer from text, handling null and non-numeric values.
     *
     * @param text the text to parse, may be null or empty
     * @return the parsed integer, or 0 if text is null, empty, or not a valid number
     */
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

    public void selectLaunchByIndex(int index) {
        String xpath = String.format(LAUNCH_SELECTION_ELEMENT_PATTERN, index);
        WebElement selectionElement = driver.findElement(By.xpath(xpath));
        click(selectionElement);
    }

    public List<String> getSelectedLaunches() {
        List<String> selectedNames = new ArrayList<>();
        for (WebElement element : selectedLaunches) {
            String name = element.getText();
            selectedNames.add(name.trim());
        }
        return selectedNames;
    }

    public void clickActionsButton() {
        click(actionsButton);
    }

    public void clickCompareButton() {
        click(compareButton);
    }

    public void clickDeleteButton() {
        click(deleteButton);
    }

    public boolean isCompareLaunchesModalWindowDisplayed() {
        return compareLaunchesModalWindow.isDisplayed();
    }

    public boolean isDeleteLaunchModalWindowDisplayed() {
        return deleteLaunchModalWindow.isDisplayed();
    }

    public boolean isListViewDisplayed() {
        return listViewTab.isDisplayed();
    }

}
