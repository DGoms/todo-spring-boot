package com.davidgomes.todospringboot.controller;

import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;
import com.davidgomes.todospringboot.repository.TodoRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = "/todo")
@CommonsLog
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping(path = "")
    public Set<TodoItem> getAllTodoByCurrentUser(@AuthenticationPrincipal User user, @RequestParam String search) {
        return todoRepository.findByUserAndTitleContaining(user, search);
    }
}
