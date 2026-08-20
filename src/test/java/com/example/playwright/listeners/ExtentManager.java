package com.example.playwright.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentManager {
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
    private static final String REPORT_FILE_PATH = System.getProperty("user.dir") + File.separator + "target" 
            + File.separator + "extent-reports" + File.separator + "ExtentReport.html";

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_FILE_PATH);
            sparkReporter.config().setDocumentTitle("Playwright Test Automation Report");
            sparkReporter.config().setReportName("Execution Results");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Framework", "Playwright Java + JUnit 5");
        }
        return extent;
    }

    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        testMap.put(Thread.currentThread().getId(), test);
        return test;
    }

    public static synchronized ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
