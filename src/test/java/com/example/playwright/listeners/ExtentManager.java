package com.example.playwright.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.Desktop;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentManager {
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
    private static final String REPORT_FILE_PATH = System.getProperty("user.dir") + File.separator + "target" 
            + File.separator + "extent-reports" + File.separator + "ExtentReport.html";
    private static boolean shutdownHookRegistered = false;

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

            registerShutdownHook();
        }
        return extent;
    }

    private static void registerShutdownHook() {
        if (!shutdownHookRegistered) {
            shutdownHookRegistered = true;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                flush();
                openReportInBrowser();
            }));
        }
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

    public static void openReportInBrowser() {
        try {
            File reportFile = new File(REPORT_FILE_PATH);
            if (!reportFile.exists()) return;

            String os = System.getProperty("os.name").toLowerCase();
            boolean opened = false;

            if (os.contains("win")) {
                try {
                    new ProcessBuilder("cmd", "/c", "start", "chrome", reportFile.getAbsolutePath()).start();
                    opened = true;
                } catch (Exception ignored) {
                }
            }

            if (!opened) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(reportFile.toURI());
                } else if (os.contains("win")) {
                    new ProcessBuilder("cmd", "/c", "start", "", reportFile.getAbsolutePath()).start();
                } else if (os.contains("mac")) {
                    new ProcessBuilder("open", reportFile.getAbsolutePath()).start();
                } else if (os.contains("nix") || os.contains("nux")) {
                    new ProcessBuilder("xdg-open", reportFile.getAbsolutePath()).start();
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to automatically open report in browser: " + e.getMessage());
        }
    }
}
