package com.qa.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
        fillTextField(FIRST_NAME, firstName);
        fillTextField(LAST_NAME, lastName);
        fillTextField(POSTAL_CODE, postalCode);

        if (firstName == null || firstName.isBlank()) {
            clickAndWaitForElement(CONTINUE_BUTTON, ERROR_MESSAGE);
        } else {
            clickAndWaitForUrl(CONTINUE_BUTTON, "checkout-step-two");
            waits.visible(FINISH_BUTTON);
        }
        return this;
    }

    public CheckoutPage finishOrder() {
        clickAndWaitForElement(FINISH_BUTTON, COMPLETE_HEADER);
        return this;
    }

    public String getErrorMessage() {
        return textOf(ERROR_MESSAGE);
    }

    public String getConfirmationMessage() {
        return textOf(COMPLETE_HEADER);
    }

    private void fillTextField(By locator, String value) {
        String expectedValue = value == null ? "" : value;
        WebElement input = waits.visible(locator);
        input.click();
        input.clear();
        if (expectedValue.isEmpty()) {
            // Empty HTML inputs may omit the value attribute; clear() is sufficient.
            return;
        }

        input.sendKeys(expectedValue);

        // Verify the value reached the control; use the native input setter as a
        // fallback only if WebDriver did not populate it.
        if (!expectedValue.equals(input.getAttribute("value"))) {
            ((JavascriptExecutor) driver).executeScript(
                    "const input = arguments[0];"
                            + "const value = arguments[1];"
                            + "const setter = Object.getOwnPropertyDescriptor("
                            + "window.HTMLInputElement.prototype, 'value').set;"
                            + "setter.call(input, value);"
                            + "input.dispatchEvent(new Event('input', { bubbles: true }));"
                            + "input.dispatchEvent(new Event('change', { bubbles: true }));",
                    input, expectedValue);
        }

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.attributeToBe(locator, "value", expectedValue));
    }
}
