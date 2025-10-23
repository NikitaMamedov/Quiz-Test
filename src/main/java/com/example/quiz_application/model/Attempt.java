package com.example.quiz_application.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attempts")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private Integer score = 0;

    private Integer maxScore;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status = AttemptStatus.IN_PROGRESS;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswer> userAnswers = new ArrayList<>();

    // Конструкторы
    public Attempt() {
        this.startedAt = LocalDateTime.now();
    }

    public Attempt(User user, Quiz quiz) {
        this();
        this.user = user;
        this.quiz = quiz;
        this.maxScore = calculateMaxScore(quiz);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }

    public AttemptStatus getStatus() { return status; }
    public void setStatus(AttemptStatus status) { this.status = status; }

    public List<UserAnswer> getUserAnswers() { return userAnswers; }
    public void setUserAnswers(List<UserAnswer> userAnswers) { this.userAnswers = userAnswers; }

    // Методы
    private Integer calculateMaxScore(Quiz quiz) {
        return quiz.getQuestions().stream()
                .mapToInt(Question::getPoints)
                .sum();
    }

    public void complete() {
        this.status = AttemptStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public enum AttemptStatus {
        IN_PROGRESS, COMPLETED, TIMED_OUT
    }
}
