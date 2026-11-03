package com.qa.ecommerce.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Loads framework settings from config.properties with system-property and environment overrides. */
public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties was not found on the classpath.");
            }
            PROPERTIES.load(input);
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String systemValue = System.getProperty(toSystemProperty(key));
        if (hasText(systemValue)) {
            return systemValue.trim();
        }

        String environmentValue = System.getenv(toEnvironmentVariable(key));
        if (hasText(environmentValue)) {
            return environmentValue.trim();
        }

        String value = PROPERTIES.getProperty(key);
        if (!hasText(value)) {
            throw new IllegalArgumentException("Missing required configuration value: " + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    private static String toSystemProperty(String key) {
        return switch (key) {
            case "base.url" -> "baseUrl";
            case "explicit.wait.seconds" -> "explicitWaitSeconds";
            case "page.load.timeout.seconds" -> "pageLoadTimeoutSeconds";
            case "standard.username" -> "username";
            case "standard.password" -> "password";
            default -> key;
        };
    }

    private static String toEnvironmentVariable(String key) {
        return switch (key) {
            case "base.url" -> "BASE_URL";
            case "browser" -> "BROWSER";
            case "headless" -> "HEADLESS";
            case "standard.username" -> "SAUCE_USERNAME";
            case "standard.password" -> "SAUCE_PASSWORD";
            default -> key.toUpperCase().replace('.', '_');
        };
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
