package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;

import java.util.*;

public class ToDoServiceStub implements IToDoRepository {
    private HashMap<Long, ToDo> data = new HashMap<>();
    long idCounter = 4L;

    public ToDoServiceStub() {
        data.put(1L, new ToDo(false, "Hi"));
        data.put(2L, new ToDo(true, "True"));
        data.put(3L, new ToDo(false, "s"));
    }

    @Override
    public List<ToDo> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public ToDo save(ToDo toDo) {
        if (toDo.getId() == null) {
            toDo.setId(idCounter++);
        }
        data.put(toDo.getId(), toDo);
        return toDo;
    }

    @Override
    public void deleteById(Long id) {
        data.remove(id);
    }
}
