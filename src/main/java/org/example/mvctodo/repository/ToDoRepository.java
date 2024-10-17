package org.example.mvctodo.repository;

import org.example.mvctodo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long>, IToDoRepository {

}
