# Java Playwright Project

A Java and Playwright test automation framework implementing the Page Object Model (POM) design pattern with JUnit 5.

## Prerequisites

- **Java**: 17+
- **Maven**: 3.9+

## Setup & Execution

### 1. Install Playwright Browsers
Run the following command to download and install Playwright browser binaries:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

### 2. Run Tests
To execute all test suites:

```bash
mvn clean test
```

---

## Project Structure

```text
java-playwright-project/
├── pom.xml
├── README.md
└── src/
    └── test/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── playwright/
        │               ├── base/
        │               │   └── BaseTest.java            # Base test fixture managing lifecycle & fixtures
        │               ├── factory/
        │               │   └── PlaywrightFactory.java   # Initializes Playwright, Browser, and Page instances
        │               ├── pages/
        │               │   ├── CartPage.java            # Page Object for Shopping Cart
        │               │   ├── CheckoutPage.java        # Page Object for Checkout workflow
        │               │   ├── InventoryPage.java       # Page Object for Products catalog
        │               │   └── LoginPage.java           # Page Object for Authentication
        │               ├── tests/
        │               │   ├── CheckoutTest.java        # End-to-end checkout test cases
        │               │   ├── InventoryTest.java       # Product sorting & cart interaction tests
        │               │   └── LoginTest.java           # Authentication test scenarios
        │               └── utils/
        │                   └── ConfigReader.java        # Utility for reading configuration properties
        └── resources/
            ├── config.properties                        # Framework configuration (browser, headless mode, base URL)
            └── junit-platform.properties               # JUnit 5 configuration
```

## Architecture Overview

- **Page Object Model (POM)**: Web pages are represented as Java classes containing locators and action methods.
- **Factory Pattern**: `PlaywrightFactory` handles dynamic browser launching (Chromium, Firefox, WebKit) and context creation based on `config.properties`.
- **Configurable**: Global settings (e.g., `browser`, `headless`, `timeout`, `website.url`) are driven via `src/test/resources/config.properties`.
