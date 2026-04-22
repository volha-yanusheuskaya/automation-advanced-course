package com.epam.automation.tests.ui.listeners;

import com.epam.automation.ui.core.utils.ScreenshotUtil;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.testng.ITestResult;

public class ReportPortalListener extends ReportPortalTestNGListener {

    @Override
    public void onTestFailure(ITestResult testResult) {
        ScreenshotUtil.saveScreenshotForReportPortal();
        super.onTestFailure(testResult);
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        ScreenshotUtil.saveScreenshotForReportPortal();
        super.onTestSuccess(testResult);
    }
}
