package com.example.quiz_application.dto;

import com.example.quiz_application.model.Quiz;

public class UserProgress {
    private Long userId;
    private Long totalAttempts;
    private Long completedAttempts;
    private Double averageScore;
    private Quiz favoriteQuiz;

    public UserProgress() {}

    public UserProgress(Long userId, Long totalAttempts, Long completedAttempts,
                        Double averageScore, Quiz favoriteQuiz) {
        this.userId = userId;
        this.totalAttempts = totalAttempts;
        this.completedAttempts = completedAttempts;
        this.averageScore = averageScore;
        this.favoriteQuiz = favoriteQuiz;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Long totalAttempts) { this.totalAttempts = totalAttempts; }

    public Long getCompletedAttempts() { return completedAttempts; }
    public void setCompletedAttempts(Long completedAttempts) { this.completedAttempts = completedAttempts; }

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }

    public Quiz getFavoriteQuiz() { return favoriteQuiz; }
    public void setFavoriteQuiz(Quiz favoriteQuiz) { this.favoriteQuiz = favoriteQuiz; }
}

