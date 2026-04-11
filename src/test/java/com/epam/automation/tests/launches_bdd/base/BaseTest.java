package com.epam.automation.tests.launches_bdd.base;

import com.epam.automation.core.base.BaseTestCore;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import io.cucumber.java.*;

import static com.epam.automation.core.utils.ScreenshotUtil.takeScreenshot;

public class BaseTest extends BaseTestCore {
    private static final ILogger logger = LoggerFactory.getLogger(BaseTestCore.class);

    @Before
    public void setUp() {
        performSetUp();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            takeScreenshot("Test_" + scenario.getName() + "_");
        } catch (Exception e) {
            logger.error("Error taking screenshot: {}", e.getMessage());
        }
        performTearDown();
    }
}
