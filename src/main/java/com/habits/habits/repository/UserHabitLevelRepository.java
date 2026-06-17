package com.habits.habits.repository;

import com.habits.habits.model.User;
import com.habits.habits.model.UserHabitLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHabitLevelRepository extends JpaRepository<UserHabitLevel, Long> {

    UserHabitLevel findByUser(User user);

}
