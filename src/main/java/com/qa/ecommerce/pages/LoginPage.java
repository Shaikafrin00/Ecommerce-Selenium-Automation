package com.qa.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page object for SauceDemo's login screen. */
public class LoginPage extends BasePage {
    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage loginAs(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
        return this;
    }

    public String getErrorMessage() {
        return textOf(ERROR_MESSAGE);
    }

    public boolean isErrorDisplayed() {
        return waits.visible(ERROR_MESSAGE).isDisplayed();
    }
}
