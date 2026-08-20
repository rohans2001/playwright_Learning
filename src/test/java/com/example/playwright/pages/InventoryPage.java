package com.example.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private final Page page;
    private final Locator pageTitle;
    private final Locator sortDropdown;
    private final Locator cartBadge;
    private final Locator cartLink;

    public InventoryPage(Page page) {
        this.page = page;
        this.pageTitle = page.locator(".title");
        this.sortDropdown = page.locator(".product_sort_container");
        this.cartBadge = page.locator(".shopping_cart_badge");
        this.cartLink = page.locator(".shopping_cart_link");
    }

    public boolean isLoaded() {
        return pageTitle.isVisible() && "Products".equalsIgnoreCase(pageTitle.textContent().trim());
    }

    public String getTitleText() {
        return pageTitle.textContent().trim();
    }

    public void addItemToCartByName(String itemName) {
        page.locator(".inventory_item")
            .filter(new Locator.FilterOptions().setHasText(itemName))
            .locator("button")
            .click();
    }

    public int getCartBadgeCount() {
        if (cartBadge.isVisible()) {
            return Integer.parseInt(cartBadge.textContent().trim());
        }
        return 0;
    }

    public void selectSortOption(String optionValue) {
        // Options: az, za, lohi, hilo
        sortDropdown.selectOption(optionValue);
    }

    public List<Double> getItemPrices() {
        List<Locator> priceLocators = page.locator(".inventory_item_price").all();
        List<Double> prices = new ArrayList<>();
        for (Locator priceLoc : priceLocators) {
            String text = priceLoc.textContent().replace("$", "").trim();
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public void goToCart() {
        cartLink.click();
    }
}
