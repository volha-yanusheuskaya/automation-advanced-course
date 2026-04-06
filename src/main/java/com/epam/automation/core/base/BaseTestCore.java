package com.epam.automation.core.base;

import com.epam.automation.business.models.User;
import com.epam.automation.business.pages.DashboardPage;
import com.epam.automation.business.pages.LoginPage;
import com.epam.automation.business.service.LoginService;
import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.driver.DriverManager;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.openqa.selenium.WebDriver;

public abstract class BaseTestCore {
    private static final ILogger logger = LoggerFactory.getLogger(BaseTestCore.class);
    protected WebDriver driver;
    private final String baseUrl = ConfigurationReader.getBaseUrl();

    protected void performSetUp() {
        logger.info("Setting up test...");
        driver = DriverManager.getDriver();
        driver.get(baseUrl);
        logger.info("Navigated to: {}", baseUrl);
    }

    protected DashboardPage loginWithDefaultCredentials() {
        LoginService loginService = new LoginService(new LoginPage());
        return loginService.loginAs(User.defaultUser());
    }

    protected void performTearDown() {
        logger.info("Tearing down test...");
        DriverManager.quitDriver();
        logger.info("Driver closed successfully");
    }
}
