package com.davidgomes.todospringboot.repository;

import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;

import java.util.List;

public interface TodoRepositoryCustom {

    List<TodoItem> findByUserWithOptions(User user, String search, TodoItem.TodoItemStatus status);
}
