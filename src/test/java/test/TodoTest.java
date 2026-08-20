package test;

import com.microsoft.playwright.*;

public class TodoTest {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false));

            Page page = browser.newPage();

            page.navigate("https://demo.playwright.dev/todomvc");

            page.getByPlaceholder("What needs to be done?")
                    .fill("Buy groceries");

            page.keyboard().press("Enter");

            System.out.println(page.locator(".todo-list li").allTextContents());

            // browser.close();
        }
    }
}