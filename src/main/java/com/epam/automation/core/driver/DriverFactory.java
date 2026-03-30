package com.epam.automation.core.driver;

import com.epam.automation.core.config.ConfigurationReader;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class DriverFactory {
    private static final ILogger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final String browser = ConfigurationReader.getProperty("browser");
    private static final BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());
    private static final boolean headlessMode = Boolean.parseBoolean(ConfigurationReader.getProperty("headless"));

    public static WebDriver createDriver() {
        WebDriver driver;

        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--start-maximized");
                if (headlessMode) {
                    chromeOptions.addArguments("--headless=new");
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

            case SAFARI:
                driver = new SafariDriver();
                logger.info("Safari driver initialized");
                break;

            default:
                throw new IllegalArgumentException("Browser type not supported");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        return driver;
    }
}
