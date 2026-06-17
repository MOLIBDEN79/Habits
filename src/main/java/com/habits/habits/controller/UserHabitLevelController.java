package com.habits.habits.controller;

import com.habits.habits.service.UserHabitLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.habits.habits.model.User;

@RestController
public class UserHabitLevelController {

    private final UserHabitLevelService userHabitLevelService;

    @Autowired
    public UserHabitLevelController(UserHabitLevelService userHabitLevelService) {
        this.userHabitLevelService = userHabitLevelService;
    }



}
