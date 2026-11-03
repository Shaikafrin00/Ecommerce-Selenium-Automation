package com.qa.ecommerce.pages;

import com.qa.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Common page-object behavior used by all screen-specific page objects. */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils waits;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }

    protected void click(By locator) {
        waits.clickable(locator).click();
    }

    protected void type(By locator, String value) {
        WebElement element = waits.visible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String textOf(By locator) {
        return waits.visible(locator).getText().trim();
    }
}
