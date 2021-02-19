package com.davidgomes.todospringboot.controller;

import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;
import com.davidgomes.todospringboot.repository.TodoRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/todo")
@RestController
@CommonsLog
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping(path = "")
    public List<TodoItem> getAllTodoByCurrentUser(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TodoItem.TodoItemStatus status
    ) {
        return todoRepository.findByUserWithOptions(user, search, status);
    }
}
