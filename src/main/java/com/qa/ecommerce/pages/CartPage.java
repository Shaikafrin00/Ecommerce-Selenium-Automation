package com.qa.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Page object for the shopping cart. */
public class CartPage extends BasePage {
    private static final By CART_CONTAINER = By.id("cart_contents_container");
    private static final By CART_ITEMS = By.cssSelector(".cart_item");
    private static final By CART_ITEM_NAMES = By.cssSelector(".cart_item .inventory_item_name");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CHECKOUT_FIRST_NAME = By.id("first-name");

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

    /**
     * Scrolls the checkout control into the viewport, clicks it, and waits until
     * the checkout information form is ready before returning its page object.
     */
    public CheckoutPage proceedToCheckout() {
        WebElement checkoutButton = waits.clickable(CHECKOUT_BUTTON);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", checkoutButton);
        new Actions(driver).moveToElement(checkoutButton)
                .pause(Duration.ofMillis(150)).click().perform();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("checkout-step-one"));
        waits.visible(CHECKOUT_FIRST_NAME);
        return new CheckoutPage(driver);
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
