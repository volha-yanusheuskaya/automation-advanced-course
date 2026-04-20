package com.epam.automation.tests.ui.launches_bdd.step_definitions;

import com.epam.automation.ui.business.components.ToastComponent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class ToastComponentSteps {
    private final ScenarioContext scenarioContext;

    public ToastComponentSteps(ScenarioContext context) {
        this.scenarioContext = context;
    }

    @When("User closes the Toast component")
    public void userClosesTheToastComponent() {
        ToastComponent toastComponent = scenarioContext.getToastComponent();
        if (toastComponent != null && toastComponent.isToastComponentDisplayed()) {
            toastComponent.clickCloseToast();
        }
    }

    @Then("Toast component should be displayed with message {string}")
    public void toastComponentShouldBeDisplayedWithMessage(String message) {
        ToastComponent toastComponent = scenarioContext.getToastComponent();
        assertThat(toastComponent)
                .as("Toast component should not be null")
                .isNotNull();
        assertThat(toastComponent.isToastComponentDisplayed())
                .as("Toast component should be displayed")
                .isTrue();
        assertThat(toastComponent.getToastMessage())
                .as("Toast message is not correct")
                .isEqualTo(message);
    }
}
