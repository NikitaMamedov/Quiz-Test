package com.example.quiz_application.dto;

public class QuestionAnalysis {
    private Long questionId;
    private String questionText;
    private boolean correct;
    private Integer points;

    public QuestionAnalysis() {}

    public QuestionAnalysis(Long questionId, String questionText, boolean correct, Integer points) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correct = correct;
        this.points = points;
    }

    // Getters and Setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
}
