package com.qa.ecommerce.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;

/** Singleton owner for the HTML execution report. */
public final class ExtentReportManager {
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            try {
                Path reportDirectory = Path.of("target", "extent-reports");
                Files.createDirectories(reportDirectory);
                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
                        reportDirectory.resolve("SparkReport.html").toString());
                sparkReporter.config().setDocumentTitle("E-Commerce UI Automation Results");
                sparkReporter.config().setReportName("SauceDemo Regression Suite");
                extentReports = new ExtentReports();
                extentReports.attachReporter(sparkReporter);
                extentReports.setSystemInfo("Framework", "Selenium + TestNG + Maven");
                extentReports.setSystemInfo("Application", "SauceDemo");
                extentReports.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
            } catch (Exception exception) {
                throw new IllegalStateException("Could not initialize the Extent HTML report.", exception);
            }
        }
        return extentReports;
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
            extentReports = null;
        }
    }
}
