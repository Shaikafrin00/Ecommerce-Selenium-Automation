package com.qa.ecommerce.pages;

import com.qa.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    /**
     * Uses a native Selenium click first. If the expected page transition does not
     * occur promptly, retries via the element's JavaScript click handler and still
     * requires the destination URL before continuing.
     */
    protected void clickAndWaitForUrl(By locator, String urlFragment) {
        WebElement element = waits.clickable(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        try {
            element.click();
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.urlContains(urlFragment));
            return;
        } catch (TimeoutException ignored) {
            // The native click did not navigate; retry through the element click handler.
        }

        WebElement retryElement = waits.clickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryElement);
        new WebDriverWait(driver, Duration.ofSeconds(12))
                .until(ExpectedConditions.urlContains(urlFragment));
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
