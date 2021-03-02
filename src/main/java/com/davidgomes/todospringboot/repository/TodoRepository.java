package com.davidgomes.todospringboot.repository;

import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoItem, Integer>, TodoRepositoryCustom {
    Optional<TodoItem> findByIdAndUser(Integer id, User user);

    void deleteByIdAndUser(Integer id, User user);
}
