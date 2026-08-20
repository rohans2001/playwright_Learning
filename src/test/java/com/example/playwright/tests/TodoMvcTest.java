package com.example.playwright.tests;

import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.TodoMvcPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoMvcTest extends BaseTest {

    @Test
    @DisplayName("Should add new todo item to TodoMVC list")
    void shouldAddTodoItem() {
        TodoMvcPage todoPage = new TodoMvcPage(page);
        todoPage.navigate();
        todoPage.addTodoItem("Buy groceries");

        List<String> items = todoPage.getTodoItemsText();
        assertTrue(items.contains("Buy groceries"), "Expected todo list to contain 'Buy groceries'");
    }
}
