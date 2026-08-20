package com.example.playwright.tests;

import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.InventoryPage;
import com.example.playwright.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest extends BaseTest {

    @Test
    @DisplayName("Should add products to cart and update cart badge count")
    void shouldAddProductsToCartAndUpdateBadge() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addItemToCartByName("Sauce Labs Backpack");
        inventoryPage.addItemToCartByName("Sauce Labs Bike Light");

        assertEquals(2, inventoryPage.getCartBadgeCount(), "Expected cart badge count to be 2");
    }

    @Test
    @DisplayName("Should sort products by price low to high")
    void shouldSortProductsByPriceLowToHigh() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.selectSortOption("lohi");

        List<Double> prices = inventoryPage.getItemPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            assertTrue(prices.get(i) <= prices.get(i + 1), 
                    "Expected price " + prices.get(i) + " to be <= " + prices.get(i + 1));
        }
    }
}
