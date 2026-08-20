package com.example.playwright.tests;

import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.WikipediaHomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikipediaTest extends BaseTest {

    @Test
    @DisplayName("Should search Wikipedia for Playwright topic")
    void shouldSearchWiki() {
        WikipediaHomePage wikiPage = new WikipediaHomePage(page);
        wikiPage.navigate();
        wikiPage.searchFor("playwright");

        assertEquals("https://en.wikipedia.org/wiki/Playwright", wikiPage.getPageUrl());
    }
}
