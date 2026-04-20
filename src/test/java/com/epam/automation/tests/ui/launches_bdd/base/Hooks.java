package com.epam.automation.tests.ui.launches_bdd.base;

import com.epam.automation.ui.core.base.BaseTestCore;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import io.cucumber.java.*;

import static com.epam.automation.ui.core.utils.ScreenshotUtil.takeScreenshot;

public class Hooks extends BaseTestCore {
    private static final ILogger logger = LoggerFactory.getLogger(Hooks.class);

    @Before(order = 0)
    public void setUpTestRun() {
        performSetUp();
        logger.info("Test run setup complete");
    }

    @After(order = 0)
    public void tearDownTestRun() {
        performTearDown();
        logger.info("Test run teardown complete");
    }

    @Before(order = 1)
    public void beforeFeature() {
        logger.info("Starting feature");
    }

    @After(order = 1)
    public void afterFeature() {
        logger.info("Feature completed");
    }

    @Before(order = 2)
    public void beforeScenario(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        loginWithDefaultCredentials();
    }

    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("FAILED: {}", scenario.getName());
            takeScreenshot("FAILURE_" + scenario.getName() + "_");
        } else {
            takeScreenshot("SUCCESS_" + scenario.getName() + "_");
        }
    }

}
