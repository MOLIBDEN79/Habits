package com.habits.habits.controller;

import com.habits.habits.model.User;
import com.habits.habits.repository.UserRepository;
import com.habits.habits.service.UserService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    private UserService userService;



    @GetMapping("/admin")
    public String adminPanel(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "admin-panel";
    }

    @PostMapping("/admin/{id}/remove")
    public String userDelete(@PathVariable(value = "id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        model.addAttribute("user", user); // Используйте "user" вместо "users", так как это одиночный объект
        return "user-edit";
    }

    @PostMapping("/admin/{id}/edit")
    public String saveUser(@PathVariable("id") Long id, @RequestParam String username1, @RequestParam String password1, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setUsername(username1);
        user.setPassword(password1);
        userRepository.save(user);
        return "redirect:/admin";
    }



}
