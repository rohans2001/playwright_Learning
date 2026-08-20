# Java Playwright Project

## Requirements
- Java 17+
- Maven 3.9+

## Install Playwright browsers
```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

## Run tests
```bash
mvn test
```

## Project structure
```text
java-playwright-project/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/example/
    └── test/java/com/example/ExampleTest.java
```
