package com.epam.automation.core.utils;

import com.epam.automation.core.driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.epam.automation.core.config.ConfigurationReader.getDefaultTimeout;

public class WaitUtil {

    public static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getDefaultTimeout()));
    }

    public static void waitForElementVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForUrlContains(String urlFragment) {
        getWait().until(ExpectedConditions.urlContains(urlFragment));
    }

    public static void waitForElementContainsText(WebElement element, String expectedText) {
        getWait().until(ExpectedConditions.textToBePresentInElement(element, expectedText));
    }
}
