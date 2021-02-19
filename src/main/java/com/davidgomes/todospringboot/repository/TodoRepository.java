package com.davidgomes.todospringboot.repository;

import com.davidgomes.todospringboot.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoItem, Integer>, TodoRepositoryCustom {

}
