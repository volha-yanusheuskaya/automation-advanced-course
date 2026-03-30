package com.epam.automation.core.utils;

import com.epam.automation.core.driver.DriverSingleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {

    private static final int DEFAULT_TIMEOUT = 30;

    public static WebDriverWait getWait() {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public static void waitForElementVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
}
