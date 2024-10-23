package org.example.mvctodo.service;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.ToDoServiceStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceStubTest {
    ToDoServiceStub test;

    @BeforeEach
    public void setUp() {
        test = new ToDoServiceStub();
    }

    @Test
    public void testFindAll() {
        List<ToDo> result = test.findAll();

        // Then: Verify the number of ToDos returned
        assertEquals(3, result.size(), "Should return 3 ToDos");
    }
    @Test
    public void testFindByID() {
        Optional<ToDo> result = test.findById((long) 1);
        assertTrue(result.isPresent(), "ToDo should be found");
        assertEquals("Hi", result.get().getTitle(), "Title should match");
    }

    @Test
    public void testSave() {
        ToDo todo = new ToDo();
        todo.setTitle("Save this ToDo");
        ToDo result = test.save(todo);
        assertNotNull(result.getId(), "ToDo should have an ID");
        assertEquals("Save this ToDo", result.getTitle(), "Title should match");
    }

    @Test
    public void testDelete() {
        test.deleteById((long) 2);
        List<ToDo> all = test.findAll();
        Optional<ToDo> result = test.findById((long) 2);
        assertEquals(2, all.size(), "should only contain two");
        assertFalse(result.isPresent(), "s should no longer be present");
    }

    @Test
    public void testGetToDoById_NotFound_WithStub() {
        Optional<ToDo> result = test.findById(999L);
        assertFalse(result.isPresent(), "ToDo should not be found");
    }
}
