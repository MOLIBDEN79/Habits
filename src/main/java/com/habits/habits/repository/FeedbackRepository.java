package com.habits.habits.repository;

import com.habits.habits.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findBySubmitDateGreaterThanEqual(LocalDateTime date);


}
