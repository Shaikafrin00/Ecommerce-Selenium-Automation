package com.qa.ecommerce.tests;

import com.aventstack.extentreports.ExtentTest;
import com.qa.ecommerce.driver.DriverFactory;
import com.qa.ecommerce.utils.ExtentReportManager;
import com.qa.ecommerce.utils.ScreenshotUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Bridges TestNG lifecycle events into an Extent Spark HTML report. */
public class ExtentTestListener implements ITestListener {
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTest test = ExtentReportManager.getInstance().createTest(testName);
        Object[] parameters = result.getParameters();
        if (parameters.length > 0) {
            test.info("Parameters: " + java.util.Arrays.toString(parameters));
        }
        CURRENT_TEST.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        currentTest().pass("Test passed");
        CURRENT_TEST.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = currentTest();
        test.fail(result.getThrowable());
        try {
            String screenshot = (String) result.getAttribute("failureScreenshot");
            if (screenshot == null) {
                screenshot = ScreenshotUtils.capture(DriverFactory.getDriver(),
                        result.getMethod().getMethodName());
            }
            if (screenshot != null) {
                test.addScreenCaptureFromPath(screenshot);
            }
        } catch (Exception exception) {
            test.warning("Screenshot was not available: " + exception.getMessage());
        } finally {
            CURRENT_TEST.remove();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() == null) {
            currentTest().skip("Test skipped");
        } else {
            currentTest().skip(result.getThrowable());
        }
        CURRENT_TEST.remove();
    }

    private ExtentTest currentTest() {
        ExtentTest test = CURRENT_TEST.get();
        if (test == null) {
            throw new IllegalStateException("No Extent test is active for the current thread.");
        }
        return test;
    }
}
