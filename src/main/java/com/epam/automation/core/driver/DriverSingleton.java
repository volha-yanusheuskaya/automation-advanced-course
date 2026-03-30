package com.epam.automation.core.driver;

import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.openqa.selenium.WebDriver;

public class DriverSingleton {
    private static WebDriver driver;
    private static DriverSingleton instance = new DriverSingleton();
    private static final ILogger logger = LoggerFactory.getLogger(DriverSingleton.class);

    private DriverSingleton() {
    }

    public static DriverSingleton getInstance() {
        if (instance == null) {
            instance = new DriverSingleton();
        }
        return instance;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.createDriver();
        }
        return driver;
    }

    public void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        } else {
            logger.error("Driver is already null!");
        }
    }
}
