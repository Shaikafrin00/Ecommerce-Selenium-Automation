package com.qa.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page object for checkout information, order overview and confirmation screens. */
public class CheckoutPage extends BasePage {
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By COMPLETE_HEADER = By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage enterCustomerInformation(String firstName, String lastName, String postalCode) {
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(POSTAL_CODE, postalCode);
        click(CONTINUE_BUTTON);
        return this;
    }

    public CheckoutPage finishOrder() {
        click(FINISH_BUTTON);
        return this;
    }

    public String getErrorMessage() {
        return textOf(ERROR_MESSAGE);
    }

    public String getConfirmationMessage() {
        return textOf(COMPLETE_HEADER);
    }
}
