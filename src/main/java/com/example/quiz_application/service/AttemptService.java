package com.example.quiz_application.service;

import com.example.quiz_application.model.*;
import com.example.quiz_application.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
