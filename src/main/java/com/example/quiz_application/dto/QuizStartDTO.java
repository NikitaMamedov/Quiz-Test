package com.example.quiz_application.dto;

public class QuizStartDTO {
    private Long userId;
    private Long quizId;

    // Конструкторы
    public QuizStartDTO() {}

    public QuizStartDTO(Long userId, Long quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
}
