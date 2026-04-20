package com.epam.automation.ui.core.base;

import com.epam.automation.ui.business.models.User;
import com.epam.automation.ui.business.pages.DashboardPage;
import com.epam.automation.ui.business.pages.LoginPage;
import com.epam.automation.ui.business.service.LoginService;
import com.epam.automation.common.core.config.ConfigurationReader;
import com.epam.automation.ui.core.driver.DriverManager;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
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
