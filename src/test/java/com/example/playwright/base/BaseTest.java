package com.example.playwright.base;

import com.example.playwright.factory.PlaywrightFactory;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    protected Page page;

    @BeforeEach
    public void setUp() {
        page = PlaywrightFactory.initPage();
    }

    @AfterEach
    public void tearDown() {
        PlaywrightFactory.cleanUp();
    }
}

