package com.example.playwright.tests;

import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.InventoryPage;
import com.example.playwright.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {

    @Test
    @Order(1)
    @Tag("Priority-High")
    @DisplayName("Should login successfully with valid standard_user credentials")
    void shouldLoginSuccessfullyWithStandardUser() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        assertTrue(inventoryPage.isLoaded(), "Expected Products page to be loaded after valid login");
    }

    @Test
    @Order(2)
    @Tag("Priority-Medium")
    @DisplayName("Should display error message when login fails with locked_out_user")
    void shouldShowErrorForLockedOutUser() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("locked_out_user", "secret_sauce");

        assertTrue(loginPage.isErrorMessageDisplayed(), "Expected error message container to be visible");
        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"), 
                "Expected error message to mention locked out user");
    }

    @Test
    @Order(3)
    @Tag("Priority-Medium")
    @DisplayName("Should display error message for invalid credentials")
    void shouldShowErrorForInvalidCredentials() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("invalid_user", "invalid_password");

        assertTrue(loginPage.isErrorMessageDisplayed(), "Expected error message for invalid login");
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), 
                "Expected credential mismatch error message");
    }
}
