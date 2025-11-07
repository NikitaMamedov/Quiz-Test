package com.example.quiz_application.service;

import com.example.quiz_application.dto.AttemptAnalytics;
import com.example.quiz_application.dto.QuestionAnalysis;
import com.example.quiz_application.dto.UserComparison;
import com.example.quiz_application.dto.UserProgress;
import com.example.quiz_application.model.*;
import com.example.quiz_application.repository.AttemptRepository;
import com.example.quiz_application.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizRepository quizRepository;

    public Attempt startAttempt(Long userId, Long quizId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Quiz quiz = quizService.getQuizById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        if (!quiz.isActive()) {
            throw new IllegalStateException("Quiz is not active");
        }

        Optional<Attempt> activeAttempt = attemptRepository
                .findByUserIdAndQuizIdAndStatus(userId, quizId, Attempt.AttemptStatus.IN_PROGRESS);

        if (activeAttempt.isPresent()) {
            return activeAttempt.get();
        }

        Attempt attempt = new Attempt(user, quiz);
        return attemptRepository.save(attempt);
    }

    public Attempt completeAttempt(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        attempt.complete();
        calculateScore(attempt);

        return attemptRepository.save(attempt);
    }

    private void calculateScore(Attempt attempt) {
        int score = 0;

        for (UserAnswer userAnswer : attempt.getUserAnswers()) {
            if (userAnswer.getSelectedAnswer().isCorrect()) {
                score += userAnswer.getQuestion().getPoints();
            }
        }

        attempt.setScore(score);
    }

    public List<Attempt> getUserAttempts(Long userId) {
        return attemptRepository.findByUserId(userId);
    }

    public Optional<Attempt> getAttemptById(Long id) {
        return attemptRepository.findById(id);
    }

    public List<Attempt> getAllAttempts() {
        return attemptRepository.findAll();
    }

    // НОВЫЙ МЕТОД ДЛЯ ОТПРАВКИ ОТВЕТОВ
    public Attempt submitAnswers(Long attemptId, Map<Long, Long> answers) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (attempt.getStatus() != Attempt.AttemptStatus.IN_PROGRESS) {
            throw new IllegalStateException("Attempt is already completed");
        }

        // Очищаем предыдущие ответы
        attempt.getUserAnswers().clear();

        // Добавляем новые ответы
        for (Map.Entry<Long, Long> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            Long answerOptionId = entry.getValue();

            Question question = questionService.getQuestionById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found: " + questionId));

            AnswerOption selectedAnswer = question.getAnswerOptions().stream()
                    .filter(option -> option.getId().equals(answerOptionId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Answer option not found: " + answerOptionId));

            UserAnswer userAnswer = new UserAnswer(attempt, question, selectedAnswer);
            attempt.getUserAnswers().add(userAnswer);
        }

        return attemptRepository.save(attempt);
    }

    // Бизнес-операция 3: Получить детальную аналитику попытки
    public AttemptAnalytics getAttemptAnalytics(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        int totalQuestions = attempt.getQuiz().getQuestions().size();
        int correctAnswers = (int) attempt.getUserAnswers().stream()
                .filter(ua -> ua.getSelectedAnswer().isCorrect())
                .count();
        double percentage = totalQuestions > 0 ? (correctAnswers * 100.0) / totalQuestions : 0;

        // Анализ по вопросам
        List<QuestionAnalysis> questionAnalysis = attempt.getUserAnswers().stream()
                .map(ua -> new QuestionAnalysis(
                        ua.getQuestion().getId(),
                        ua.getQuestion().getText(),
                        ua.getSelectedAnswer().isCorrect(),
                        ua.getQuestion().getPoints()
                ))
                .collect(Collectors.toList());

        return new AttemptAnalytics(
                attempt.getId(), totalQuestions, correctAnswers,
                percentage, attempt.getScore(), attempt.getMaxScore(), questionAnalysis
        );
    }

    // Бизнес-операция 4: Сравнить результаты двух пользователей
    public UserComparison compareUsers(Long userId1, Long userId2, Long quizId) {
        List<Attempt> user1Attempts = attemptRepository.findTopByUserIdAndQuizIdOrderByScoreDesc(userId1, quizId);
        List<Attempt> user2Attempts = attemptRepository.findTopByUserIdAndQuizIdOrderByScoreDesc(userId2, quizId);

        Attempt bestUser1 = user1Attempts.isEmpty() ? null : user1Attempts.get(0);
        Attempt bestUser2 = user2Attempts.isEmpty() ? null : user2Attempts.get(0);

        return new UserComparison(bestUser1, bestUser2);
    }

// Бизнес-операция 5: Получить прогресс пользователя
public UserProgress getUserProgress(Long userId) {
    List<Attempt> userAttempts = attemptRepository.findByUserId(userId);

    long totalAttempts = userAttempts.size();
    long completedAttempts = userAttempts.stream()
            .filter(a -> a.getStatus() == Attempt.AttemptStatus.COMPLETED)
            .count();
    double averageScore = userAttempts.stream()
            .filter(a -> a.getScore() != null)
            .mapToInt(Attempt::getScore)
            .average()
            .orElse(0.0);

    // Самый популярный тест
    Long favoriteQuizId = attemptRepository.findMostFrequentQuizByUserId(userId);
    Quiz favoriteQuiz = favoriteQuizId != null ?
            quizRepository.findById(favoriteQuizId).orElse(null) : null;

    return new UserProgress(userId, totalAttempts, completedAttempts, averageScore, favoriteQuiz);
}

}
