package com.epam.automation.ui.core.driver;

import com.epam.automation.common.core.config.ConfigurationReader;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
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

import static com.epam.automation.common.core.config.ConfigurationReader.getImplicitWait;
import static com.epam.automation.common.core.config.ConfigurationReader.getPageLoadTimeout;

public class DriverFactory {
    private static final ILogger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static WebDriver createDriver() {
        String browser = ConfigurationReader.getProperty("browser");
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());
        boolean headlessMode = Boolean.parseBoolean(ConfigurationReader.getProperty("headless"));
        ExecutionMode executionMode = ExecutionMode.fromString(ConfigurationReader.getExecutionMode());

        logger.info("Creating driver: browser={}, headless={}, executionMode={}",
                browserType, headlessMode, executionMode);

        WebDriver driver = switch (executionMode) {
            case GRID -> initializeRemoteDriver(browserType, headlessMode);
            case LOCAL, BROWSERSTACK -> initializeBrowser(browserType, headlessMode);
        };
        configureDriver(driver, headlessMode);
        return driver;
    }

    private static WebDriver initializeBrowser(BrowserType browserType, boolean headlessMode) {
        WebDriver driver;
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(buildChromeOptions(headlessMode));
                logger.info("Chrome driver initialized");
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(buildFirefoxOptions(headlessMode));
                logger.info("Firefox driver initialized");
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(buildEdgeOptions(headlessMode));
                logger.info("Edge driver initialized");
                break;

            case SAFARI:
                driver = new SafariDriver();
                logger.info("Safari driver initialized");
                break;

            default:
                throw new IllegalArgumentException("Browser type not supported");
        }
        return driver;
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

    private static ChromeOptions buildChromeOptions(boolean headlessMode) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
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
