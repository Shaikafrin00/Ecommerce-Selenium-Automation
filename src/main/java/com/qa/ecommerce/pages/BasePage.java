package com.qa.ecommerce.pages;

import com.qa.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
     * Performs a native Selenium click, then verifies the expected UI state.
     * When the native click does not produce that state, retries the element's
     * click handler through JavaScript. The condition must still become true.
     */
    protected void clickAndWaitForUrl(By locator, String urlFragment) {
        clickAndWait(locator, ExpectedConditions.urlContains(urlFragment));
    }

    protected void clickAndWaitForElement(By clickLocator, By expectedLocator) {
        clickAndWait(clickLocator, ExpectedConditions.visibilityOfElementLocated(expectedLocator));
    }

    protected void clickAndWaitForElementCount(By clickLocator, By observedLocator, int expectedCount) {
        clickAndWait(clickLocator, ExpectedConditions.numberOfElementsToBe(observedLocator, expectedCount));
    }

    private void clickAndWait(By locator, ExpectedCondition<?> successCondition) {
        WebElement element = waits.clickable(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        try {
            element.click();
            new WebDriverWait(driver, Duration.ofSeconds(4)).until(successCondition);
            return;
        } catch (WebDriverException nativeClickFailure) {
            // Retry below only because the expected state was not reached.
        }

        WebElement retryElement = waits.clickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryElement);
        new WebDriverWait(driver, Duration.ofSeconds(12)).until(successCondition);
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
