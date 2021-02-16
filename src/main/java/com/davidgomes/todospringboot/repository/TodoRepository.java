package com.davidgomes.todospringboot.repository;

import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TodoRepository extends JpaRepository<TodoItem, Integer> {

    Set<TodoItem> findByUserAndTitleContaining(User user, String title);
}
