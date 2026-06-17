package com.habits.habits.service;

import com.habits.habits.model.Habit;
import com.habits.habits.model.User;
import com.habits.habits.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    @Autowired
    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getUserHabits(User user) {
        return habitRepository.findByUser(user);
    }

    public void saveHabit(Habit habit) {
        habitRepository.save(habit);
    }

    public void deleteHabit(Habit habit) {
        habitRepository.delete(habit);
    }

    public void updateHabit(Habit habit) {
        habitRepository.save(habit);
    }

    public Habit getHabitById(Long id) {
        return habitRepository.findById(id).orElse(null);
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

}
