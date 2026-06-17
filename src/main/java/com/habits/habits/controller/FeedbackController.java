package com.habits.habits.controller;

import com.habits.habits.model.Feedback;
import com.habits.habits.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedback")
    public String showFeedbackForm(Model model) {
        // Модель для формы
        model.addAttribute("feedback", new Feedback());
        return "feedback";
    }



    @PostMapping("/feedback")
    public String submitFeedback(@ModelAttribute Feedback feedback, RedirectAttributes redirectAttrs) {
        try {
            feedbackService.saveFeedback(feedback);
            redirectAttrs.addFlashAttribute("success", "Сообщение отправлено.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Ошибка.");
        }
        return "redirect:/feedback";
    }
    @GetMapping("/feedbacks")
    public String showFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.findAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedbacks";
    }
    @GetMapping("/delete-feedback/{id}")
    public String deleteFeedback(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            feedbackService.deleteFeedback(id);
            redirectAttrs.addFlashAttribute("success", "Feedback has been deleted successfully.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "There was a problem deleting the feedback.");
        }
        return "redirect:/feedbacks";
    }


}



