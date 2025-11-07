package com.example.quiz_application.dto;

import com.example.quiz_application.model.Attempt;

public class UserComparison {
    private Attempt user1BestAttempt;
    private Attempt user2BestAttempt;
    private String comparisonResult;

    public UserComparison() {}

    public UserComparison(Attempt user1BestAttempt, Attempt user2BestAttempt) {
        this.user1BestAttempt = user1BestAttempt;
        this.user2BestAttempt = user2BestAttempt;
        this.comparisonResult = calculateComparison();
    }

    private String calculateComparison() {
        if (user1BestAttempt == null && user2BestAttempt == null) {
            return "Оба пользователя не проходили тест";
        }
        if (user1BestAttempt == null) {
            return "Пользователь 2 имеет лучший результат";
        }
        if (user2BestAttempt == null) {
            return "Пользователь 1 имеет лучший результат";
        }

        int score1 = user1BestAttempt.getScore();
        int score2 = user2BestAttempt.getScore();

        if (score1 > score2) {
            return "Пользователь 1 имеет лучший результат: " + score1 + " vs " + score2;
        } else if (score2 > score1) {
            return "Пользователь 2 имеет лучший результат: " + score2 + " vs " + score1;
        } else {
            return "Оба пользователя имеют одинаковый результат: " + score1;
        }
    }

    // Getters and Setters
    public Attempt getUser1BestAttempt() { return user1BestAttempt; }
    public void setUser1BestAttempt(Attempt user1BestAttempt) { this.user1BestAttempt = user1BestAttempt; }

    public Attempt getUser2BestAttempt() { return user2BestAttempt; }
    public void setUser2BestAttempt(Attempt user2BestAttempt) { this.user2BestAttempt = user2BestAttempt; }

    public String getComparisonResult() { return comparisonResult; }
    public void setComparisonResult(String comparisonResult) { this.comparisonResult = comparisonResult; }
}

