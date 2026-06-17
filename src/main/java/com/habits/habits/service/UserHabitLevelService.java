package com.habits.habits.service;

import com.habits.habits.model.User;
import com.habits.habits.model.UserHabitLevel;
import com.habits.habits.repository.UserHabitLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHabitLevelService {

    private final UserHabitLevelRepository userHabitLevelRepository;

    @Autowired
    public UserHabitLevelService(UserHabitLevelRepository userHabitLevelRepository) {
        this.userHabitLevelRepository = userHabitLevelRepository;
    }

    public int getHabitLevelForUser(User user) {
        UserHabitLevel userHabitLevel = userHabitLevelRepository.findByUser(user);
        if (userHabitLevel != null) {
            return userHabitLevel.getHabitLevel();
        } else {
            // Если у пользователя нет записи об уровне привычки, возвращаем 1 (по умолчанию)
            return 1;
        }
    }

    public int increaseHabitLevelForCurrentUser(User user) {
        UserHabitLevel userHabitLevel = userHabitLevelRepository.findByUser(user);
        if (userHabitLevel != null) {
            int currentLevel = userHabitLevel.getHabitLevel();
            if (currentLevel < 10) {
                userHabitLevel.setHabitLevel(currentLevel + 1);
                userHabitLevelRepository.save(userHabitLevel);
                return currentLevel + 1;
            }
        } else {
            // Если у пользователя нет записи об уровне привычки, создаем новую
            userHabitLevel = new UserHabitLevel();
            userHabitLevel.setUser(user);
            userHabitLevel.setHabitLevel(1);
            userHabitLevelRepository.save(userHabitLevel);
            return 1;
        }
        // Возвращаем текущий уровень, если уровень не увеличился
        return userHabitLevel.getHabitLevel();
    }
}
