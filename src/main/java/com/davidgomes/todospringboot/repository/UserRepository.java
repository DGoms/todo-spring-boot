package com.davidgomes.todospringboot.repository;

import com.davidgomes.todospringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}