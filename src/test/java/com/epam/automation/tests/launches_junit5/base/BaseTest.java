package com.epam.automation.tests.launches_junit5.base;

import com.epam.automation.core.base.BaseTestCore;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Method;

import static com.epam.automation.core.utils.ScreenshotUtil.takeScreenshot;

@ExtendWith({TestResultExtension.class})
public class BaseTest extends BaseTestCore {
    private static final ILogger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        performSetUp();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        try {
            String testName = testInfo.getTestMethod()
                    .map(Method::getName)
                    .orElse("Unknown");
            takeScreenshot("Test_" + testName + "_");
        } catch (Exception e) {
            logger.error("Error taking screenshot: {}", e.getMessage());
        }
        performTearDown();
    }
}
