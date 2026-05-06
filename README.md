# SauceDemo Automation Framework

A scalable and maintainable **UI Test Automation Framework** built using **Java, Selenium WebDriver, and TestNG**, designed with real-world practices such as **Page Object Model, parallel execution, retry mechanisms, and detailed reporting**.

---

## Key Highlights

* Hybrid Framework (POM + Utility + Data-Driven approach)
* Thread-safe parallel execution using TestNG
* Centralized Driver Management using Factory Pattern
* Reusable utility classes (Waits, Config, etc.)
* Retry mechanism for handling flaky tests
* Extent Reports with screenshot capture on failure
* Logging support for debugging and traceability
* CI/CD ready (integrated with Jenkins)

---

## Framework Architecture

```
SauceDemoAutomationFramework
│
├── src/main/java
│   ├── com.saucedemo.base
│   │   ├── BaseTest.java
│   │   └── BaseLoggedInTest.java
│   │
│   ├── com.saucedemo.driver
│   │   └── DriverFactory.java
│   │
│   ├── com.saucedemo.listeners
│   │   ├── RetryAnalyzer.java
│   │   └── TestListener.java
│   │
│   ├── com.saucedemo.pages
│   │   ├── BasePage.java
│   │   ├── LoginPage.java
│   │   ├── InventoryPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   └── CheckoutOverviewPage.java
│   │
│   ├── com.saucedemo.utilities
│   │   ├── ConfigReader.java
│   │   ├── ExcelDataProvider.java
│   │   ├── ExcelUtil.java
│   │   ├── ExtentManager.java
│   │   └── ScreenshotUtil.java
│
├── src/main/resources
│   ├── config.properties
│   └── log4j2.xml
│
├── src/test/java
│   └── com.saucedemo.tests
│       ├── LoginPageTest.java
│       ├── InventoryPageTest.java
│       ├── CartPageTest.java
│       └── CheckoutPageTest.java
│
├── src/test/resources
│   └── testdata
│
├── logs
│   └── automationTest.log
│
├── reports
│   └── extentReport.html
│
├── screenshots
│
├── test-output
│
├── pom.xml
└── testng.xml
```

### Architecture Explanation

* **Page Layer** → Contains all UI actions and locators (follows POM)
* **Test Layer** → Contains test scenarios and validations
* **Utility Layer** → Common reusable methods
* **Factory Layer** → Driver initialization using ThreadLocal (parallel-safe)

---

## Tech Stack

| Tool/Technology    | Purpose                      |
| ------------------ | ---------------------------- |
| Java               | Programming Language         |
| Selenium WebDriver | UI Automation                |
| TestNG             | Test Execution Framework     |
| Maven              | Build Tool                   |
| Extent Reports     | Reporting                    |
| Log4j / Logger     | Logging                      |
| Jenkins            | CI/CD                        |

---

## Core Features

* Parallel Execution → Faster execution using TestNG + ThreadLocal
* Driver Management → Centralized & scalable using Factory Pattern
* Retry Logic → Automatically retries failed tests
* Data-Driven Testing → Excel-based test data using DataProvider
* Reporting → Extent Reports with failure screenshots
* Logging → Debug-friendly logs using Log4j

---

## Test Scenarios Covered

The framework automates key user flows of SauceDemo application, including:

* User Login (Valid & Invalid scenarios)
* Product Listing Validation
* Add to Cart functionality
* Remove from Cart
* Cart Page Validation
* Checkout Process
* Order Confirmation

---

## How to Run the Project

### From Command Line

```bash
git clone https://github.com/Raubin4321/SaucedemoAutomationFramework.git
cd SaucedemoAutomationFramework
mvn test
```

### Using TestNG

* Navigate to `testng.xml`
* Right-click → Run as TestNG Suite

---

## Reports & Logs

* Extent Report → /reports/extentReport.html
* Logs → /logs/automationTest.log
* Screenshots → /screenshots/

---

## 👨‍💻 Author

**Raubin Kumar**
QA Automation Engineer

GitHub: https://github.com/Raubin4321

---

## 💡 Note

This framework is designed to simulate a **real-world enterprise-level automation framework**, focusing on scalability, maintainability, and best practices.

---
