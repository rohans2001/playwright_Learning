package com.example.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private final Page page;
    private final Locator pageTitle;
    private final Locator checkoutButton;

    public CartPage(Page page) {
        this.page = page;
        this.pageTitle = page.locator(".title");
        this.checkoutButton = page.locator("#checkout");
    }

    public boolean isLoaded() {
        return pageTitle.isVisible() && "Your Cart".equalsIgnoreCase(pageTitle.textContent().trim());
    }

    public List<String> getCartItemNames() {
        List<Locator> itemLocators = page.locator(".inventory_item_name").all();
        List<String> names = new ArrayList<>();
        for (Locator loc : itemLocators) {
            names.add(loc.textContent().trim());
        }
        return names;
    }

    public void clickCheckout() {
        checkoutButton.click();
    }
}
