package com.qa.ecommerce.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Saves failure screenshots under target/screenshots and returns the saved path. */
public final class ScreenshotUtils {
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String testName) {
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            return null;
        }

        try {
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            String safeName = testName.replaceAll("[^a-zA-Z0-9-_]", "_");
            Path destination = directory.resolve(safeName + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png");
            Files.copy(screenshotDriver.getScreenshotAs(OutputType.FILE).toPath(), destination,
                    StandardCopyOption.REPLACE_EXISTING);
            return destination.toAbsolutePath().toString();
        } catch (IOException | RuntimeException exception) {
            System.err.println("Unable to capture screenshot: " + exception.getMessage());
            return null;
        }
    }
}
