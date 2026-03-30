package com.epam.automation.core.utils;

import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.openqa.selenium.WebElement;

import static com.epam.automation.core.utils.WaitUtil.*;

public class ElementUtil {
    private static final ILogger logger = LoggerFactory.getLogger(ElementUtil.class);

    public static void sendText(WebElement element, String text) {
        try {
            waitForElementVisible(element);
            element.clear();
            element.sendKeys(text);

            String logMessage = isSensitivePasswordField(element) ? "***MASKED***" : text;
            logger.info("Text sent to element: {}", logMessage);
        } catch (Exception e) {
            logger.error("Failed to send text to element", e);
        }
    }

    public static void click(WebElement element) {
        try {
            waitForElementClickable(element);
            element.click();
            logger.info("Element clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to click element", e);
        }
    }

    public static String getText(WebElement element) {
        try {
            waitForElementVisible(element);
            String text = element.getText();
            logger.info("Text retrieved: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element", e);
            return "";
        }
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            waitForElementVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            logger.warn("Element not displayed");
            return false;
        }
    }

    private static boolean isSensitivePasswordField(WebElement element) {
        String elementName = element.getAttribute("name");
        if (elementName != null) {
            String lowerName = elementName.toLowerCase();
            return lowerName.contains("password");
        }
        return false;
    }
}

