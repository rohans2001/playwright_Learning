package com.example.playwright.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.example.playwright.factory.PlaywrightFactory;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.*;

import java.util.Base64;
import java.util.Optional;

public class TestListener implements TestWatcher, BeforeTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getDisplayName();
        ExtentTest test = ExtentManager.createTest(methodName, "Class: " + className);
        test.info("Starting test execution: " + methodName);

        for (String tag : context.getTags()) {
            test.assignCategory(tag);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null && context.getExecutionException().isPresent()) {
            try {
                Page page = PlaywrightFactory.getPage();
                if (page != null && !page.isClosed()) {
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                    String base64Image = Base64.getEncoder().encodeToString(screenshot);
                    test.fail("Failure Screenshot", 
                            MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
                }
            } catch (Exception e) {
                test.warning("Failed to capture failure screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.pass("Test passed successfully.");
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.fail("Test failed: " + cause.getMessage());
            test.fail(cause);
        }
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.skip("Test disabled: " + reason.orElse("No reason provided"));
        }
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.skip("Test aborted: " + cause.getMessage());
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ExtentManager.flush();
    }
}
