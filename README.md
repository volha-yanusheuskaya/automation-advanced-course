# Automation Advanced Course Framework

Test automation framework for UI and API testing built with Java 21, Selenium WebDriver, JUnit 5, TestNG, Cucumber, and REST Assured.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Execution Modes](#execution-modes)
- [Running Tests](#running-tests)
- [Maven Profiles](#maven-profiles)
- [Selenium Grid (Docker)](#selenium-grid-docker)
- [BrowserStack](#browserstack)
- [CI/CD (GitHub Actions)](#cicd-github-actions)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Technologies](#technologies)

---

## Prerequisites

- Java 21
- Maven 3.9+
- Docker + Docker Compose (for Selenium Grid)
- Git

---

## Quick Start

```bash
# 1. Clone the repository
git clone <repository-url>
cd automation-advanced-course

# 2. Build the project
mvn clean compile

# 3. Run tests locally
mvn clean test
```

---

## Execution Modes

The framework supports three execution modes, controlled by **Maven profiles** or the `execution.mode` property in `config.properties`:

| Mode | Profile | How it runs | When to use |
|---|---|---|---|
| **Local** | _(default)_ | Browser on your machine | Development, debugging |
| **Grid** | `-Pgrid` | Browser in Docker containers | Parallel runs, CI/CD |
| **BrowserStack** | `-Pbrowserstack` | Browser on BrowserStack cloud | Cross-browser/OS testing |

**Priority:** Maven profile (system property) → `config.properties` → default (`local`)

You can also switch modes in `config.properties` for quick IDE runs:
```properties
execution.mode=local    # or grid, browserstack
```

---

## Running Tests

### All tests (local)
```bash
mvn clean test
```

### Specific test class
```bash
mvn clean test -Dtest=LoginTest
```

### By test group
```bash
mvn clean test -Dgroups=ui_junit     # UI tests (JUnit 5)
mvn clean test -Dgroups=api          # API tests
```

### Choose browser
```bash
mvn clean test -Dbrowser=firefox     # chrome (default), firefox, edge, safari
```

### Headless mode
```bash
mvn clean test -Dheadless=true
```

### BDD tests (Cucumber)
```bash
mvn clean test -Pcucumber "-Dcucumber.filter.tags=@ui_bdd"
```

---

## Maven Profiles

| Profile | Command | Description |
|---|---|---|
| `grid` | `mvn test -Pgrid` | Run tests on Selenium Grid |
| `browserstack` | `mvn test -Pbrowserstack` | Run tests on BrowserStack |
| `cucumber` | `mvn test -Pcucumber` | Run Cucumber BDD tests |
| `api` | `mvn test -Papi` | Run API tests only |
| `testng-class-parallel` | `mvn test -Ptestng-class-parallel` | Parallel TestNG execution |
| `junit-class-parallel` | `mvn test -Pjunit-class-parallel` | Parallel JUnit 5 execution |

Profiles can be combined: `mvn test -Pcucumber,grid`

---

## Selenium Grid (Docker)

Run tests in isolated Docker containers with Selenium Grid (Hub + browser nodes).

### Architecture
```
Your machine
  └── Selenium Hub (localhost:4444) ── routes requests
        ├── Chrome node  (up to 2 sessions)
        └── Firefox node (up to 2 sessions)
```

### Start the Grid
```bash
# macOS Apple Silicon (ARM)
docker-compose -f docker-compose.grid.yml up -d

# Verify it's running
curl -s http://localhost:4444/status | grep ready
```

### Run tests on Grid
```bash
# Using Maven profile (recommended)
mvn clean test -Pgrid

# Choose a different browser
mvn clean test -Pgrid -Dbrowser=firefox

# Or via system properties directly
mvn clean test -Dexecution.mode=grid -Dgrid.url=http://localhost:4444/wd/hub
```

### Stop the Grid
```bash
docker-compose -f docker-compose.grid.yml down
```

### Docker Compose files

| File | Images | Platform |
|---|---|---|
| `docker-compose.grid.yml` | `seleniarm/*` (ARM) | macOS Apple Silicon |
| `docker-compose.grid-ci.yml` | `selenium/*` (amd64) | GitHub Actions / Linux |

### Grid Console
- Status: http://localhost:4444/status
- UI: http://localhost:4444/ui

---

## BrowserStack

Run tests on BrowserStack cloud for cross-browser and cross-OS testing.

### Setup

1. Set environment variables:
```bash
export BROWSERSTACK_USERNAME=your_username
export BROWSERSTACK_ACCESS_KEY=your_access_key
```

2. Platform configuration is in `browserstack.yml`:
```yaml
platforms:
  - os: Windows
    osVersion: 11
    browserName: Chrome
    browserVersion: latest
```

### Run tests
```bash
mvn clean test -Pbrowserstack
```

### What the profile does
- Adds `browserstack-java-sdk` dependency
- Attaches the BrowserStack java-agent (intercepts driver creation)
- Sets `execution.mode=browserstack`
- Test Observability reports are sent to BrowserStack dashboard

---

## CI/CD (GitHub Actions)

The pipeline (`.github/workflows/quality-gate.yml`) runs on every push/PR to `main` and `develop`:

```
1. Checkout code
2. Set up JDK 21
3. Create config from GitHub Secrets
4. Build (mvn compile)
5. Start Selenium Grid (docker-compose.grid-ci.yml)
6. Run UI JUnit tests on Grid (-Pgrid)
7. Run API tests
8. Stop Selenium Grid
9. Upload test results and screenshots as artifacts
```

### Required GitHub Secrets

| Secret | Description |
|---|---|
| `REPORTPORTAL_URL` | ReportPortal application URL |
| `REPORTPORTAL_USERNAME` | Test user login |
| `REPORTPORTAL_PASSWORD` | Test user password |
| `RP_DEFAULT_PROJECT` | Dashboard project name |
| `RP_DEMO_PROJECT` | Demo project name |
| `RP_URI` | ReportPortal API URI |
| `RP_TOKEN` | ReportPortal API token |
| `REPORTPORTAL_ENDPOINT` | ReportPortal endpoint |
| `REPORTPORTAL_UUID` | ReportPortal UUID |
| `REPORTPORTAL_PROJECT` | ReportPortal project |

---

## Project Structure

```
src/
├── main/java/com/epam/automation/
│   ├── api/business/              # API layer
│   │   ├── client/                  - HTTP clients
│   │   ├── controller/              - API controllers
│   │   ├── model/                   - API response models
│   │   └── request/                 - API request builders
│   ├── common/core/               # Shared utilities
│   │   ├── config/                  - ConfigurationReader
│   │   ├── data_reader/             - Test data readers
│   │   ├── logger/                  - ILogger, LoggerFactory
│   │   └── model/                   - Shared models
│   └── ui/                        # UI layer
│       ├── core/
│       │   ├── base/                - BasePage
│       │   ├── driver/              - DriverFactory, ExecutionMode, BrowserType
│       │   └── utils/               - ScreenshotUtil, wait helpers
│       └── business/
│           ├── components/          - Reusable UI components
│           ├── models/              - UI data models
│           ├── pages/               - Page Objects
│           └── service/             - Business-level services
│
├── test/java/com/epam/automation/tests/
│   ├── api/
│   │   ├── BaseApiTest.java         - API test base class
│   │   └── launches/                - API test classes
│   └── ui/
│       ├── data/                    - Test data providers
│       ├── launches_junit5/         - JUnit 5 UI tests
│       ├── launches_testng/         - TestNG UI tests
│       ├── launches_bdd/            - Cucumber BDD tests
│       └── listeners/               - TestListener, ReportPortalListener
│
└── test/resources/
    ├── config.properties            - Test configuration
    ├── reportportal.properties      - ReportPortal settings
    ├── testng.xml                   - TestNG suite config
    └── junit-platform.properties    - JUnit 5 parallel config
```

### Key files in project root

| File | Purpose |
|---|---|
| `pom.xml` | Maven build, dependencies, profiles |
| `docker-compose.grid.yml` | Selenium Grid for local dev (ARM) |
| `docker-compose.grid-ci.yml` | Selenium Grid for CI (amd64) |
| `browserstack.yml` | BrowserStack SDK configuration |
| `.github/workflows/quality-gate.yml` | CI/CD pipeline |

---

## Configuration

### `config.properties`

| Property | Default | Description |
|---|---|---|
| `browser` | `chrome` | Browser: chrome, firefox, edge, safari |
| `headless` | `false` | Run browser without UI |
| `url` | — | Application URL |
| `username` | — | Test user login |
| `password` | — | Test user password |
| `execution.mode` | `local` | Execution mode: local, grid, browserstack |
| `grid.url` | `http://localhost:4444/wd/hub` | Selenium Grid hub URL |
| `screenshot.path` | `./screenshots/` | Screenshot save location |

All properties can be overridden via system properties: `-Dbrowser=firefox`

---

## Technologies

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Language |
| Selenium WebDriver | 4.41.0 | Browser automation |
| JUnit 5 | 5.11.4 | Test runner (UI + API) |
| TestNG | 7.12.0 | Test runner (UI) |
| Cucumber | 7.19.0 | BDD test runner |
| REST Assured | 6.0.0 | API testing |
| AssertJ | 3.27.7 | Fluent assertions |
| Log4j2 | 2.25.4 | Logging |
| ReportPortal | 5.x | Test reporting |
| BrowserStack SDK | 1.57.0 | Cloud browser testing |
| WebDriverManager | 6.3.3 | Driver binary management |
| Lombok | 1.18.44 | Boilerplate reduction |
| Jackson | 2.17.2 | JSON serialization |
| Docker Compose | — | Selenium Grid infrastructure |
| GitHub Actions | — | CI/CD |

---

## Test Results

### Logs
```bash
cat logs/automation.log
```

### Screenshots (captured on test failure)
```bash
open screenshots/    # macOS
```

### Surefire reports
```bash
open target/surefire-reports/
```

---

## Troubleshooting

| Problem | Solution |
|---|---|
| Browser driver not found | WebDriverManager auto-downloads. Check internet connection. |
| Grid connection refused | Verify Grid is running: `curl http://localhost:4444/status` |
| BrowserStack not creating reports | Ensure `BROWSERSTACK_USERNAME` and `BROWSERSTACK_ACCESS_KEY` env vars are set |
| Tests run locally instead of Grid | Check `execution.mode` in config or use `-Pgrid` profile |
| Docker images won't pull | Check Docker/Colima is running: `colima status` or `docker info` |
| Blank Grid UI page | Grid is working — verify with `curl http://localhost:4444/status` |

---

Framework Version: 1.0-SNAPSHOT
Last Updated: April 29, 2026
