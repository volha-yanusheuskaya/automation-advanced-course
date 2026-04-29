package com.epam.automation.ui.core.driver;

import com.epam.automation.common.core.config.ConfigurationReader;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;

import static com.epam.automation.common.core.config.ConfigurationReader.getImplicitWait;
import static com.epam.automation.common.core.config.ConfigurationReader.getPageLoadTimeout;

public class DriverFactory {
    private static final ILogger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final String BROWSERSTACK_HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    public static WebDriver createDriver() {
        String browser = ConfigurationReader.getProperty("browser");
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());
        boolean headlessMode = Boolean.parseBoolean(ConfigurationReader.getProperty("headless"));
        ExecutionMode executionMode = ExecutionMode.fromString(ConfigurationReader.getExecutionMode());

        logger.info("Creating driver: browser={}, headless={}, executionMode={}",
                browserType, headlessMode, executionMode);

        WebDriver driver = switch (executionMode) {
            case GRID -> initializeRemoteDriver(browserType, headlessMode);
            case SELENOID -> initializeSelenoidDriver(browserType, headlessMode);
            case BROWSERSTACK -> initializeBrowserStackDriver(browserType, headlessMode);
            case LOCAL -> initializeBrowser(browserType, headlessMode);
        };
        configureDriver(driver, headlessMode);
        return driver;
    }

    private static WebDriver initializeBrowser(BrowserType browserType, boolean headlessMode) {
        return switch (browserType) {
            case CHROME -> new ChromeDriver(buildChromeOptions(headlessMode));
            case FIREFOX -> new FirefoxDriver(buildFirefoxOptions(headlessMode));
            case EDGE -> new EdgeDriver(buildEdgeOptions(headlessMode));
            case SAFARI -> new SafariDriver();
        };
    }

    private static WebDriver initializeRemoteDriver(BrowserType browserType, boolean headlessMode) {
        String gridUrl = ConfigurationReader.getGridUrl();
        MutableCapabilities options = switch (browserType) {
            case CHROME -> buildChromeOptions(headlessMode);
            case FIREFOX -> buildFirefoxOptions(headlessMode);
            case EDGE -> buildEdgeOptions(headlessMode);
            case SAFARI -> new SafariOptions();
        };

        try {
            RemoteWebDriver driver = new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
            driver.setFileDetector(new org.openqa.selenium.remote.LocalFileDetector());
            logger.info("Remote {} driver initialized against grid {}", browserType, gridUrl);
            return driver;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Selenium Grid URL: " + gridUrl, e);
        }
    }

    private static WebDriver initializeSelenoidDriver(BrowserType browserType, boolean headlessMode) {
        String selenoidUrl = ConfigurationReader.getSelenoidUrl();
        MutableCapabilities browserOptions = switch (browserType) {
            case CHROME -> buildChromeOptions(headlessMode);
            case FIREFOX -> buildFirefoxOptions(headlessMode);
            case EDGE -> buildEdgeOptions(headlessMode);
            case SAFARI -> new SafariOptions();
        };

        HashMap<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", true);
        selenoidOptions.put("enableLog", true);
        selenoidOptions.put("sessionTimeout", "3m");
        browserOptions.setCapability("selenoid:options", selenoidOptions);

        try {
            RemoteWebDriver driver = new RemoteWebDriver(URI.create(selenoidUrl).toURL(), browserOptions);
            driver.setFileDetector(new org.openqa.selenium.remote.LocalFileDetector());
            logger.info("Selenoid {} driver initialized against {}", browserType, selenoidUrl);
            return driver;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Selenoid URL: " + selenoidUrl, e);
        }
    }

    private static WebDriver initializeBrowserStackDriver(BrowserType browserType, boolean headlessMode) {
        MutableCapabilities browserOptions = switch (browserType) {
            case CHROME -> buildChromeOptions(headlessMode);
            case FIREFOX -> buildFirefoxOptions(headlessMode);
            case EDGE -> buildEdgeOptions(headlessMode);
            case SAFARI -> new SafariOptions();
        };

        if (isBrowserStackSdkActive()) {
            try {
                RemoteWebDriver driver = new RemoteWebDriver(URI.create(BROWSERSTACK_HUB_URL).toURL(), browserOptions);
                driver.setFileDetector(new org.openqa.selenium.remote.LocalFileDetector());
                logger.info("BrowserStack {} driver initialized via SDK agent", browserType);
                return driver;
            } catch (MalformedURLException e) {
                throw new IllegalStateException("Invalid BrowserStack hub URL: " + BROWSERSTACK_HUB_URL, e);
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

        if (username == null || accessKey == null) {
            throw new IllegalStateException(
                    "BrowserStack credentials not set. " +
                    "Set BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY environment variables.");
        }

        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", username);
        bstackOptions.put("accessKey", accessKey);
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("browserVersion", "latest");
        bstackOptions.put("projectName", "BrowserStack Advanced Course");
        bstackOptions.put("buildName", "bstack-demo");
        bstackOptions.put("seleniumVersion", "4.41.0");
        bstackOptions.put("debug", "true");
        bstackOptions.put("networkLogs", "true");
        bstackOptions.put("consoleLogs", "info");

        browserOptions.setCapability("bstack:options", bstackOptions);

        try {
            RemoteWebDriver driver = new RemoteWebDriver(URI.create(BROWSERSTACK_HUB_URL).toURL(), browserOptions);
            driver.setFileDetector(new org.openqa.selenium.remote.LocalFileDetector());
            logger.info("BrowserStack {} driver initialized with manual bstack:options", browserType);
            return driver;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid BrowserStack hub URL: " + BROWSERSTACK_HUB_URL, e);
        }
    }

    private static boolean isBrowserStackSdkActive() {
        try {
            Class.forName("com.browserstack.BrowserStackSDK");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static ChromeOptions buildChromeOptions(boolean headlessMode) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        if (headlessMode) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--window-size=1920,1200");
        } else {
            chromeOptions.addArguments("--start-maximized");
        }
        return chromeOptions;
    }

    private static FirefoxOptions buildFirefoxOptions(boolean headlessMode) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (headlessMode) {
            firefoxOptions.addArguments("--headless");
        }
        return firefoxOptions;
    }

    private static EdgeOptions buildEdgeOptions(boolean headlessMode) {
        EdgeOptions edgeOptions = new EdgeOptions();
        if (headlessMode) {
            edgeOptions.addArguments("--headless=new");
        }
        return edgeOptions;
    }

    private static void configureDriver(WebDriver driver, boolean headlessMode) {
        int implicitWait = getImplicitWait();
        int pageLoadTimeout = getPageLoadTimeout();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        if (!headlessMode) {
            try {
                driver.manage().window().maximize();
            } catch (Exception e) {
                logger.warn("window().maximize() not supported on this platform: {}", e.getMessage());
            }
        }

        logger.info("Driver configured with timeouts: implicit={}, pageLoad={}", implicitWait, pageLoadTimeout);
    }
}
