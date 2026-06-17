package com.habits.habits.controller;

import com.habits.habits.model.User;
import com.habits.habits.repository.UserRepository;
import com.habits.habits.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;


@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    public DashboardController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        return "home";
    }

    @GetMapping("/user")
    public String user( Model model) {

        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Неверное имя пользователя или пароль!");
        }
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/achievement")
    public String achievement() {
        return "achievement";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }
    @GetMapping("/start")
    public String menu() {
        return "start";
    }



    @GetMapping("/game_shablon")
    public String game_shablon() {
        return "game_shablon";
    }

    @GetMapping("/game1")
    public String game1() {
        return "game1";
    }

    @GetMapping("/game2")
    public String game2() {
        return "game2";
    }

    @GetMapping("/game3")
    public String game3() {
        return "game3";
    }

    @GetMapping("/instructions")
    public String instructions() {
        return "instructions";
    }

    @GetMapping("/payment")
    public String payment() {return "payment";
    }


    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password, Map<String, Object> model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.put("message", "Пользователь с данным именем уже существует");
            return "register";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";


    }


}