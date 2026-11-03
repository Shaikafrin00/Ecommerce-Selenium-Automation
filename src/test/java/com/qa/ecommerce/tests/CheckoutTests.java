package com.qa.ecommerce.tests;

import com.qa.ecommerce.pages.CartPage;
import com.qa.ecommerce.pages.CheckoutPage;
import com.qa.ecommerce.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {
    @Test(description = "A customer can complete checkout with valid information")
    public void userCanCompleteCheckout() {
        InventoryPage inventoryPage = loginAsStandardUser();
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        CartPage cartPage = inventoryPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1);

        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        checkoutPage.enterCustomerInformation("Alex", "Tester", "560001");
        checkoutPage.finishOrder();

        Assert.assertEquals(checkoutPage.getConfirmationMessage(), "Thank you for your order!");
    }

    @Test(description = "Checkout information requires a first name")
    public void checkoutRequiresFirstName() {
        InventoryPage inventoryPage = loginAsStandardUser();
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        CheckoutPage checkoutPage = inventoryPage.openCart().proceedToCheckout();
        checkoutPage.enterCustomerInformation("", "Tester", "560001");

        Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: First Name is required");
    }
}
