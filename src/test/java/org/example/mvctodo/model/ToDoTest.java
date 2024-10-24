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

        assertEquals(1L, todo.getId());
        assertEquals("Test To-Do", todo.getTitle());
        assertFalse(todo.isCompleted());
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
    public void testToDoTitleThrowsException() {
        ToDo todo = new ToDo();

        assertThrows(NullPointerException.class, () -> {
            todo.getTitle().length();
        });
    }
}
