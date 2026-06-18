package com.habits.habits.controller;

import com.habits.habits.model.Habit;
import com.habits.habits.model.PracticeTracker;
import com.habits.habits.model.User;
import com.habits.habits.model.UserHabitLevel;
import com.habits.habits.model.UserProfile;
import com.habits.habits.repository.HabitRepository;
import com.habits.habits.repository.UserHabitLevelRepository;
import com.habits.habits.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserHabitLevelRepository userHabitLevelRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("currentUser", currentUser);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isAdmin", authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin")));

        // ===== Привычки пользователя =====
        List<Habit> habits = habitRepository.findByUser(currentUser);
        model.addAttribute("habits", habits);
        model.addAttribute("habitsCount", habits.size());

        // ===== Вода 💧 = общее число выполненных отметок =====
        // ===== Серия дней = подряд идущие дни с выполнением до сегодня =====
        int water = 0;
        Set<LocalDate> completedDays = new HashSet<>();
        for (Habit habit : habits) {
            List<PracticeTracker> trackers = habit.getPracticeTrackerList();
            if (trackers == null) continue;
            for (PracticeTracker tracker : trackers) {
                if (tracker.isCompleted()) {
                    water++;
                    if (tracker.getDate() != null) {
                        completedDays.add(tracker.getDate());
                    }
                }
            }
        }
        model.addAttribute("water", water);

        int streak = 0;
        LocalDate cursor = LocalDate.now();
        // Если сегодня ещё не отмечено — серию считаем со вчерашнего дня
        if (!completedDays.contains(cursor)) {
            cursor = cursor.minusDays(1);
        }
        while (completedDays.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }
        model.addAttribute("streak", streak);

        // ===== Уровень кактуса =====
        UserHabitLevel levelEntity = userHabitLevelRepository.findByUser(currentUser);
        int level;
        if (levelEntity != null && levelEntity.getHabitLevel() > 0) {
            level = levelEntity.getHabitLevel();
        } else {
            // Производный уровень: каждые 10 капель воды = новый уровень, максимум 10
            level = Math.min(10, water / 10 + 1);
        }
        model.addAttribute("level", level);
        model.addAttribute("levelMax", 10);

        // ===== Биография =====
        UserProfile userProfile = userProfileRepository.findById(currentUser.getId()).orElse(null);
        model.addAttribute("userProfile", userProfile);

        // Названия привычек для тегов
        List<String> habitNames = habits.stream()
                .map(Habit::getName)
                .filter(name -> name != null && !name.isBlank())
                .collect(Collectors.toList());
        model.addAttribute("habitNames", habitNames);

        return "profile";
    }
}
