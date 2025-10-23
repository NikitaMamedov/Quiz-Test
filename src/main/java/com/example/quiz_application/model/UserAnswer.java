package com.example.quiz_application.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private Attempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_option_id", nullable = false)
    private AnswerOption selectedAnswer;

    // Конструкторы
    public UserAnswer() {}

    public UserAnswer(Attempt attempt, Question question, AnswerOption selectedAnswer) {
        this.attempt = attempt;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Attempt getAttempt() { return attempt; }
    public void setAttempt(Attempt attempt) { this.attempt = attempt; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public AnswerOption getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(AnswerOption selectedAnswer) { this.selectedAnswer = selectedAnswer; }
}