package com.epam.automation.tests.ui.launches_junit5.base;

import com.epam.automation.ui.core.base.BaseTestCore;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Method;

import static com.epam.automation.ui.core.utils.ScreenshotUtil.takeScreenshot;

@ExtendWith({TestResultExtension.class})
@Tag("ui_junit")
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
