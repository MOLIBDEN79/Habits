package com.habits.habits.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_profile", schema = "habits_corp")
public class UserProfile {
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "age")
    private Integer age;

    @Column(name = "country")
    private String country;

    @Column(name = "height")
    private Integer height;

    @Column(name = "education")
    private String education;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "professional_interests")
    private String professionalInterests;

    @Column(name = "personal_achievements")
    private String personalAchievements;

    @Column(name = "goal")
    private String goal;

    @Column(name = "favorite_quote")
    private String favoriteQuote;

    @Column(name = "avatar_path")
    private String avatarPath;
    // Геттер и сеттер для поля userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Геттер и сеттер для поля nickname
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // Геттер и сеттер для поля aboutMe
    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    // Геттер и сеттер для поля birthDate
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Геттер и сеттер для поля age
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // Геттер и сеттер для поля country
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Геттер и сеттер для поля height
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    // Геттер и сеттер для поля education
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    // Геттер и сеттер для поля hobbies
    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    // Геттер и сеттер для поля professionalInterests
    public String getProfessionalInterests() {
        return professionalInterests;
    }

    public void setProfessionalInterests(String professionalInterests) {
        this.professionalInterests = professionalInterests;
    }

    // Геттер и сеттер для поля personalAchievements
    public String getPersonalAchievements() {
        return personalAchievements;
    }

    public void setPersonalAchievements(String personalAchievements) {
        this.personalAchievements = personalAchievements;
    }

    // Геттер и сеттер для поля goal
    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    // Геттер и сеттер для поля favoriteQuote
    public String getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(String favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

}


