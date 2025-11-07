package com.example.quiz_application.dto;

import java.util.List;

public class AttemptAnalytics {
    private Long attemptId;
    private int totalQuestions;
    private int correctAnswers;
    private double correctPercentage;
    private Integer score;
    private Integer maxScore;
    private List<QuestionAnalysis> questionAnalysis;

    public AttemptAnalytics() {}

    public AttemptAnalytics(Long attemptId, int totalQuestions, int correctAnswers,
                            double correctPercentage, Integer score, Integer maxScore,
                            List<QuestionAnalysis> questionAnalysis) {
        this.attemptId = attemptId;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.correctPercentage = correctPercentage;
        this.score = score;
        this.maxScore = maxScore;
        this.questionAnalysis = questionAnalysis;
    }

    // Getters and Setters
    public Long getAttemptId() { return attemptId; }
    public void setAttemptId(Long attemptId) { this.attemptId = attemptId; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public double getCorrectPercentage() { return correctPercentage; }
    public void setCorrectPercentage(double correctPercentage) { this.correctPercentage = correctPercentage; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }

    public List<QuestionAnalysis> getQuestionAnalysis() { return questionAnalysis; }
    public void setQuestionAnalysis(List<QuestionAnalysis> questionAnalysis) { this.questionAnalysis = questionAnalysis; }
}
