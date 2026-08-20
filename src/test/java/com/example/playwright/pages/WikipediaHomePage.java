package com.example.playwright.pages;

import com.example.playwright.utils.ConfigReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class WikipediaHomePage {
    private final Page page;
    private final Locator searchInput;

    public WikipediaHomePage(Page page) {
        this.page = page;
        this.searchInput = page.locator("input[name=\"search\"]");
    }

    public void navigate() {
        String url = ConfigReader.getProperty("wiki.url", "https://www.wikipedia.org/");
        page.navigate(url);
    }

    public void searchFor(String query) {
        searchInput.click();
        searchInput.fill(query);
        searchInput.press("Enter");
    }

    public String getPageUrl() {
        return page.url();
    }
}
