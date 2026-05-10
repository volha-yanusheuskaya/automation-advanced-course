package com.epam.automation.ui.core.utils;

import com.epam.automation.common.core.config.ConfigurationReader;
import com.epam.automation.ui.core.driver.DriverManager;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import com.epam.reportportal.service.ReportPortal;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ScreenshotUtil {
    public static final String SCREENSHOTS_PATH = ConfigurationReader.getProperty("screenshot.path");
    private static final ILogger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() {
    }

    public static Optional<String> takeScreenshot(String result) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = result + "_" + timeStamp + ".png";
        String filePath = SCREENSHOTS_PATH + fileName;

        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver instanceof TakesScreenshot takesScreenshot) {
                File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
                File destination = new File(filePath);
                File parentDir = destination.getParentFile();
                if (parentDir != null) {
                    parentDir.mkdirs();
                }
                FileHandler.copy(source, destination);
                logger.info("Screenshot saved: {}", filePath);
                return Optional.of(filePath);
            }
        } catch (IOException e) {
            logger.error("Screenshot failed: {}", e.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    public static void saveScreenshotForReportPortal() {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                logger.warn("Driver is null, cannot take screenshot for ReportPortal");
                return;
            }
            if (driver instanceof TakesScreenshot takesScreenshot) {
                File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
                ReportPortal.emitLog("Screenshot is attached", "INFO", new Date(), screenshot);
            }
        } catch (Exception e) {
            logger.error("Failed to save screenshot to ReportPortal: {}" + e.getMessage());
        }
    }
}
