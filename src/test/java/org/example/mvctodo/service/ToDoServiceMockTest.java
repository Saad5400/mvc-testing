package org.example.mvctodo.service;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.IToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceMockTest {

    @Mock
    private IToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllToDos() {
        List<ToDo> mockToDos = Arrays.asList(new ToDo(), new ToDo());
        when(toDoRepository.findAll()).thenReturn(mockToDos);

        List<ToDo> result = toDoService.getAllToDos();

        assertEquals(2, result.size(), "Should return 2 ToDos");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findAll();
        inOrder.verifyNoMoreInteractions();  // Ensures no further interactions happened after findAll
    }

    @Test
    public void testGetPendingToDos() {
        // Given: Two pending ToDos
        ToDo pendingToDo1 = new ToDo();
        pendingToDo1.setId(1L);
        pendingToDo1.setTitle("Pending ToDo 1");
        pendingToDo1.setCompleted(false);

        ToDo pendingToDo2 = new ToDo();
        pendingToDo2.setId(2L);
        pendingToDo2.setTitle("Pending ToDo 2");
        pendingToDo2.setCompleted(false);

        when(toDoRepository.findAll()).thenReturn(Arrays.asList(pendingToDo1, pendingToDo2));

        // When: Retrieving only pending ToDos
        List<ToDo> result = toDoService.getPendingToDos();

        // Then: Both ToDos should be returned
        assertEquals(2, result.size(), "Should return 2 pending ToDos");
        assertTrue(result.stream().noneMatch(ToDo::isCompleted), "All returned ToDos should be pending");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findAll();
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void testGetCompletedToDos() {
        // Given: Two completed ToDos
        ToDo completedToDo1 = new ToDo();
        completedToDo1.setId(1L);
        completedToDo1.setTitle("Completed ToDo 1");
        completedToDo1.setCompleted(true);

        ToDo completedToDo2 = new ToDo();
        completedToDo2.setId(2L);
        completedToDo2.setTitle("Completed ToDo 2");
        completedToDo2.setCompleted(true);

        when(toDoRepository.findAll()).thenReturn(Arrays.asList(completedToDo1, completedToDo2));

        // When: Retrieving only completed ToDos
        List<ToDo> result = toDoService.getCompletedToDos();

        // Then: Both ToDos should be returned
        assertEquals(2, result.size(), "Should return 2 completed ToDos");
        assertTrue(result.stream().allMatch(ToDo::isCompleted), "All returned ToDos should be completed");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findAll();
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void testMarkAsComplete() {
        ToDo todo = new ToDo();
        todo.setCompleted(false);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(todo));

        toDoService.markAsComplete(1L);

        assertTrue(todo.isCompleted(), "ToDo should be marked as complete");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findById(1L);
        inOrder.verify(toDoRepository).save(todo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMarkAsPending() {
        ToDo todo = new ToDo();
        todo.setCompleted(true);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(todo));

        toDoService.markAsPending(1L);

        assertFalse(todo.isCompleted(), "ToDo should be marked as pending");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findById(1L);
        inOrder.verify(toDoRepository).save(todo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testGetToDoById() {
        ToDo mockToDo = new ToDo();
        mockToDo.setId(1L);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));

        Optional<ToDo> result = toDoService.getToDoById(1L);

        assertTrue(result.isPresent(), "ToDo should be present");
        assertEquals(1L, result.get().getId(), "ID should match");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findById(1L);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSaveToDo() {
        ToDo mockToDo = new ToDo();
        mockToDo.setTitle("New ToDo");

        when(toDoRepository.save(mockToDo)).thenReturn(mockToDo);

        ToDo result = toDoService.saveToDo(mockToDo);

        assertNotNull(result, "Saved ToDo should not be null");
        assertEquals("New ToDo", result.getTitle(), "Title should match");

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).save(mockToDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testDeleteToDoById() {
        toDoService.deleteToDoById(1L);

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).deleteById(1L);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSaveToDoMultipleCalls() {
        ToDo todo1 = new ToDo();
        ToDo todo2 = new ToDo();

        toDoService.saveToDo(todo1);
        toDoService.saveToDo(todo2);

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).save(todo1);
        inOrder.verify(toDoRepository).save(todo2);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMethodCallOrder_GetByIdThenSave() {
        ToDo mockToDo = new ToDo();
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));

        toDoService.getToDoById(1L);
        toDoService.saveToDo(mockToDo);

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).findById(1L);
        inOrder.verify(toDoRepository).save(mockToDo);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSaveDoesNotCallDelete() {
        ToDo todo = new ToDo();
        when(toDoRepository.save(todo)).thenReturn(todo);

        toDoService.saveToDo(todo);

        InOrder inOrder = inOrder(toDoRepository);
        inOrder.verify(toDoRepository).save(todo);
        inOrder.verifyNoMoreInteractions();
    }
}
