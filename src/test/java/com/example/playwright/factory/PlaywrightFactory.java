package com.example.playwright.factory;

import com.example.playwright.utils.ConfigReader;
import com.microsoft.playwright.*;

import java.util.Arrays;

public class PlaywrightFactory {
    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Page initPage() {
        String browserName = ConfigReader.getProperty("browser", "chromium").toLowerCase();
        boolean isHeadless = ConfigReader.getBooleanProperty("headless", false);

        Playwright playwright = Playwright.create();
        tlPlaywright.set(playwright);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless)
                .setArgs(Arrays.asList("--disable-gpu", "--no-sandbox"));

        Browser browser;
        switch (browserName) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(launchOptions);
                break;
        }
        tlBrowser.set(browser);

        BrowserContext context = browser.newContext();
        tlContext.set(context);

        Page page = context.newPage();
        tlPage.set(page);

        return page;
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static void cleanUp() {
        if (tlPage.get() != null) {
            tlPage.get().close();
            tlPage.remove();
        }
        if (tlContext.get() != null) {
            tlContext.get().close();
            tlContext.remove();
        }
        if (tlBrowser.get() != null) {
            tlBrowser.get().close();
            tlBrowser.remove();
        }
        if (tlPlaywright.get() != null) {
            tlPlaywright.get().close();
            tlPlaywright.remove();
        }
    }
}

