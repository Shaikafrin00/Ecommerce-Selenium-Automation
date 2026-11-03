package com.qa.ecommerce.tests;

import com.qa.ecommerce.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class ProductTests extends BaseTest {
    @Test(description = "The inventory page displays the expected six products")
    public void inventoryDisplaysSixProducts() {
        InventoryPage inventoryPage = loginAsStandardUser();
        Assert.assertEquals(inventoryPage.getProductCount(), 6,
                "The standard user should see six products in the demo inventory.");
        Assert.assertEquals(inventoryPage.getProductNames().size(), 6);
    }

    @Test(description = "Products can be sorted by price from low to high")
    public void productsCanBeSortedByPriceAscending() {
        InventoryPage inventoryPage = loginAsStandardUser();
        inventoryPage.sortByPriceLowToHigh();
        List<BigDecimal> prices = inventoryPage.getProductPrices();

        List<BigDecimal> sortedPrices = prices.stream().sorted().toList();
        Assert.assertEquals(prices, sortedPrices,
                "Displayed prices should be in ascending order after selecting Price (low to high).");
    }
}
