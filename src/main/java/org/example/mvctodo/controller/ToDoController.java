package org.example.mvctodo.controller;

import org.example.mvctodo.model.ToDo;
import org.example.mvctodo.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
         this.toDoService = toDoService;
    }

    // Display the list of To-Do items, separated into pending and completed
    @GetMapping("/")
    public String listToDos(Model model) {
        model.addAttribute("pendingTodos", toDoService.getPendingToDos());
        model.addAttribute("completedTodos", toDoService.getCompletedToDos());
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
            model.addAttribute("todo", toDo);
            model.addAttribute("errors", result.getAllErrors());
            return "add-todo";
        }
        toDoService.saveToDo(toDo);
        return "redirect:/";
    }

    // Mark a To-Do as completed
    @GetMapping("/complete/{id}")
    public String completeToDo(@PathVariable Long id) {
        toDoService.markAsComplete(id);
        return "redirect:/";
    }

    // Reopen a To-Do (mark as pending)
    @GetMapping("/reopen/{id}")
    public String reopenToDo(@PathVariable Long id) {
        toDoService.markAsPending(id);
        return "redirect:/";
    }
    // Delete a To-Do
    // Delete a To-Do
    @GetMapping("/delete/{id}")
    public String deleteToDo(@PathVariable Long id) {
        toDoService.deleteToDoById(id);
        return "redirect:/";
    }
}
