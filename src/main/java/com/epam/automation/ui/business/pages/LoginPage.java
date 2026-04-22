package com.epam.automation.ui.business.pages;

import com.epam.automation.ui.core.base.BasePage;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import com.epam.automation.ui.core.utils.WaitUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.epam.automation.ui.business.pages.DashboardPage.DEFAULT_DASHBOARD_URL;
import static com.epam.automation.ui.core.utils.ElementUtil.click;
import static com.epam.automation.ui.core.utils.ElementUtil.sendText;

public class LoginPage extends BasePage {
    private static final ILogger logger = LoggerFactory.getLogger(LoginPage.class);

    @FindBy(name = "login")
    private WebElement userNameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type=submit]")
    private WebElement loginButton;

    public LoginPage() {
        super();
    }

    private LoginPage enterUserName(String userName) {
        logger.logStep("Entering username");
        sendText(userNameField, userName);
        return new LoginPage();
    }

    private void enterPassword(String password) {
        logger.logStep("Entering password");
        sendText(passwordField, password);
    }

    public void clickLoginButton() {
        logger.logStep("Clicking login button");
        click(loginButton);
    }

    public void waitForLoginSuccess() {
        logger.logStep("Waiting for navigation away from login page");
        WaitUtil.waitForUrlContains(DEFAULT_DASHBOARD_URL);
        logger.info("Navigated away from login page");
    }

    public void enterCredentials(String username, String password) {
        enterUserName(username)
                .enterPassword(password);
    }
}
