package org.example.mvctodo.service;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.IToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceMockTest {

    @Mock
    private IToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeEach
    public void setUp() {
        // Initialize the mock objects
        MockitoAnnotations.openMocks(this);
    }

    // Mock-based test for getting all ToDos
    @Test
    public void testGetAllToDos() {
        // Given: Mocked repository returning a list of ToDos
        List<ToDo> mockToDos = Arrays.asList(new ToDo(), new ToDo());
        when(toDoRepository.findAll()).thenReturn(mockToDos);

        // When: Call the service method
        List<ToDo> result = toDoService.getAllToDos();

        // Then: Verify the results and method call counts
        assertEquals(2, result.size(), "Should return 2 ToDos");
        verify(toDoRepository, times(1)).findAll();  // Ensure `findAll` is called once
    }

    // Mock-based test for getting a single ToDo by ID
    @Test
    public void testGetToDoById() {
        // Given: Mocked repository returning a ToDo
        ToDo mockToDo = new ToDo();
        mockToDo.setId(1L);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));

        // When: Call the service method
        Optional<ToDo> result = toDoService.getToDoById(1L);

        // Then: Verify the result and the order of method calls
        assertTrue(result.isPresent(), "ToDo should be present");
        assertEquals(1L, result.get().getId(), "ID should match");

        verify(toDoRepository, times(1)).findById(1L);  // Ensure findById is called exactly once
        verify(toDoRepository, never()).deleteById(anyLong());  // Ensure deleteById is never called
    }

    // Mock-based test for saving a new ToDo
    @Test
    public void testSaveToDo() {
        // Given: Mocked repository saving a ToDo
        ToDo mockToDo = new ToDo();
        mockToDo.setTitle("New ToDo");

        when(toDoRepository.save(mockToDo)).thenReturn(mockToDo);

        // When: Call the service method
        ToDo result = toDoService.saveToDo(mockToDo);

        // Then: Verify save is called and the correct order
        assertNotNull(result, "Saved ToDo should not be null");
        assertEquals("New ToDo", result.getTitle(), "Title should match");

        verify(toDoRepository, times(1)).save(mockToDo);  // Ensure save is called once
        verify(toDoRepository, times(1)).save(any(ToDo.class));  // Ensure save is called exactly once with any ToDo
        verifyNoMoreInteractions(toDoRepository);  // No more interactions should occur with the repository
    }

    // Mock-based test for deleting a ToDo by ID
    @Test
    public void testDeleteToDoById() {
        // When: Call the service method
        toDoService.deleteToDoById(1L);

        // Then: Verify the method call and order
        verify(toDoRepository, times(1)).deleteById(1L);  // Ensure deleteById is called once
        verify(toDoRepository, never()).findById(1L);  // Ensure findById is never called
    }

    // Test for calling save twice and ensuring the method is called in order
    @Test
    public void testSaveToDoMultipleCalls() {
        // Given: Mocked repository with multiple save calls
        ToDo todo1 = new ToDo();
        ToDo todo2 = new ToDo();

        // When: Save two ToDos
        toDoService.saveToDo(todo1);
        toDoService.saveToDo(todo2);

        // Then: Verify that save was called twice in the correct order
        verify(toDoRepository, times(2)).save(any(ToDo.class));  // Save should be called twice
        verify(toDoRepository).save(todo1);  // First save should be with todo1
        verify(toDoRepository).save(todo2);  // Second save should be with todo2
        verifyNoMoreInteractions(toDoRepository);  // Ensure no more interactions occur
    }

    // Test for ensuring the order of method calls
    @Test
    public void testMethodCallOrder() {
        // Given: Mocked repository with specific calls
        ToDo mockToDo = new ToDo();
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));

        // When: Call getById and then save
        toDoService.getToDoById(1L);
        toDoService.saveToDo(mockToDo);

        // Then: Ensure method calls happened in the correct order
        inOrder(toDoRepository).verify(toDoRepository).findById(1L);  // findById should be called first
        inOrder(toDoRepository).verify(toDoRepository).save(mockToDo);  // save should be called after findById
    }

    // Test with mock ensuring `delete` is not called when save is
    @Test
    public void testSaveDoesNotCallDelete() {
        // Given: Mocked repository
        ToDo todo = new ToDo();
        when(toDoRepository.save(todo)).thenReturn(todo);

        // When: Save a ToDo
        toDoService.saveToDo(todo);

        // Then: Ensure delete is not called
        verify(toDoRepository, times(1)).save(todo);  // Save should be called once
        verify(toDoRepository, never()).deleteById(anyLong());  // deleteById should never be called
    }
}
