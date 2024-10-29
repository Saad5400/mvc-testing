package org.example.mvctodo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest {

    @Test
    public void testToDoGettersAndSetters() {
        ToDo todo = new ToDo();
        todo.setId(1L);
        todo.setTitle("Test To-Do");
        todo.setCompleted(false);

        assertEquals(5L, todo.getId());
        assertEquals("Test To-Do", todo.getTitle());
        assertFalse(todo.isCompleted());
        assertNotNull(todo.getTitle(), "Title should not be null");
        assertNotNull(todo.getId(), "Id should not be null");

    }

    @Test
    public void testToDoCompletionStatus() {
        ToDo todo = new ToDo();
        todo.setCompleted(true);

        assertTrue(todo.isCompleted());
    }

    @Test
    public void testToDoTitleNotNull() {
        ToDo todo = new ToDo();
        todo.setTitle("My Task");

        assertNotNull(todo.getTitle());
    }


    @Test
    public void testToDoIdNotNull() {
        ToDo todo = new ToDo();
        todo.setId(1L);

        assertNotNull(todo.getId());
    }
}
