package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;

import java.util.*;

public class ToDoRepositoryFake implements IToDoRepository {

    private final Map<Long, ToDo> stubData = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<ToDo> findAll() {
        return new ArrayList<>(stubData.values());
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return Optional.ofNullable(stubData.get(id));
    }

    @Override
    public ToDo save(ToDo toDo) {
        if (toDo.getId() == null) {
            toDo.setId(idCounter++);
        }
        stubData.put(toDo.getId(), toDo);
        return toDo;
    }

    @Override
    public void deleteById(Long id) {
        stubData.remove(id);
    }
}
