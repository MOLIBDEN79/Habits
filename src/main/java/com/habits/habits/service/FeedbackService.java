package com.habits.habits.service;

import com.habits.habits.model.Feedback;
import com.habits.habits.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void saveFeedback(Feedback feedback) {
        feedback.setSubmitDate(LocalDateTime.now());
        feedbackRepository.save(feedback);
    }

    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> findRecentFeedbacks() {
        LocalDateTime fifteenDaysAgo = LocalDateTime.now().minusDays(15);
        return feedbackRepository.findBySubmitDateGreaterThanEqual(fifteenDaysAgo);
    }
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }


}



