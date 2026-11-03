# E-Commerce Website Test Automation Framework

A Java UI automation framework for the public [SauceDemo](https://www.saucedemo.com/) practice store. It uses Selenium WebDriver, TestNG, Maven, Page Object Model (POM), explicit waits, WebDriverManager, and Extent Spark reporting.

> Portfolio project for learning and demonstration. SauceDemo is a public training application; do not use real personal information or credentials.

## Features
- Page Object Model for login, inventory, cart, and checkout.
- Reusable WebDriver factory with Chrome, Firefox, and Edge support.
- TestNG assertions, listener hooks, and data-driven negative login tests.
- Explicit waits and centralized configuration.
- Extent HTML report and failure screenshots.
- GitHub Actions workflow for headless Chrome regression runs.

## Stack
Java 17+, Selenium 4.51.0, TestNG 7.12.0, Maven, WebDriverManager 6.4.0, ExtentReports 5.1.2.

## Coverage
| Area | Scenarios |
|---|---|
| Login | Valid login; invalid username/password; blank fields; locked-out user |
| Inventory | Product count; sort by price ascending |
| Cart | Add/remove products; cart badge; empty cart |
| Checkout | Successful order; required first-name validation |

The suite contains 12 TestNG invocations, including data-provider cases.

## Prerequisites
- JDK 17 or later
- Apache Maven 3.9+
- Chrome, Firefox, or Edge
- Internet access on the first run for Maven and WebDriverManager downloads

Check installation:
```bash
java -version
mvn -version
```

## Run
Clone the repository:
```bash
git clone https://github.com/Shaikafrin00/Ecommerce-Selenium-Automation.git
cd Ecommerce-Selenium-Automation
```

Run headed Chrome:
```bash
mvn clean test
```

Run headless Chrome:
```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

Run Firefox or Edge:
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

Override the URL or public demo credentials without editing source:
```bash
mvn clean test -DbaseUrl=https://www.saucedemo.com/ -Dusername=standard_user -Dpassword=secret_sauce
```

Supported config values are documented in `src/test/resources/config.properties`. Optional environment overrides are listed in `.env.example`; export them in your shell before running Maven.

## Reports
- Extent HTML: `target/extent-reports/SparkReport.html`
- Failure screenshots: `target/screenshots/`
- Surefire reports: `target/surefire-reports/`

Open the Extent report in a browser after the run.

## CI
The workflow at `.github/workflows/ui-tests.yml` runs tests on pushes, pull requests, and manual dispatch. It uses headless Chrome and uploads reports/screenshots as an artifact even when tests fail.

## Repository layout
```text
src/main/java/com/qa/ecommerce/   # Config, driver, page objects, utilities
src/test/java/com/qa/ecommerce/   # Test classes and TestNG listener
src/test/resources/               # Test configuration
docs/                              # Test plan and test-case inventory
.github/workflows/                 # CI workflow
pom.xml
testng.xml
```

## Test design notes
Each test gets a fresh browser session. Page locators and actions are encapsulated in page objects, while assertions remain in test classes. Tests run sequentially to avoid shared browser-state issues.

## Limitations / next steps
SauceDemo is an external practice site, so availability and selectors may change independently of this repository. Planned extensions include API tests, accessibility checks, cross-browser CI matrix, and richer test-data management.
