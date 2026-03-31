package com.epam.automation.tests.base;

import com.epam.automation.business.models.LoginService;
import com.epam.automation.business.models.User;
import com.epam.automation.business.pages.DashboardPage;
import com.epam.automation.business.pages.LoginPage;
import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.driver.DriverManager;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.epam.automation.tests.listeners.ReportPortalListener;
import com.epam.automation.tests.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({TestListener.class, ReportPortalListener.class})
public class BaseTest {
    private static final ILogger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    private final String url = ConfigurationReader.getBaseUrl();

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test...");
        driver = DriverManager.getDriver();
        driver.get(url);
        logger.info("Navigated to: {}", url);
    }

    protected DashboardPage loginWithDefaultCredentials() {
        LoginService loginService = new LoginService(new LoginPage());
        return loginService.loginAs(User.defaultUser());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.info("Tearing down test...");
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
        }
        DriverManager.quitDriver();
        logger.info("Driver closed successfully");
    }
}
