package com.epam.automation.tests.ui.launches_testng.base;

import com.epam.automation.ui.core.base.BaseTestCore;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import com.epam.automation.tests.ui.listeners.ReportPortalListener;
import com.epam.automation.tests.ui.listeners.TestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({TestListener.class, ReportPortalListener.class})
@Test(groups = "ui_testng")
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
