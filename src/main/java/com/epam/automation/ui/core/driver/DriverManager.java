package com.epam.automation.ui.core.driver;

import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ILogger logger = LoggerFactory.getLogger(DriverManager.class);

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(DriverFactory.createDriver());
            logger.info("Driver initialized for thread: {}", Thread.currentThread().threadId());
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("Driver closed for thread: {}", Thread.currentThread().threadId());
        }
    }
}
