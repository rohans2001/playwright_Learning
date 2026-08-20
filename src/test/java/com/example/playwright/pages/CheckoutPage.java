package com.example.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage {
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator postalCodeInput;
    private final Locator continueButton;
    private final Locator finishButton;
    private final Locator completeHeader;

    public CheckoutPage(Page page) {
        this.firstNameInput = page.locator("#first-name");
        this.lastNameInput = page.locator("#last-name");
        this.postalCodeInput = page.locator("#postal-code");
        this.continueButton = page.locator("#continue");
        this.finishButton = page.locator("#finish");
        this.completeHeader = page.locator(".complete-header");
    }

    public void fillCustomerInformation(String firstName, String lastName, String postalCode) {
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        postalCodeInput.fill(postalCode);
        continueButton.click();
    }

    public void clickFinish() {
        finishButton.click();
    }

    public String getCompleteHeaderText() {
        return completeHeader.textContent().trim();
    }

    public boolean isOrderComplete() {
        return completeHeader.isVisible() && getCompleteHeaderText().toLowerCase().contains("thank you");
    }
}
