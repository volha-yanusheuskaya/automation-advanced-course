package com.epam.automation.business.pages;

import com.epam.automation.core.base.BasePage;
import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.epam.automation.core.utils.ElementUtil.click;
import static com.epam.automation.core.utils.ElementUtil.sendText;

public class LoginPage extends BasePage {
    private static final ILogger logger = LoggerFactory.getLogger(LoginPage.class);

    @FindBy(name = "login")
    private WebElement userNameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type=submit]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private void enterUserName() {
        logger.logStep("Entering username");
        sendText(userNameField, ConfigurationReader.getUsername());
    }

    private void enterPassword() {
        logger.logStep("Entering password");
        sendText(passwordField, ConfigurationReader.getPassword());
    }

    private void clickLoginButton() {
        logger.logStep("Clicking login button");
        click(loginButton);
    }

    private void waitForLoginSuccess() {
        logger.logStep("Waiting for login to complete");
        wait.until(ExpectedConditions.not(
            ExpectedConditions.urlContains("#login")
        ));
        logger.info("Login successful - page navigated from login");
    }

    public void login() {
        enterUserName();
        enterPassword();
        clickLoginButton();
        waitForLoginSuccess();
    }
}
