package org.example.mvctodo.service;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.IToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    private final IToDoRepository toDoRepository;

    @Autowired
    public ToDoService(IToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    public Optional<ToDo> getToDoById(Long id) {
        return toDoRepository.findById(id);
    }

    public ToDo saveToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public void deleteToDoById(Long id) {
        toDoRepository.deleteById(id);
    }

    // Manually filter out pending To-Dos
    public List<ToDo> getPendingToDos() {
        return getAllToDos().stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }

    // Manually filter out completed To-Dos
    public List<ToDo> getCompletedToDos() {
        return getAllToDos().stream()
                .filter(ToDo::isCompleted)
                .collect(Collectors.toList());
    }

    // Mark a To-Do as complete
    public void markAsComplete(Long id) {
        ToDo todo = toDoRepository.findById(id).orElseThrow();
        todo.setCompleted(true);
        toDoRepository.save(todo);
    }

    // Mark a To-Do as pending (reopen it)
    public void markAsPending(Long id) {
        ToDo todo = toDoRepository.findById(id).orElseThrow();
        todo.setCompleted(false);
        toDoRepository.save(todo);
    }
}
