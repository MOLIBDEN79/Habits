package com.habits.habits.service;

import com.habits.habits.model.UserProfile;
import com.habits.habits.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository,
                              FileStorageService fileStorageService) {
        this.userProfileRepository = userProfileRepository;
        this.fileStorageService = fileStorageService;
    }

    public Optional<UserProfile> findById(Long userId) {
        return userProfileRepository.findById(userId);
    }

    @Transactional
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Transactional
    public UserProfile updateUserProfile(Long userId, UserProfile userProfileDetails, MultipartFile avatarFile) {
        return userProfileRepository.findById(userId).map(existingUserProfile -> {
            updateExistingUserProfile(existingUserProfile, userProfileDetails);
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = fileStorageService.storeFile(avatarFile);
                existingUserProfile.setAvatarPath(avatarPath);
            }
            return userProfileRepository.save(existingUserProfile);
        }).orElseThrow(() -> new RuntimeException("Profile not found for id: " + userId));
    }

    private void updateExistingUserProfile(UserProfile existingUserProfile, UserProfile userProfileDetails) {
        existingUserProfile.setNickname(userProfileDetails.getNickname());
        existingUserProfile.setAboutMe(userProfileDetails.getAboutMe());
        existingUserProfile.setBirthDate(userProfileDetails.getBirthDate());
        existingUserProfile.setAge(userProfileDetails.getAge());
        existingUserProfile.setCountry(userProfileDetails.getCountry());
        existingUserProfile.setHeight(userProfileDetails.getHeight());
        existingUserProfile.setEducation(userProfileDetails.getEducation());
        existingUserProfile.setHobbies(userProfileDetails.getHobbies());
        existingUserProfile.setProfessionalInterests(userProfileDetails.getProfessionalInterests());
        existingUserProfile.setPersonalAchievements(userProfileDetails.getPersonalAchievements());
        existingUserProfile.setGoal(userProfileDetails.getGoal());
        existingUserProfile.setFavoriteQuote(userProfileDetails.getFavoriteQuote());
    }

    public void deleteById(Long userId) {
        userProfileRepository.deleteById(userId);
    }
}
