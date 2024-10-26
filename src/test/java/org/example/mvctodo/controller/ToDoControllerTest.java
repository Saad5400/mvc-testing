package org.example.mvctodo.controller;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ToDoControllerTest {

    @InjectMocks
    private ToDoController toDoController;

    @Mock
    private ToDoService toDoService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListToDos() {
        // Test if listToDos returns "index" and adds an empty list to the model
        when(toDoService.getAllToDos()).thenReturn(Collections.emptyList());
        String viewName = toDoController.listToDos(model);

        assertEquals("index", viewName);
        verify(model).addAttribute("todos", Collections.emptyList());
    }

    @Test
    public void testAddToDoForm() {
        // Test if addToDoForm returns "add-todo" and adds a new ToDo to the model
        String viewName = toDoController.addToDoForm(model);

        assertEquals("add-todo", viewName);
        verify(model).addAttribute("todo", new ToDo());
    }

    @Test
    public void testSaveToDo() {
        // Test if saveToDo redirects to "/" and calls saveToDo on the service
        ToDo newToDo = new ToDo();
        String redirectUrl = toDoController.saveToDo(newToDo);

        assertEquals("redirect:/", redirectUrl);
        verify(toDoService).saveToDo(newToDo);
    }

    @Test
    public void testEditToDoForm() {
        /* Test if editToDoForm returns edit-todo and adds the existing ToDo to the model */
        Long id = 1L;
        ToDo existingToDo = new ToDo();
        when(toDoService.getToDoById(id)).thenReturn(Optional.of(existingToDo));

        String viewName = toDoController.editToDoForm(id, model);

        assertEquals("edit-todo", viewName);
        verify(model).addAttribute("todo", existingToDo);
    }

    @Test
    public void testEditToDoForm_InvalidId() {
        // Test if editToDoForm throws IllegalArgumentException for an invalid id
        Long invalidId = 99L;
        when(toDoService.getToDoById(invalidId)).thenReturn(Optional.empty());

        try {
            toDoController.editToDoForm(invalidId, model);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid To-Do ID: 99", e.getMessage());
        }
    }

    @Test
    public void testUpdateToDo() {
        // Test if updateToDo redirects to "/" and calls saveToDo on the service
        ToDo updatedToDo = new ToDo();
        String redirectUrl = toDoController.updateToDo(updatedToDo);

        assertEquals("redirect:/", redirectUrl);
        verify(toDoService).saveToDo(updatedToDo);
    }

    @Test
    public void testDeleteToDo() {
        // Test if deleteToDo redirects to "/" and calls deleteToDoById on the service
        Long id = 1L;
        String redirectUrl = toDoController.deleteToDo(id);

        assertEquals("redirect:/", redirectUrl);
        verify(toDoService).deleteToDoById(id);
    }
}
