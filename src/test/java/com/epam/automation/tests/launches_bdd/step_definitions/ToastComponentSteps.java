package com.epam.automation.tests.launches_bdd.step_definitions;

import com.epam.automation.business.components.ToastComponent;
import com.epam.automation.business.cucumber.BaseStepDefinitions;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class ToastComponentSteps extends BaseStepDefinitions {

    @Then("Toast component should be displayed with message {string}")
    public void toastComponentShouldBeDisplayedWithMessage(String message) {
        ToastComponent toastComponent = new ToastComponent();

        toastComponent.isToastComponentDisplayed();
        assertThat(toastComponent.getToastMessage())
                .as("Toast message is not correct")
                .isEqualTo(message);
    }
}
