package com.epam.automation.tests.launches_testng.base;

import com.epam.automation.core.base.BaseTestCore;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.epam.automation.tests.listeners.ReportPortalListener;
import com.epam.automation.tests.listeners.TestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({TestListener.class, ReportPortalListener.class})
public class BaseTest extends BaseTestCore {
    private static final ILogger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        performSetUp();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
        }
        performTearDown();
    }
}
