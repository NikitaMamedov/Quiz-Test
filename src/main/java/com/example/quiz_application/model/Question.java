package com.example.quiz_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type = QuestionType.SINGLE_CHOICE;

    private Integer points = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    // Конструкторы
    public Question() {}

    public Question(String text, Quiz quiz) {
        this.text = text;
        this.quiz = quiz;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public List<AnswerOption> getAnswerOptions() { return answerOptions; }
    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public enum QuestionType {
        SINGLE_CHOICE, MULTIPLE_CHOICE
    }
}