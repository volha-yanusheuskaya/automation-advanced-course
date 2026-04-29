package com.epam.automation.common.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
    private static final Logger logger = LogManager.getLogger(ConfigurationReader.class);
    private static final Properties properties;

    static {
        try (InputStream input = ConfigurationReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                logger.error("Can't find config.properties file");
                throw new RuntimeException("config.properties file not found in classpath");
            }

            properties = new Properties();
            properties.load(input);
            logger.info("Configuration loaded successfully...");
        } catch (IOException e) {
            logger.error("Failed to load config.properties: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load config.properties");
        }
    }

    public static String getProperty(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }
        return properties.getProperty(key);
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static String getBaseUrl() {
        return properties.getProperty("url");
    }

    public static String getBaseUri() {
        return properties.getProperty("uri");
    }

    public static String getToken() {
        return properties.getProperty("token");
    }

    public static String getDemoProjectName() {
        return properties.getProperty("demo.project");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getDefaultTimeout() {
        return Integer.parseInt(properties.getProperty("default.timeout", "30"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "45"));
    }

    public static String getExecutionMode() {
        String sysProp = System.getProperty("execution.mode");
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }
        return properties.getProperty("execution.mode", "local");
    }

    public static String getGridUrl() {
        String sysProp = System.getProperty("grid.url");
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }
        return properties.getProperty("grid.url", "http://localhost:4444/wd/hub");
    }
}
