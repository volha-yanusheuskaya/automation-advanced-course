# Automation Advanced Course Framework

Production-ready test automation framework built with Java, Selenium, TestNG, and ReportPortal.

---

## Quick Start

### Prerequisites
- Java 21
- Maven 3.9+
- Git

### Setup (5 minutes)

1. Clone and navigate to project
```bash
git clone <repository-url>
cd automation-advanced-course
```

2. Configure test environment
```bash
cp src/test/resources/config.properties.example src/test/resources/config.properties
# Edit config.properties with your application URL and test credentials
```

3. Build project
```bash
mvn clean compile
```

4. Run tests
```bash
mvn clean test
```

---

## Running Tests

Run all tests
```bash
mvn clean test
```

Run specific test
```bash
mvn clean test -Dtest=LoginTest
```

Run in headless mode (for CI/CD)
```bash
mvn clean test -Dheadless=true
```

---

## View Test Results

Test logs
```bash
cat logs/automation.log
```

Screenshots
```bash
open screenshots/  # macOS
xdg-open screenshots/  # Linux
```

---

## Project Structure

Framework code
```
src/main/java/com/epam/automation/
  core/
    base/          - BasePage for page objects
    config/        - Configuration management
    driver/        - WebDriver management
    logger/        - Log4j2 logging
    utils/         - Utility classes
  business/
    pages/         - Page objects
```

Tests
```
src/test/java/com/epam/automation/
  tests/
    LoginTest.java - Example test
    base/          - BaseTest with setup/teardown
    listeners/     - Test listeners
```

Configuration
```
src/test/resources/
  config.properties         - Test configuration
  testng.xml               - TestNG suite config
  reportportal.properties  - ReportPortal config
```

---

## Features

Logger - Apache Log4j2
- Multiple log levels (DEBUG, INFO, WARN, ERROR, FATAL)
- Console output
- File output (logs/automation.log)
- ReportPortal integration

Reporter - ReportPortal
- Test execution tracking via TestNG integration
- Automatic screenshot capture on test success/failure
- Log aggregation with Log4j2 integration
- Asynchronous logging support

Test Runner - TestNG
- Parallel execution (2 threads)
- Test grouping and filtering
- Lifecycle management
- Maven Surefire integration

Configuration
- Centralized property management
- Environment-specific settings
- Easy customization

Utilities
- Element operations with waits
- Screenshot capture
- Wait management
- Sensitive data masking

---

## Writing Tests

Create page object
```java
public class MyPage extends BasePage {
    @FindBy(id = "button-id")
    private WebElement myButton;

    public MyPage(WebDriver driver) {
        super(driver);
    }

    public void clickButton() {
        click(myButton);
    }
}
```

Create test
```java
public class MyTest extends BaseTest {
    @Test
    public void testExample() {
        MyPage page = new MyPage(driver);
        page.clickButton();
        
        Assert.assertTrue(condition, "Test assertion message");
    }
}
```

---

## Configuration

Edit src/test/resources/config.properties
```properties
browser=chrome                         # chrome, firefox, edge, safari
headless=false                         # false = visible browser window, true = hidden browser
url=http://localhost:8080/ui/#login    # ReportPortal login page (local Docker)
username=test_user                     # Test credentials
password=test_password
screenshot.path=./screenshots/
```

---

## Parallel Execution
- Configured for 2 parallel threads (safe for CI environments)
- Each test gets isolated WebDriver via ThreadLocal
- Increase for local runs: `mvn test -DthreadCount=4`

---

## Technologies

- Java 21
- Selenium WebDriver 4.41.0
- TestNG 7.12.0
- Apache Log4j2 2.25.3
- ReportPortal 5.x
- WebDriverManager 6.3.3
- Maven 3.9+

---

## Common Issues

Issue: Can't find driver
Solution: WebDriverManager auto-downloads drivers. Ensure internet connection.

Issue: Connection refused
Solution: Verify application is running and URL is correct in config.properties

Issue: Test timeout
Solution: Check if application is slow or increase timeout in BasePage.java

---

## Logging Best Practices

Use Log4j2 logger for all output:
```java
ILogger logger = LoggerFactory.getLogger(MyTest.class);
logger.info("Test message");
logger.logStep("Performing action");
```

Do NOT use System.out.println() - use logger instead.

---

Framework Version: 1.0-SNAPSHOT

Last Updated: March 30, 2026

