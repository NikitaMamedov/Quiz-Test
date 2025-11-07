package com.example.quiz_application.controller;

import com.example.quiz_application.dto.*;
import com.example.quiz_application.model.Quiz;
import com.example.quiz_application.service.QuizService;
import com.example.quiz_application.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AttemptService attemptService;

    // Бизнес-операция 1: Статистика по тесту
    @GetMapping("/quizzes/{quizId}/statistics")
    public QuizStatistics getQuizStatistics(@PathVariable Long quizId) {
        return quizService.getQuizStatistics(quizId);
    }

    // Бизнес-операция 2: Клонирование теста
    @PostMapping("/quizzes/{quizId}/clone")
    public Quiz cloneQuiz(@PathVariable Long quizId, @RequestParam String newTitle) {
        return quizService.cloneQuiz(quizId, newTitle);
    }

    // Бизнес-операция 3: Аналитика попытки
    @GetMapping("/attempts/{attemptId}/analytics")
    public AttemptAnalytics getAttemptAnalytics(@PathVariable Long attemptId) {
        return attemptService.getAttemptAnalytics(attemptId);
    }

    // Бизнес-операция 4: Сравнение пользователей
    @GetMapping("/compare-users")
    public UserComparison compareUsers(
            @RequestParam Long userId1,
            @RequestParam Long userId2,
            @RequestParam Long quizId) {
        return attemptService.compareUsers(userId1, userId2, quizId);
    }

    // Бизнес-операция 5: Прогресс пользователя
    @GetMapping("/users/{userId}/progress")
    public UserProgress getUserProgress(@PathVariable Long userId) {
        return attemptService.getUserProgress(userId);
    }
}
