package com.example.playwright.tests;

import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.CartPage;
import com.example.playwright.pages.CheckoutPage;
import com.example.playwright.pages.InventoryPage;
import com.example.playwright.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutTest extends BaseTest {

    @Test
    @DisplayName("Should complete end-to-end checkout purchase workflow successfully")
    void shouldCompleteEndToEndCheckoutFlow() {
        // 1. Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        // 2. Add product to cart
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addItemToCartByName("Sauce Labs Backpack");
        inventoryPage.goToCart();

        // 3. Verify Cart & proceed to checkout
        CartPage cartPage = new CartPage(page);
        assertTrue(cartPage.isLoaded(), "Expected Cart page to be loaded");
        assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Backpack"), 
                "Expected cart to contain 'Sauce Labs Backpack'");
        cartPage.clickCheckout();

        // 4. Fill shipping information & complete checkout
        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.fillCustomerInformation("John", "Doe", "12345");
        checkoutPage.clickFinish();

        // 5. Verify order completion
        assertTrue(checkoutPage.isOrderComplete(), "Expected order confirmation header to be displayed");
    }
}
