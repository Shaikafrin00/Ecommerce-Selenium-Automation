# Test Plan — SauceDemo E-Commerce UI

## 1. Objective

Validate the primary customer journey of the SauceDemo training store through browser-based functional automation.

## 2. Scope

**In scope:** authentication, product inventory display and sorting, shopping-cart operations, required checkout information, and order confirmation.

**Out of scope:** payment-provider integration, production security testing, performance/load testing, mobile-native testing, and backend/database validation.

## 3. Test approach

- Functional UI testing using Selenium WebDriver.
- Page Object Model to separate locators/actions from test assertions.
- TestNG for test lifecycle, data providers, and assertions.
- Explicit waits for asynchronous UI behavior.
- A fresh browser session for every test method invocation.
- Extent Spark report and failure screenshots for execution evidence.

## 4. Test environment

| Item | Configuration |
|---|---|
| Application | https://www.saucedemo.com/ |
| Browser | Chrome by default; Firefox and Edge supported |
| Runtime | Java 17+ |
| Build | Maven |
| Test runner | TestNG |

## 5. Entry criteria

- Java and Maven are installed.
- Browser is installed and can be launched.
- The application URL is reachable.
- Maven dependencies can be resolved.

## 6. Exit criteria

- All planned scenarios have an execution result.
- Failed scenarios have a failure reason and, where possible, a screenshot.
- Test execution reports are generated and reviewed.

## 7. Risks and mitigations

| Risk | Mitigation |
|---|---|
| Demo site is unavailable | Re-run when the public practice site is reachable; record infrastructure failures separately. |
| Browser/driver mismatch | Use WebDriverManager to resolve compatible drivers. |
| Dynamic UI timing | Use centralized explicit waits instead of fixed sleeps. |
| Shared test state | Start each test with a new browser session. |

## 8. Deliverables

Automation source code, TestNG suite, test case inventory, Extent HTML report, failure screenshots, and CI workflow artifacts.
