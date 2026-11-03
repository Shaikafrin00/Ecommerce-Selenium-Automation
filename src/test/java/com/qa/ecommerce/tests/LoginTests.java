package com.qa.ecommerce.tests;

import com.qa.ecommerce.pages.InventoryPage;
import com.qa.ecommerce.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @Test(description = "A valid standard user can sign in and see the product inventory")
    public void validUserCanLogin() {
        new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        Assert.assertEquals(new InventoryPage(driver).getPageTitle(), "Products",
                "The inventory page should be displayed after successful login.");
    }

    @Test(dataProvider = "invalidCredentials", description = "Invalid and incomplete credentials are rejected")
    public void invalidCredentialsShowValidationMessage(String username, String password, String expectedMessage) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "An authentication error should be displayed.");
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage);
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"invalid_user", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."}
        };
    }
}