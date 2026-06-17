package com.habits.habits.controller;

import com.habits.habits.exception.HabitNotFoundException;
import com.habits.habits.model.Category;
import com.habits.habits.model.Habit;
import com.habits.habits.model.User;
import com.habits.habits.service.CategoryService;
import com.habits.habits.service.HabitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.habits.habits.repository.HabitRepository;

@Controller
@RequestMapping("/habits")
public class HabitController {
    private final HabitService habitService;
    private final CategoryService categoryService;

    @Autowired
    private HabitRepository habitRepository; // Добавляем аннотацию @Autowired

    private static final Logger logger = LoggerFactory.getLogger(HabitController.class);

    @Autowired
    public HabitController(HabitService habitService, CategoryService categoryService) {
        this.habitService = habitService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public String showHabits(@AuthenticationPrincipal User user, Model model) {
        List<Habit> habits = habitRepository.findByUser(user); // Используем метод findByUser, предполагая, что он есть в вашем репозитории
        model.addAttribute("habits", habits);
        return "habit/all";
    }
    @GetMapping("/list")
    public String listHabits(@AuthenticationPrincipal User user, Model model) {
        List<Habit> habits = habitService.getUserHabits(user);
        model.addAttribute("habits", habits);
        return "habit/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("habit", new Habit());
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "habit/form";
    }


    @PostMapping("/add")
    @Transactional
    public String addHabit(@AuthenticationPrincipal User user, @ModelAttribute("habit") Habit habit, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.error(error.toString()));
            redirectAttributes.addFlashAttribute("error", "Validation error");
            return "redirect:/habits/add";
        }
        try {
            logger.debug("Saving habit: {}", habit);
            habit.setUser(user);
            habitService.saveHabit(habit);
            redirectAttributes.addFlashAttribute("success", "Habit added successfully!");
        } catch (Exception e) {
            logger.error("Error while adding habit", e);
            redirectAttributes.addFlashAttribute("error", "Failed to add habit: " + e.getMessage());
        }
        return "redirect:/habits/list";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Habit habit = habitService.getHabitById(id);
            model.addAttribute("habit", habit);
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
        } catch (HabitNotFoundException e) {
            model.addAttribute("error", "Habit not found");
            return "redirect:/habits/list";
        }
        return "habit/form";
    }

    @PostMapping("/edit/{id}")
    @Transactional
    public String updateHabit(@PathVariable("id") Long id, @ModelAttribute("habit") Habit habit, RedirectAttributes redirectAttributes) {
        try {
            habit.setId(id);
            habitService.updateHabit(habit);
            redirectAttributes.addFlashAttribute("success", "Habit updated successfully!");
        } catch (Exception e) {
            logger.error("Error while updating habit", e);
            redirectAttributes.addFlashAttribute("error", "Failed to update habit: " + e.getMessage());
        }
        return "redirect:/habits/list";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteHabit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Habit habit = habitService.getHabitById(id);
            habitService.deleteHabit(habit);
            redirectAttributes.addFlashAttribute("success", "Habit deleted successfully!");
        } catch (Exception e) {
            logger.error("Error while deleting habit", e);
            redirectAttributes.addFlashAttribute("error", "Failed to delete habit: " + e.getMessage());
        }
        return "redirect:/habits/list";
    }

    @GetMapping("/habits")
    public String getHabitsByCategory(Model model) {
        List<Habit> allHabits = habitService.getAllHabits();

        // Группируем привычки по категориям
        Map<Category, List<Habit>> habitsByCategory = allHabits.stream()
                .collect(Collectors.groupingBy(Habit::getCategory));

        model.addAttribute("habitsByCategory", habitsByCategory);
        return "habits";
    }
    @ExceptionHandler(HabitNotFoundException.class)
    public String handleHabitNotFound(HabitNotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "The habit was not found.");
        return "redirect:/habits/list";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
        return "redirect:/habits/list";
    }
}
