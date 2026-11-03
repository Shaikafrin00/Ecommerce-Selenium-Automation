package com.qa.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.List;

/** Page object for the product inventory and sorting controls. */
public class InventoryPage extends BasePage {
    private static final By PAGE_TITLE = By.cssSelector(".title");
    private static final By INVENTORY_ITEMS = By.cssSelector(".inventory_item");
    private static final By PRODUCT_NAMES = By.cssSelector(".inventory_item_name");
    private static final By PRODUCT_PRICES = By.cssSelector(".inventory_item_price");
    private static final By SORT_DROPDOWN = By.className("product_sort_container");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return textOf(PAGE_TITLE);
    }

    public int getProductCount() {
        waits.visible(INVENTORY_ITEMS);
        return driver.findElements(INVENTORY_ITEMS).size();
    }

    public List<String> getProductNames() {
        waits.visible(PRODUCT_NAMES);
        return driver.findElements(PRODUCT_NAMES).stream().map(WebElement::getText).toList();
    }

    public void addProductToCart(String productName) {
        String xpath = "//div[contains(concat(' ', normalize-space(@class), ' '), ' inventory_item ')"
                + "][.//*[contains(concat(' ', normalize-space(@class), ' '), ' inventory_item_name ')"
                + " and normalize-space(.)=" + xpathLiteral(productName) + "]]"
                + "//button[contains(concat(' ', normalize-space(@class), ' '), ' btn_inventory ')]";
        click(By.xpath(xpath));
    }

    public void removeProductFromCart(String productName) {
        String xpath = "//div[contains(concat(' ', normalize-space(@class), ' '), ' inventory_item ')"
                + "][.//*[contains(concat(' ', normalize-space(@class), ' '), ' inventory_item_name ')"
                + " and normalize-space(.)=" + xpathLiteral(productName) + "]]"
                + "//button[contains(concat(' ', normalize-space(@class), ' '), ' btn_inventory ')]";
        click(By.xpath(xpath));
    }

    public int getCartBadgeCount() {
        if (driver.findElements(CART_BADGE).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(driver.findElement(CART_BADGE).getText().trim());
    }

    public void sortByPriceLowToHigh() {
        waits.visible(SORT_DROPDOWN);
        new Select(driver.findElement(SORT_DROPDOWN)).selectByValue("lohi");
    }

    public List<BigDecimal> getProductPrices() {
        waits.visible(PRODUCT_PRICES);
        return driver.findElements(PRODUCT_PRICES).stream()
                .map(WebElement::getText)
                .map(price -> new BigDecimal(price.replace("$", "").trim()))
                .toList();
    }

    public CartPage openCart() {
        clickAndWaitForUrl(CART_LINK, "cart.html");
        return new CartPage(driver);
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'", -1);
        return "concat('" + String.join("', \"'\", '", parts) + "')";
    }
}
