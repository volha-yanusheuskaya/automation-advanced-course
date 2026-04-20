package com.epam.automation.ui.core.driver;

import com.epam.automation.common.core.config.ConfigurationReader;
import com.epam.automation.common.core.logger.ILogger;
import com.epam.automation.common.core.logger.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

import static com.epam.automation.common.core.config.ConfigurationReader.getImplicitWait;
import static com.epam.automation.common.core.config.ConfigurationReader.getPageLoadTimeout;

public class DriverFactory {
    private static final ILogger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static WebDriver createDriver() {
        String browser = ConfigurationReader.getProperty("browser");
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());
        boolean headlessMode = Boolean.parseBoolean(ConfigurationReader.getProperty("headless"));

        WebDriver driver = initializeBrowser(browserType, headlessMode);
        configureDriver(driver, headlessMode);
        return driver;
    }

    private static WebDriver initializeBrowser(BrowserType browserType, boolean headlessMode) {
        WebDriver driver;
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                if (headlessMode) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1200");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                driver = new ChromeDriver(chromeOptions);
                logger.info("Chrome driver initialized");
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headlessMode) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("Firefox driver initialized");
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headlessMode) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
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

    private static void configureDriver(WebDriver driver, boolean headlessMode) {
        int implicitWait = getImplicitWait();
        int pageLoadTimeout = getPageLoadTimeout();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        if (!headlessMode) {
            driver.manage().window().maximize();
        }

        logger.info("Driver configured with timeouts: implicit={}, pageLoad={}", implicitWait, pageLoadTimeout);
    }
}
