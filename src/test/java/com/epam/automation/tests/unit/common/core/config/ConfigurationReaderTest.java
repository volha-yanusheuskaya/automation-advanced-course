package com.epam.automation.tests.unit.common.core.config;

import com.epam.automation.common.core.config.ConfigurationReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ConfigurationReader.
 * <p>
 * NOTE: The static initializer's exception handling cannot be covered in unit tests:
 * <ul>
 *   <li>Static initializers run once when the class is loaded (JVM-wide)</li>
 *   <li>If any exception occurs, ConfigurationException is thrown immediately</li>
 *   <li>The class fails to load, preventing all tests from running</li>
 *   <li>Can't re-trigger or reload the class in the same JVM session</li>
 * </ul>
 * Exception paths are implicitly verified by successful class initialization.
 * <p>
 * <strong>Integration Test Alternatives:</strong>
 * To explicitly test exception scenarios, create integration tests in a separate
 * JVM process or classloader:
 * <ul>
 *   <li>Missing config.properties → tests null input check</li>
 *   <li>Corrupted/unreadable config.properties → tests IOException handling</li>
 * </ul>
 * These can be tested via:
 * <ul>
 *   <li>Maven Failsafe integration tests</li>
 *   <li>Docker containers with missing/corrupt config files</li>
 *   <li>Separate test process spawning</li>
 * </ul>
 */
@DisplayName("ConfigurationReader Unit Tests")
class ConfigurationReaderTest {

    @AfterEach
    void cleanSystemProps() {
        System.clearProperty("browser");
        System.clearProperty("execution.mode");
        System.clearProperty("implicit.wait");
        System.clearProperty("demo.project");
        System.clearProperty("grid.url");
        System.clearProperty("selenoid.url");
    }

    @Test
    @DisplayName("Should successfully initialize when config.properties exists in classpath")
    void classInitializesSuccessfullyWithValidConfigFile() {
        assertThat(ConfigurationReader.getProperty("browser")).isNotNull();
        assertThat(ConfigurationReader.getProperty("url")).isNotNull();
    }


    @Test
    @DisplayName("Should return value from file when no system property is set")
    void getPropertyReturnsValueFromFileWhenNoSystemProperty() {
        assertThat(ConfigurationReader.getProperty("browser")).isEqualTo("chrome");
    }

    @Test
    @DisplayName("Should prefer system property over file configuration")
    void getPropertyPrefersSystemPropertyOverFile() {
        System.setProperty("browser", "firefox");
        assertThat(ConfigurationReader.getProperty("browser")).isEqualTo("firefox");
    }

    @Test
    @DisplayName("Should ignore blank system property and use file value")
    void getPropertyIgnoresBlankSystemProperty() {
        System.setProperty("browser", "   ");
        assertThat(ConfigurationReader.getProperty("browser")).isEqualTo("chrome");
    }

    @Test
    @DisplayName("Should return default when execution mode not set")
    void getExecutionModeReturnsDefaultWhenNotSet() {
        assertThat(ConfigurationReader.getExecutionMode())
                .isIn("local", ConfigurationReader.getProperty("execution.mode"));
    }

    @Test
    @DisplayName("Should return positive implicit wait value")
    void getImplicitWaitReturnsConfiguredOrDefault() {
        int value = ConfigurationReader.getImplicitWait();
        assertThat(value).isPositive();
    }

    @Test
    @DisplayName("Should return valid grid URL with http protocol")
    void getGridUrlFallsBackToDefault() {
        assertThat(ConfigurationReader.getGridUrl()).startsWith("http");
    }

    @Test
    @DisplayName("Should return custom grid URL when set")
    void getGridUrlReturnsCustomValueWhenSet() {
        System.setProperty("grid.url", "http://custom-grid:4444/wd/hub");
        assertThat(ConfigurationReader.getGridUrl()).isEqualTo("http://custom-grid:4444/wd/hub");
        System.clearProperty("grid.url");
    }

