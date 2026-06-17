package com.habits.habits.repository;

import com.habits.habits.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
