package org.example.mvctodo.controller;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/")
    public String listToDos(Model model) {
        model.addAttribute("todos", toDoRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String addToDoForm(Model model) {
        model.addAttribute("todo", new ToDo());
        return "add-todo";
    }

    @PostMapping("/save")
    public String saveToDo(@ModelAttribute ToDo todo) {
        toDoRepository.save(todo);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editToDoForm(@PathVariable Long id, Model model) {
        ToDo todo = toDoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid to-do ID:" + id));
        model.addAttribute("todo", todo);
        return "edit-todo";
    }

    @PostMapping("/update/{id}")
    public String updateToDo(@PathVariable Long id, @ModelAttribute ToDo todo) {
        todo.setId(id);
        toDoRepository.save(todo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteToDo(@PathVariable Long id) {
        toDoRepository.deleteById(id);
        return "redirect:/";
    }
}
