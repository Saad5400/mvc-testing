package org.example.mvctodo.controller;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ToDoControllerTest {

    @Mock
    private ToDoService toDoService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ToDoController toDoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for listToDos method
    @Test
    public void testListToDos() {
        // Setup mock data
        when(toDoService.getPendingToDos()).thenReturn(Collections.emptyList());
        when(toDoService.getCompletedToDos()).thenReturn(Collections.emptyList());

        // Call method
        String viewName = toDoController.listToDos(model);

        // Verify results
        assertEquals("index", viewName);
        verify(model).addAttribute("pendingTodos", Collections.emptyList());
        verify(model).addAttribute("completedTodos", Collections.emptyList());
    }

    // Test for addToDoForm method
    @Test
    public void testAddToDoForm() {
        // Call method
        String viewName = toDoController.addToDoForm(null, model);

        // Verify results
        assertEquals("add-todo", viewName);
        verify(model).addAttribute(eq("todo"), any(ToDo.class));
    }

    // Test for saveToDo method with valid input
    @Test
    public void testSaveToDo_ValidInput() {
        // Setup mock data
        ToDo toDo = new ToDo();
        when(bindingResult.hasErrors()).thenReturn(false);

        // Call method
        String viewName = toDoController.saveToDo(toDo, bindingResult, model);

        // Verify results
        assertEquals("redirect:/", viewName);
        verify(toDoService).saveToDo(toDo);
        verify(model, never()).addAttribute(eq("errors"), any());
    }

    // Test for saveToDo method with invalid input
    @Test
    public void testSaveToDo_InvalidInput() {
        // Setup mock data
        ToDo toDo = new ToDo();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Call method
        String viewName = toDoController.saveToDo(toDo, bindingResult, model);

        // Verify results
        assertEquals("add-todo", viewName);
        verify(model).addAttribute("todo", toDo);
        verify(model).addAttribute(eq("errors"), any());
        verify(toDoService, never()).saveToDo(toDo);
    }

    // Test for completeToDo method
    @Test
    public void testCompleteToDo() {
        Long id = 1L;

        // Call method
        String viewName = toDoController.completeToDo(id);

        // Verify results
        assertEquals("redirect:/", viewName);
        verify(toDoService).markAsComplete(id);
    }

    // Test for reopenToDo method
    @Test
    public void testReopenToDo() {
        Long id = 1L;

        // Call method
        String viewName = toDoController.reopenToDo(id);

        // Verify results
        assertEquals("redirect:/", viewName);
        verify(toDoService).markAsPending(id);
    }

    // Test for deleteToDo method
    @Test
    public void testDeleteToDo() {
        Long id = 1L;

        // Call method
        String viewName = toDoController.deleteToDo(id);

        // Verify results
        assertEquals("redirect:/", viewName);
        verify(toDoService).deleteToDoById(id);
    }
}
