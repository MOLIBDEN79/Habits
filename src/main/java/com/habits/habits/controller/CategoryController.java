//package com.habits.habits.controller;
//
//import com.habits.habits.model.Category;
//import com.habits.habits.model.Habit;
//import com.habits.habits.model.PracticeTracker;
//import com.habits.habits.service.CategoryService;
//import com.habits.habits.service.PracticeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Category Controller for handling category management operations.
// * Note: Imported and refactored from todo project
// * @version 1.3.0
// */
//@RestController
//public class CategoryController {
//    private CategoryService categoryService;
//    private PracticeService practiceService;
//
////    @GetMapping("/categories")
////    public String categories() {
////        return "categories";
////    }
//
//    @Autowired
//    public void setCategoryService(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    @Autowired
//    public void setPracticeService(PracticeService practiceService) {
//        this.practiceService = practiceService;
//    }
//
//    @PostMapping(path = "/categories")
//    public Category createCategory(@RequestBody Category categoryObj) {
//        return categoryService.createCategory(categoryObj);
//    }
//
//    @GetMapping(path = "/categories/{categoryId}")
//    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long categoryId) {
//        return categoryService.getCategory(categoryId);
//    }
//
//    @GetMapping(path = "/categories")
//    public List<Category> getCategories() {
//        return categoryService.getAllCategories();
//    }
//
//    @PutMapping(path = "/categories/{categoryId}")
//    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category category) {
//        return categoryService.updateCategory(categoryId, category);
//    }
//
//    @DeleteMapping(path="/categories/{categoryId}")
//    public Optional<Category> deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
//        return categoryService.deleteCategory(categoryId);
//    }
//
//    @PostMapping(path = "/categories/{categoryId}/habits")
//    public Habit createCategoryHabit(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Habit habit) {
//        return categoryService.createCategoryHabit(categoryId, habit);
//    }
//
//    @GetMapping(path = "/categories/{categoryId}/habits/{habitId}")
//    public Optional<Habit> getHabit(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "habitId") Long habitId) {
//        return Optional.ofNullable(categoryService.getHabitByIdAndCategory(categoryId, habitId));
//    }
//
//    @GetMapping(path = "/habits")
//    public List<Habit> getAllHabits() {
//        return categoryService.getAllHabits();
//    }
//
//    @PutMapping(path = "/habits/{habitId}")
//    public Optional<Habit> updateHabit(@PathVariable(value = "habitId") Long habitId, @RequestBody Habit habit) throws IllegalAccessException {
//        habit.setId(habitId);
//        return Optional.ofNullable(categoryService.updateHabit(habit));
//    }
//
//    @DeleteMapping(path = "/categories/{categoryId}/habits/{habitId}")
//    public Optional<Habit> deleteHabit(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "habitId") Long habitId) {
//        return categoryService.deleteHabit(categoryId, habitId);
//    }
//
//    @PostMapping(path = "/habits/{habitId}/practices")
//    public PracticeTracker createPractice(@PathVariable(value = "habitId") Long habitId, @RequestBody PracticeTracker practiceTracker) {
//        return practiceService.createPractice(habitId, practiceTracker);
//    }
//
//    @GetMapping(path = "/practices/{habitId}")
//    public PracticeTracker getPracticeById(@PathVariable(value = "habitId") Long habitId) {
//        return practiceService.getPracticeById(habitId);
//    }
//
//    @GetMapping(path = "/practices/date/{date}")
//    public List<PracticeTracker> getPracticeByDate(@PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
//        return practiceService.getPracticeByDate(date);
//    }
//
//    @GetMapping(path = "/practices")
//    public List<PracticeTracker> getAllPractices() {
//        return practiceService.getAllPractices();
//    }
//
//    @PutMapping(path = "/practices/{practiceId}")
//    public PracticeTracker updatePractice(@PathVariable(value = "practiceId") Long practiceId, @RequestBody PracticeTracker practice) throws IllegalAccessException {
//        practice.setId(practiceId);
//        return practiceService.updatePractice(practice);
//    }
//
//    @DeleteMapping(path = "/practices/{practiceId}")
//    public Optional<PracticeTracker> deletePractice(@PathVariable(value = "practiceId") Long practiceId) {
//        return practiceService.deletePractice(practiceId);
//    }
//}



package com.habits.habits.controller;

import com.habits.habits.model.Category;
import com.habits.habits.model.User;
import com.habits.habits.model.Habit;
import com.habits.habits.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/form")
    public String category_form() {
        return "/form";
    }

    @GetMapping("/list")
    public String listCategories(@AuthenticationPrincipal User user, Model model) {
        List<Category> categories = categoryService.getUserCategories(user);
        model.addAttribute("categories", categories);
        return "/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "/form";
    }

    @PostMapping("/add")
    public String addCategory(@AuthenticationPrincipal User user, @ModelAttribute("category") Category category) {
        category.setUser(user);
        categoryService.saveCategory(category);
        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(category);
        return "redirect:/list";
    }

//    @GetMapping("/users")
//    public String getUserDetails(@RequestParam Long userId, Model model) {
//        // Получаем пользователя по ID
//        User user = userService.getUserById(userId);
//
//        // Получаем список категорий и привычек пользователя
//        List<Category> categories = user.getCategoryList();
//        List<Habit> habits = user.getHabitList();
//
//        // Передаем данные в представление
//        model.addAttribute("user", user);
//        model.addAttribute("categories", categories);
//        model.addAttribute("habits", habits);
//
//        return "userDetails";
//    }
}
