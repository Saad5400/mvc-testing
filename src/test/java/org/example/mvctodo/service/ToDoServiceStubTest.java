package org.example.mvctodo.service;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.ToDoRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceStubTest {

    private ToDoService toDoService;

    @BeforeEach
    public void setUp() {
        // Use the ToDoRepositoryStub instead of a real repository
        ToDoRepositoryStub stub = new ToDoRepositoryStub();
        toDoService = new ToDoService(stub);  // Injecting the stub into the service
    }

    @Test
    public void testGetAllToDos_WithStub() {
        // Given: Two ToDos are saved
        ToDo todo1 = new ToDo();
        todo1.setTitle("First ToDo");
        toDoService.saveToDo(todo1);

        ToDo todo2 = new ToDo();
        todo2.setTitle("Second ToDo");
        toDoService.saveToDo(todo2);

        // When: We retrieve all ToDos
        List<ToDo> result = toDoService.getAllToDos();

        // Then: Verify the number of ToDos returned
        assertEquals(2, result.size(), "Should return 2 ToDos");
    }

    @Test
    public void testGetToDoById_WithStub() {
        // Given: One ToDo is saved
        ToDo todo = new ToDo();
        todo.setTitle("Test ToDo");
        toDoService.saveToDo(todo);

        // When: We retrieve the ToDo by ID
        Optional<ToDo> result = toDoService.getToDoById(todo.getId());

        // Then: Verify the ToDo is found and matches the title
        assertTrue(result.isPresent(), "ToDo should be found");
        assertEquals("Test ToDo", result.get().getTitle(), "Title should match");
    }

    @Test
    public void testSaveToDo_WithStub() {
        // Given: A new ToDo
        ToDo todo = new ToDo();
        todo.setTitle("Save this ToDo");

        // When: We save the ToDo
        ToDo result = toDoService.saveToDo(todo);

        // Then: Verify the ToDo is saved and has an ID
        assertNotNull(result.getId(), "ToDo should have an ID");
        assertEquals("Save this ToDo", result.getTitle(), "Title should match");
    }

    @Test
    public void testDeleteToDo_WithStub() {
        // Given: A ToDo is saved
        ToDo todo = new ToDo();
        todo.setTitle("To be deleted");
        toDoService.saveToDo(todo);

        // When: We delete the ToDo by ID
        toDoService.deleteToDoById(todo.getId());

        // Then: Verify the ToDo is deleted
        Optional<ToDo> result = toDoService.getToDoById(todo.getId());
        assertFalse(result.isPresent(), "ToDo should no longer be present");
    }

    @Test
    public void testGetToDoById_NotFound_WithStub() {
        // Given: No ToDo is saved

        // When: We try to retrieve a non-existent ToDo by ID
        Optional<ToDo> result = toDoService.getToDoById(999L);

        // Then: Verify that the result is empty
        assertFalse(result.isPresent(), "ToDo should not be found");
    }

    @Test
    public void testUpdateToDo_WithStub() {
        // Given: A ToDo is saved
        ToDo todo = new ToDo();
        todo.setTitle("Original Title");
        toDoService.saveToDo(todo);

        // When: We update the ToDo
        todo.setTitle("Updated Title");
        toDoService.saveToDo(todo);

        // Then: Verify the ToDo is updated
        Optional<ToDo> result = toDoService.getToDoById(todo.getId());
        assertTrue(result.isPresent(), "ToDo should be found");
        assertEquals("Updated Title", result.get().getTitle(), "Title should be updated");
    }
}
