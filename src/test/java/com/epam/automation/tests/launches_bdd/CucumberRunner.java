package com.epam.automation.tests.launches_bdd;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

@Suite(failIfNoTests = false)
@IncludeEngines("cucumber")
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "com.epam.automation.tests.launches_bdd.step_definitions,com.epam.automation.tests.launches_bdd.base"
)
@ConfigurationParameter(
        key = Constants.FEATURES_PROPERTY_NAME,
        value = "classpath:launches_features"
)
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty,json:target/cucumber-report.json"
)
public class CucumberRunner {
}