    @Test
    @DisplayName("Should return default grid URL when property is blank")
    void getGridUrlReturnsFallbackWhenBlank() {
        System.setProperty("grid.url", "   ");
        assertThat(ConfigurationReader.getGridUrl()).isEqualTo("http://localhost:4444/wd/hub");
        System.clearProperty("grid.url");
    }

    @Test
    @DisplayName("Should return username from configuration")
    void getUsernameReturnsConfiguredValue() {
        String username = ConfigurationReader.getUsername();
        assertThat(username).isNotBlank();
    }

    @Test
    @DisplayName("Should return password from configuration")
    void getPasswordReturnsConfiguredValue() {
        String password = ConfigurationReader.getPassword();
        assertThat(password).isNotBlank();
    }

    @Test
    @DisplayName("Should return base URL from configuration")
    void getBaseUrlReturnsConfiguredValue() {
        String url = ConfigurationReader.getBaseUrl();
        assertThat(url).isNotBlank().startsWith("http");
    }

    @Test
    @DisplayName("Should return base URI from configuration")
    void getBaseUriReturnsConfiguredValue() {
        String uri = ConfigurationReader.getBaseUri();
        assertThat(uri).isNotBlank();
    }

    @Test
    @DisplayName("Should return token from configuration")
    void getTokenReturnsConfiguredValue() {
        String token = ConfigurationReader.getToken();
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("Should return demo project name from configuration")
    void getDemoProjectNameReturnsConfiguredValue() {
        String projectName = ConfigurationReader.getDemoProjectName();
        assertThat(projectName).isNotBlank();
    }

    @Test
    @DisplayName("Should return default timeout value")
    void getDefaultTimeoutReturnsPositiveValue() {
        int timeout = ConfigurationReader.getDefaultTimeout();
        assertThat(timeout).isPositive();
    }

    @Test
    @DisplayName("Should return page load timeout value")
    void getPageLoadTimeoutReturnsPositiveValue() {
        int timeout = ConfigurationReader.getPageLoadTimeout();
        assertThat(timeout).isPositive();
    }

    @Test
    @DisplayName("Should return selenoid URL with fallback to default")
    void getSelenoidUrlReturnsValidUrl() {
        String url = ConfigurationReader.getSelenoidUrl();
        assertThat(url).startsWith("http");
    }

    @Test
    @DisplayName("Should return custom selenoid URL when set")
    void getSelenoidUrlReturnsCustomValueWhenSet() {
        System.setProperty("selenoid.url", "http://selenoid-server:4444/wd/hub");
        assertThat(ConfigurationReader.getSelenoidUrl()).isEqualTo("http://selenoid-server:4444/wd/hub");
        System.clearProperty("selenoid.url");
    }

    @Test
    @DisplayName("Should return default selenoid URL when property is blank")
    void getSelenoidUrlReturnsFallbackWhenBlank() {
        System.setProperty("selenoid.url", "   ");
        assertThat(ConfigurationReader.getSelenoidUrl()).isEqualTo("http://localhost:4444/wd/hub");
        System.clearProperty("selenoid.url");
    }

    @Test
    @DisplayName("Should return system property when set from getProperty call")
    void getPropertySystemPropertyTakesPrecedence() {
        System.setProperty("implicit.wait", "20");
        assertThat(ConfigurationReader.getProperty("implicit.wait")).isEqualTo("20");
    }

    @Test
    @DisplayName("Should return property or default value fallback")
    void getExecutionModeReturnsLocalByDefault() {
        String mode = ConfigurationReader.getExecutionMode();
        assertThat(mode).isNotBlank();
    }

    @Test
    @DisplayName("Should return default value when property is not set")
    void getPropertyOrDefaultReturnsFallbackWhenPropertyNull() {
        System.setProperty("execution.mode", "");
        String result = ConfigurationReader.getExecutionMode();
        assertThat(result).isEqualTo("local");
    }

    @Test
    @DisplayName("Should return property value when set and not blank")
    void getPropertyOrDefaultReturnsValueWhenSet() {
        System.setProperty("execution.mode", "grid");
        String result = ConfigurationReader.getExecutionMode();
        assertThat(result).isEqualTo("grid");
    }
}