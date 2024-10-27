package org.example.mvctodo.service;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.ToDoRepositoryFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceFakeTest {

    private ToDoService toDoService;

    @BeforeEach
    public void setUp() {
        // Use the ToDoRepositoryStub instead of a real repository
        ToDoRepositoryFake stub = new ToDoRepositoryFake();
        toDoService = new ToDoService(stub);  // Injecting the stub into the service
    }


    @Test
    public void testGetAllToDos() {
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
    public void testNoDuplicateId() {
        ToDo toDo1 = new ToDo();
        toDo1.setId(1L);
        toDo1.setTitle("Buy groceries");

        ToDo toDo2 = new ToDo();
        toDo2.setId(2L);
        toDo2.setTitle("Finish project");

        ToDo toDo3 = new ToDo();
        toDo3.setId(3L); // Duplicate ID to test duplicate handling
        toDo3.setTitle("Read a book");

        List<ToDo> toDos = List.of(toDo1, toDo2, toDo3);

        Set<Long> ids = new HashSet<>();

        for (ToDo toDo : toDos) {
            assertFalse(ids.contains(toDo.getId()), "Duplicate id found: " + toDo.getId());
            ids.add(toDo.getId());
        }
    }
    @Test
    public void testGetPendingToDos() {
        // Given: One pending ToDo and one completed ToDo
        ToDo pendingTodo = new ToDo();
        pendingTodo.setTitle("Pending ToDo");
        toDoService.saveToDo(pendingTodo);

        ToDo completedTodo = new ToDo();
        completedTodo.setTitle("Completed ToDo");
        completedTodo.setCompleted(true);
        toDoService.saveToDo(completedTodo);

        // When: We retrieve only pending ToDos
        List<ToDo> result = toDoService.getPendingToDos();

        // Then: Verify only the pending ToDo is returned
        assertEquals(1, result.size(), "Should return 1 pending ToDo");
        assertFalse(result.get(0).isCompleted(), "ToDo should not be completed");
    }

    @Test
    public void testGetCompletedToDos() {
        // Given: One pending ToDo and one completed ToDo
        ToDo pendingTodo = new ToDo();
        pendingTodo.setTitle("Pending ToDo");
        toDoService.saveToDo(pendingTodo);

        ToDo completedTodo = new ToDo();
        completedTodo.setTitle("Completed ToDo");
        completedTodo.setCompleted(true);
        toDoService.saveToDo(completedTodo);

        // When: We retrieve only completed ToDos
        List<ToDo> result = toDoService.getCompletedToDos();

        // Then: Verify only the completed ToDo is returned
        assertEquals(1, result.size(), "Should return 1 completed ToDo");
        assertTrue(result.get(0).isCompleted(), "ToDo should be completed");
    }

    @Test
    public void testMarkAsComplete() {
        // Given: A pending ToDo
        ToDo todo = new ToDo();
        todo.setTitle("Pending ToDo");
        toDoService.saveToDo(todo);

        // When: We mark the ToDo as complete
        toDoService.markAsComplete(todo.getId());

        // Then: Verify the ToDo is marked as complete
        Optional<ToDo> result = toDoService.getToDoById(todo.getId());
        assertTrue(result.isPresent(), "ToDo should be found");
        assertTrue(result.get().isCompleted(), "ToDo should be marked as complete");
    }

    @Test
    public void testMarkAsPending() {
        // Given: A completed ToDo
        ToDo todo = new ToDo();
        todo.setTitle("Completed ToDo");
        todo.setCompleted(true);
        toDoService.saveToDo(todo);

        // When: We mark the ToDo as pending
        toDoService.markAsPending(todo.getId());

        // Then: Verify the ToDo is marked as pending
        Optional<ToDo> result = toDoService.getToDoById(todo.getId());
        assertTrue(result.isPresent(), "ToDo should be found");
        assertFalse(result.get().isCompleted(), "ToDo should be marked as pending");
    }

    @Test
    public void testGetToDoById() {
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
    public void testSaveToDo() {
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
    public void testDeleteToDo() {
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
    public void testGetToDoById_NotFound() {
        // Given: No ToDo is saved

        // When: We try to retrieve a non-existent ToDo by ID
        Optional<ToDo> result = toDoService.getToDoById(999L);

        // Then: Verify that the result is empty
        assertFalse(result.isPresent(), "ToDo should not be found");
    }

    @Test
    public void testUpdateToDo() {
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
