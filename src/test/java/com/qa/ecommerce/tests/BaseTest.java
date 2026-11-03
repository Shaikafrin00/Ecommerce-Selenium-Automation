package com.qa.ecommerce.tests;

import com.qa.ecommerce.config.ConfigReader;
import com.qa.ecommerce.driver.DriverFactory;
import com.qa.ecommerce.pages.LoginPage;
import com.qa.ecommerce.pages.InventoryPage;
import com.qa.ecommerce.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.ITestResult;
import com.qa.ecommerce.utils.ScreenshotUtils;

/** Common test lifecycle: browser setup, application navigation and cleanup. */
public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void initializeReporting() {
        ExtentReportManager.getInstance();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(ConfigReader.get("base.url"));
    }

    protected InventoryPage loginAsStandardUser() {
        new LoginPage(driver).loginAs(
                ConfigReader.get("standard.username"), ConfigReader.get("standard.password"));
        return new InventoryPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Capture evidence before quitting the browser so failure screenshots are reliable.
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            String screenshotPath = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
            if (screenshotPath != null) {
                result.setAttribute("failureScreenshot", screenshotPath);
            }
        }
        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void closeReporting() {
        ExtentReportManager.flush();
    }
}
