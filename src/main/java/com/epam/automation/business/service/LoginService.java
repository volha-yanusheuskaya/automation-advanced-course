package com.epam.automation.business.service;

import com.epam.automation.business.models.User;
import com.epam.automation.business.pages.DashboardPage;
import com.epam.automation.business.pages.LoginPage;

public class LoginService {
    private final LoginPage loginPage;

    public LoginService(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public DashboardPage loginAs(User user) {
        loginPage.enterCredentials(user.getUsername(), user.getPassword());
        loginPage.clickLoginButton();
        loginPage.waitForLoginSuccess();
        return new DashboardPage();
    }
}
