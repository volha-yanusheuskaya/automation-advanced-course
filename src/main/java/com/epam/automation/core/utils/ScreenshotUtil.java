package com.epam.automation.core.utils;

import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.driver.DriverManager;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
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

    public static Optional<String> takeScreenshot(String result) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = result + "_" + timeStamp + ".png";
        String filePath = SCREENSHOTS_PATH + fileName;

        try {
            File source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            destination.getParentFile().mkdirs();
            FileHandler.copy(source, destination);
            logger.info("Screenshot saved: {}", filePath);
            return Optional.of(filePath);
        } catch (IOException e) {
            logger.error("Screenshot failed: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public static void saveScreenshotForReportPortal() {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                logger.warn("Driver is null, cannot take screenshot for ReportPortal");
                return;
            }
            if (driver instanceof TakesScreenshot) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                ReportPortal.emitLog("Screenshot is attached", "INFO", new Date(), screenshot);
            }
        } catch (Exception e) {
            logger.error("Failed to save screenshot to ReportPortal: {}" + e.getMessage());
        }
    }
}
