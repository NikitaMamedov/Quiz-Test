package com.example.quiz_application.dto;

import com.example.quiz_application.model.Quiz;

public class QuizStatistics {
    private Quiz quiz;
    private Long totalAttempts;
    private Double averageScore;
    private Integer maxPossibleScore;

    public QuizStatistics() {}

    public QuizStatistics(Quiz quiz, Long totalAttempts, Double averageScore, Integer maxPossibleScore) {
        this.quiz = quiz;
        this.totalAttempts = totalAttempts;
        this.averageScore = averageScore;
        this.maxPossibleScore = maxPossibleScore;
    }

    // Getters and Setters
    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public Long getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Long totalAttempts) { this.totalAttempts = totalAttempts; }

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }

    public Integer getMaxPossibleScore() { return maxPossibleScore; }
    public void setMaxPossibleScore(Integer maxPossibleScore) { this.maxPossibleScore = maxPossibleScore; }
}
