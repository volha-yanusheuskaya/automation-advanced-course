package com.epam.automation.business.components;

import com.epam.automation.core.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.epam.automation.core.utils.ElementUtil.*;

public class ToastComponent extends BasePage {

    @FindBy(css = "div.notification-item")
    private WebElement toastComponent;

    @FindBy(css = "div.notification-item h2")
    private WebElement toastText;

    @FindBy(css = "div.notification-item button")
    private WebElement toastCloseButton;

    public ToastComponent() {
        super();
    }

    public boolean isToastComponentDisplayed() {
        return isDisplayed(toastComponent);
    }

    public String getToastMessage() {
        return getText(toastText);
    }

    public void clickCloseToast() {
        click(toastCloseButton);
    }

}
