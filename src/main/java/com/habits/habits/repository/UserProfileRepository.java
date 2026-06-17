package com.habits.habits.repository;

import com.habits.habits.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // Пример метода, который ищет всех пользователей по стране
    List<UserProfile> findByCountry(String country);

    // Пример метода с использованием JPQL для получения профилей пользователей с определённой целью и возрастом выше заданного
    @Query("SELECT u FROM UserProfile u WHERE u.goal = :goal AND u.age > :age")
    List<UserProfile> findProfilesWithGoalAndAgeGreaterThan(String goal, int age);

    // Можешь добавить другие методы по мере необходимости
}
