package com.qa.ecommerce.tests;

import com.qa.ecommerce.pages.CartPage;
import com.qa.ecommerce.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {
    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";

    @Test(description = "A product can be added to the cart and removed again")
    public void userCanAddAndRemoveProducts() {
        InventoryPage inventoryPage = loginAsStandardUser();
        inventoryPage.addProductToCart(BACKPACK);
        inventoryPage.addProductToCart(BIKE_LIGHT);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 2,
                "The cart badge should reflect the number of selected products.");

        CartPage cartPage = inventoryPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 2);
        Assert.assertTrue(cartPage.getProductNames().contains(BACKPACK));

        cartPage.removeProduct(BACKPACK);
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Removing a product should update the cart contents.");
        Assert.assertFalse(cartPage.getProductNames().contains(BACKPACK));
    }

    @Test(description = "The cart can be opened when no products have been selected")
    public void emptyCartDisplaysNoProducts() {
        CartPage cartPage = loginAsStandardUser().openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 0,
                "A new session should start with an empty cart.");
    }
}
