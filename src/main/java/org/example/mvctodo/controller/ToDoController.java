package org.example.mvctodo.controller;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    // Display the list of To-Do items
    @GetMapping("/")
    public String listToDos(Model model) {
        model.addAttribute("todos", toDoService.getAllToDos());
        return "index";
    }

    // Show the form for adding a new To-Do
    @GetMapping("/add")
    public String addToDoForm(Model model) {
        model.addAttribute("todo", new ToDo());
        return "add-todo";
    }

    // Save a new To-Do with validation
    @PostMapping("/save")
    public String saveToDo(@Valid @ModelAttribute ToDo toDo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-todo"; // Return to the form with error messages
        }
        toDoService.saveToDo(toDo);
        return "redirect:/";
    }

    // Show the form for editing an existing To-Do
    @GetMapping("/edit/{id}")
    public String editToDoForm(@PathVariable Long id, Model model) {
        ToDo todo = toDoService.getToDoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid To-Do ID: " + id));
        model.addAttribute("todo", todo);
        return "edit-todo";
    }

    // Update an existing To-Do with validation
    @PostMapping("/update")
    public String updateToDo(@Valid @ModelAttribute ToDo toDo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-todo"; // Return to the form with error messages
        }
        toDoService.saveToDo(toDo);
        return "redirect:/";
    }

    // Delete a To-Do
    @GetMapping("/delete/{id}")
    public String deleteToDo(@PathVariable Long id) {
        toDoService.deleteToDoById(id);
        return "redirect:/";
    }
}
