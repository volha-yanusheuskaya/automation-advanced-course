package com.epam.automation.tests.junit5.base;

import com.epam.automation.business.service.LoginService;
import com.epam.automation.business.models.User;
import com.epam.automation.business.pages.DashboardPage;
import com.epam.automation.business.pages.LoginPage;
import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.driver.DriverManager;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static com.epam.automation.core.utils.ScreenshotUtil.takeScreenshot;

@ExtendWith({TestResultExtension.class})
public class BaseTest {
    private static final ILogger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    private final String url = ConfigurationReader.getBaseUrl();

    @BeforeEach
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

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        logger.info("Tearing down test...");
        try {
            takeScreenshot("Test_" + testInfo.getDisplayName() + "_");
        } catch (Exception e) {
            logger.error("Error taking screenshot: {}", e.getMessage());
        }
        DriverManager.quitDriver();
        logger.info("Driver closed successfully");
    }
}
