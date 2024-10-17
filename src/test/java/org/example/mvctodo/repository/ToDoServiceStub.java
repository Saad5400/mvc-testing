package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;

import java.util.List;
import java.util.Optional;

public class ToDoServiceStub implements IToDoRepository {

    List<ToDo> data = List.of(
            new ToDo(false, "Hi"),
            new ToDo(true, "True"),
            new ToDo(false, "s")
    );

    @Override
    public List<ToDo> findAll() {
        return data;
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return Optional.ofNullable(data.get(id.intValue()));
    }

    @Override
    public ToDo save(ToDo toDo) {
        data.add(toDo);
        return toDo;
    }

    @Override
    public void deleteById(Long id) {
        data.remove(id);
    }
}
