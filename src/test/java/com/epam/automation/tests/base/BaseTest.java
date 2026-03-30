package com.epam.automation.tests.base;

import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.driver.DriverSingleton;
import com.epam.automation.tests.listeners.ReportPortalListener;
import com.epam.automation.tests.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners({TestListener.class, ReportPortalListener.class})
public class BaseTest {
    protected WebDriver driver;
    private final String url = ConfigurationReader.getProperty("url");

    @BeforeMethod
    public void setUp() {
        driver = DriverSingleton.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(url);
    }

    @AfterMethod
    public void tearDown() {
        DriverSingleton.getInstance().closeDriver();
    }
}
