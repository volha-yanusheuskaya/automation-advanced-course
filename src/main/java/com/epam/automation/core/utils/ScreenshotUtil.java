package com.epam.automation.core.utils;

import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.driver.DriverSingleton;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.epam.reportportal.service.ReportPortal;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static final String SCREENSHOTS_PATH = ConfigurationReader.getProperty("screenshot.path");
    private static final ILogger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    public static String takeScreenshot(String result) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = result + "_" + timeStamp + ".png";
        String filePath = SCREENSHOTS_PATH + fileName;

        try {
            File source = ((TakesScreenshot) DriverSingleton.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            FileHandler.copy(source, destination);
            return filePath;
        } catch (IOException e) {
            logger.error("Screenshot failed " + e.getMessage());
            return null;
        }
    }

    public static void saveScreenshotForReportPortal() {
        String message = "Screenshot is attached!";
        String level = "1";
        if (DriverSingleton.getInstance().getDriver() instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) DriverSingleton.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
            ReportPortal.emitLog(message, level, new Date(), screenshot);
        }
    }
}
