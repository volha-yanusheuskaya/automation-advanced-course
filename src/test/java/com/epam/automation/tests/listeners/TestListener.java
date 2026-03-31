package com.epam.automation.tests.listeners;

import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.epam.automation.core.utils.ScreenshotUtil.takeScreenshot;

public class TestListener implements ITestListener {
    private static final ILogger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.logTestStart(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.logTestEnd(result.getName(), "PASSED");
        takeScreenshot("Success_screen_");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.logTestEnd(result.getName(), "FAILED");
        takeScreenshot("Failure_screen_");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: {}", context.getName());
    }
}
