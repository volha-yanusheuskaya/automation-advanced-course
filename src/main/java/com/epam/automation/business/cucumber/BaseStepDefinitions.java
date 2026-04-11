package com.epam.automation.business.cucumber;

import com.epam.automation.core.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.epam.automation.core.config.ConfigurationReader.getDefaultTimeout;

public class BaseStepDefinitions {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(getDefaultTimeout()));
        PageFactory.initElements(driver, this);
    }
}
