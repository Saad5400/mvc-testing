package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;

import java.util.*;

public class ToDoRepositoryStub implements IToDoRepository {

    private final List<ToDo> predefinedToDos = new ArrayList<>();

    // You can add predefined ToDo objects here for your tests
    public ToDoRepositoryStub() {
        ToDo todo1 = new ToDo();
        todo1.setId(1L);
        todo1.setTitle("Predefined ToDo 1");

        ToDo todo2 = new ToDo();
        todo2.setId(2L);
        todo2.setTitle("Predefined ToDo 2");

        predefinedToDos.add(todo1);
        predefinedToDos.add(todo2);
    }

    @Override
    public List<ToDo> findAll() {
        return new ArrayList<>(predefinedToDos);  // Return predefined ToDos
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return predefinedToDos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

    @Override
    public ToDo save(ToDo toDo) {
        // This stub doesn't actually save data, just returns the same ToDo
        if (toDo.getId() == null) {
            toDo.setId(3L);  // Assign a dummy ID for testing purposes
        }
        return toDo;
    }

    @Override
    public void deleteById(Long id) {
        // Stub does not perform deletion, it's just a no-op
    }
}
