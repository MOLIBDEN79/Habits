package com.habits.habits.controller;

import org.springframework.http.HttpStatus;
import com.habits.habits.model.UserProfile;
import com.habits.habits.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable Long userId, Model model) {
        Optional<UserProfile> userProfileOptional = userProfileService.findById(userId);
        if (!userProfileOptional.isPresent()) {
            throw new RuntimeException("Profile not found for id: " + userId);
        }
        model.addAttribute("userProfile", userProfileOptional.get());
        return "profile";
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) {
        UserProfile savedUserProfile = userProfileService.save(userProfile);
        return new ResponseEntity<>(savedUserProfile, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public String updateUserProfile(
            @RequestParam("userId") Long userId,
            @RequestParam("nickname") String nickname,
            @RequestParam("aboutMe") String aboutMe,
            @RequestParam("birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDate,
            @RequestParam("age") Integer age,
            @RequestParam("country") String country,
            @RequestParam("height") Integer height,
            @RequestParam("education") String education,
            @RequestParam("hobbies") String hobbies,
            @RequestParam("professionalInterests") String professionalInterests,
            @RequestParam("personalAchievements") String personalAchievements,
            @RequestParam("goal") String goal,
            @RequestParam("favoriteQuote") String favoriteQuote,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile,
            RedirectAttributes redirectAttributes) {

        UserProfile userProfileDetails = new UserProfile();
        userProfileDetails.setUserId(userId); // Ensure this field is managed correctly for persistence
        userProfileDetails.setNickname(nickname);
        userProfileDetails.setAboutMe(aboutMe);
        userProfileDetails.setBirthDate(birthDate);
        userProfileDetails.setAge(age);
        userProfileDetails.setCountry(country);
        userProfileDetails.setHeight(height);
        userProfileDetails.setEducation(education);
        userProfileDetails.setHobbies(hobbies);
        userProfileDetails.setProfessionalInterests(professionalInterests);
        userProfileDetails.setPersonalAchievements(personalAchievements);
        userProfileDetails.setGoal(goal);
        userProfileDetails.setFavoriteQuote(favoriteQuote);

        UserProfile updatedUserProfile = userProfileService.updateUserProfile(userId, userProfileDetails, avatarFile);
        redirectAttributes.addFlashAttribute("successMessage", "Профиль успешно обновлен!");
        return "redirect:/profile/" + userId;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfile> updateUserProfileJson(
            @PathVariable Long userId,
            @RequestBody UserProfile userProfile,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {

        UserProfile updatedUserProfile = userProfileService.updateUserProfile(userId, userProfile, avatarFile);
        return ResponseEntity.ok(updatedUserProfile);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long userId) {
        userProfileService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
