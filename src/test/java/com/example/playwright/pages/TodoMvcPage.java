package com.example.playwright.pages;

import com.example.playwright.utils.ConfigReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class TodoMvcPage {
    private final Page page;
    private final Locator todoInput;
    private final Locator todoItems;

    public TodoMvcPage(Page page) {
        this.page = page;
        this.todoInput = page.getByPlaceholder("What needs to be done?");
        this.todoItems = page.locator(".todo-list li");
    }

    public void navigate() {
        String url = ConfigReader.getProperty("todo.url", "https://demo.playwright.dev/todomvc");
        page.navigate(url);
    }

    public void addTodoItem(String taskName) {
        todoInput.fill(taskName);
        todoInput.press("Enter");
    }

    public List<String> getTodoItemsText() {
        return todoItems.allTextContents();
    }
}
