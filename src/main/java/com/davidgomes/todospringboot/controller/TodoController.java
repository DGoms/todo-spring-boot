package com.davidgomes.todospringboot.controller;

import com.davidgomes.todospringboot.exception.EntityNotFoundException;
import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;
import com.davidgomes.todospringboot.repository.TodoRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = "/todo")
@RestController
@CommonsLog
public class TodoController extends BaseController {

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

    @PostMapping(path = "")
    public TodoItem addTodo(@AuthenticationPrincipal User user, @RequestBody @Valid TodoItem todoItem) {
        todoItem.setUser(user);
        return todoRepository.save(todoItem);
    }

    @PutMapping(path = "/{id}")
    public TodoItem editTodo(@PathVariable Integer id, @AuthenticationPrincipal User user, @RequestBody @Valid TodoItem todoChanges) {
        return todoRepository.findByIdAndUser(id, user)
                .map(todo -> {
                    todo.setTitle(todoChanges.getTitle());
                    todo.setDescription(todoChanges.getDescription());
                    todo.setStatus(todoChanges.getStatus());

                    return todoRepository.save(todo);
                })
                .orElseGet(() -> {
                    throw new EntityNotFoundException(TodoItem.class, id);
                });
    }


    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        todoRepository.deleteById(id);
    }
}
