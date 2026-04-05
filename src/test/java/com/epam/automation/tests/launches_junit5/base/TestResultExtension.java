package com.epam.automation.tests.launches_junit5.base;

import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.epam.automation.core.utils.ScreenshotUtil;
import org.junit.jupiter.api.extension.*;

public class TestResultExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, TestExecutionExceptionHandler {
    private static final ILogger logger = LoggerFactory.getLogger(TestResultExtension.class);

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        logger.logTestStart(context.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            logger.logTestEnd(context.getDisplayName(), "PASSED");
            ScreenshotUtil.saveScreenshotForReportPortal();
        }
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        logger.logTestEnd(context.getDisplayName(), "FAILED");
        ScreenshotUtil.saveScreenshotForReportPortal();
        throw throwable;
    }
}
