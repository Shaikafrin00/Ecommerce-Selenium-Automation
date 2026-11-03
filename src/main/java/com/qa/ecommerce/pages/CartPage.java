package com.qa.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Page object for the shopping cart. */
public class CartPage extends BasePage {
    private static final By CART_CONTAINER = By.id("cart_contents_container");
    private static final By CART_ITEMS = By.cssSelector(".cart_item");
    private static final By CART_ITEM_NAMES = By.cssSelector(".cart_item .inventory_item_name");
    private static final By CHECKOUT_BUTTON = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        waits.visible(CART_CONTAINER);
        return driver.findElements(CART_ITEMS).size();
    }

    public List<String> getProductNames() {
        return driver.findElements(CART_ITEM_NAMES).stream().map(WebElement::getText).toList();
    }

    public void removeProduct(String productName) {
        String xpath = "//div[contains(@class,'cart_item')][.//div[@class='inventory_item_name' and normalize-space()="
                + xpathLiteral(productName) + "]]//button";
        click(By.xpath(xpath));
    }

    public CheckoutPage proceedToCheckout() {
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
