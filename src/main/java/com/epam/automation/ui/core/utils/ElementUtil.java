package com.epam.automation.ui.core.utils;

import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import org.openqa.selenium.WebElement;

import static com.epam.automation.ui.core.utils.WaitUtil.*;

public class ElementUtil {
    private static final ILogger logger = LoggerFactory.getLogger(ElementUtil.class);

    private ElementUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void sendText(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);

        String logMessage = isSensitivePasswordField(element) ? "***MASKED***" : text;
        logger.info("Text sent to element: {}", logMessage);
    }

    public static void click(WebElement element) {
        waitForElementClickable(element);
        element.click();
        logger.info("Element clicked successfully");
    }

    public static String getText(WebElement element) {
        waitForElementVisible(element);
        String text = element.getText();
        logger.info("Text retrieved: {}", text);
        return text;
    }

    public static boolean isDisplayed(WebElement element) {
        waitForElementVisible(element);
        return element.isDisplayed();
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

