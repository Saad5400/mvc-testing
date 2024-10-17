package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;

import java.util.List;
import java.util.Optional;

public interface IToDoRepository {

    List<ToDo> findAll();

    Optional<ToDo> findById(Long id);

    ToDo save(ToDo toDo);

    void deleteById(Long id);
}
