package com.epam.automation.tests.launches_bdd.step_definitions;

import com.epam.automation.business.cucumber.BaseStepDefinitions;
import com.epam.automation.business.models.User;
import com.epam.automation.business.pages.LoginPage;
import com.epam.automation.business.service.LoginService;
import io.cucumber.java.en.When;

public class LoginSteps extends BaseStepDefinitions {

    @When("User login with default credentials")
    public void loginWithDefaultCredentials() {
        LoginService loginService = new LoginService(new LoginPage());
        loginService.loginAs(User.defaultUser());
    }
}
