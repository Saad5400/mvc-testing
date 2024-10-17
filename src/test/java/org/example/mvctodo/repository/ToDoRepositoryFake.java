package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;

import java.util.*;

public class ToDoRepositoryFake implements IToDoRepository {

    private final Map<Long, ToDo> data = new HashMap<>();
    private long idCounter = 1;

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
