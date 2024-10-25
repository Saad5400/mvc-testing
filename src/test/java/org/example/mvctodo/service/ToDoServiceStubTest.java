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
        // When: We retrieve all ToDos
        List<ToDo> result = toDoService.getAllToDos();

        // Then: Verify the number of predefined ToDos returned
        assertEquals(2, result.size(), "Should return 2 predefined ToDos");
    }

    @Test
    public void testGetToDoById_WithStub() {
        // When: We retrieve the ToDo by predefined ID
        Optional<ToDo> result = toDoService.getToDoById(1L);

        // Then: Verify the ToDo is found and matches the predefined title
        assertTrue(result.isPresent(), "Predefined ToDo should be found");
        assertEquals("Predefined ToDo 1", result.get().getTitle(), "Title should match predefined title");
    }

    @Test
    public void testSaveToDo_WithStub() {
        // Given: A new ToDo
        ToDo todo = new ToDo();
        todo.setTitle("Save this ToDo");

        // When: We save the ToDo (stub will assign ID 3)
        ToDo result = toDoService.saveToDo(todo);

        // Then: Verify the ToDo is saved with a dummy ID
        assertNotNull(result.getId(), "ToDo should have an ID assigned by the stub");
        assertEquals("Save this ToDo", result.getTitle(), "Title should match");
    }

    @Test
    public void testDeleteToDo_WithStub() {
        // Given: A predefined ToDo exists (in stub)
        Long idToDelete = 1L;

        // When: We delete the ToDo (stub does not actually delete)
        toDoService.deleteToDoById(idToDelete);

        // Then: The ToDo should still exist in the stub (stub does not remove it)
        Optional<ToDo> result = toDoService.getToDoById(idToDelete);
        assertTrue(result.isPresent(), "ToDo should still be present as deletion is not simulated");
    }

    @Test
    public void testGetToDoById_NotFound_WithStub() {
        // When: We try to retrieve a non-existent ToDo by ID
        Optional<ToDo> result = toDoService.getToDoById(999L);

        // Then: Verify that the result is empty
        assertFalse(result.isPresent(), "ToDo should not be found");
    }

    @Test
    public void testUpdateToDo_WithStub() {
        // Given: A predefined ToDo exists (in stub)
        ToDo todo = toDoService.getToDoById(1L).orElseThrow();
        todo.setTitle("Updated Title");

        // When: We update the ToDo (stub will simulate save)
        ToDo updatedToDo = toDoService.saveToDo(todo);

        // Then: Verify the ToDo is updated
        assertEquals("Updated Title", updatedToDo.getTitle(), "Title should be updated");
    }
}
