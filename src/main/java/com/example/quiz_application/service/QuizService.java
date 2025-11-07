package com.example.quiz_application.service;

import com.example.quiz_application.model.Quiz;
import com.example.quiz_application.model.Question;
import com.example.quiz_application.model.AnswerOption;
import com.example.quiz_application.repository.QuizRepository;
import com.example.quiz_application.repository.QuestionRepository;
import com.example.quiz_application.repository.AttemptRepository;
import com.example.quiz_application.dto.QuizStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Quiz> getActiveQuizzes() {
        return quizRepository.findByActiveTrue();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    public Quiz updateQuiz(Long id, Quiz quizDetails) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    // Проверяем, можно ли изменять тест
                    if (!quiz.isEditable()) {
                        throw new IllegalStateException("Cannot edit quiz with existing attempts");
                    }

                    quiz.setTitle(quizDetails.getTitle());
                    quiz.setDescription(quizDetails.getDescription());
                    quiz.setTimeLimit(quizDetails.getTimeLimit());
                    quiz.setActive(quizDetails.isActive());
                    return quizRepository.save(quiz);
                })
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        if (!quiz.isEditable()) {
            throw new IllegalStateException("Cannot delete quiz with existing attempts");
        }

        quizRepository.delete(quiz);
    }

    public boolean canEditQuiz(Long quizId) {
        return quizRepository.findById(quizId)
                .map(Quiz::isEditable)
                .orElse(false);
    }

    // Добавьте эти методы в существующий QuizService.java

    // Бизнес-операция 1: Получить статистику по тесту
    public QuizStatistics getQuizStatistics(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Long attemptCount = attemptRepository.countByQuizId(quizId);
        Double averageScore = attemptRepository.findAverageScoreByQuizId(quizId);
        Integer maxScore = calculateMaxScore(quiz);

        return new QuizStatistics(quiz, attemptCount, averageScore, maxScore);
    }

    // Бизнес-операция 2: Клонировать тест
    @Transactional
    public Quiz cloneQuiz(Long quizId, String newTitle) {
        Quiz originalQuiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Quiz clonedQuiz = new Quiz(newTitle, originalQuiz.getDescription());
        clonedQuiz.setTimeLimit(originalQuiz.getTimeLimit());
        quizRepository.save(clonedQuiz);

        // Клонируем вопросы и ответы в одной транзакции
        for (Question originalQuestion : originalQuiz.getQuestions()) {
            Question clonedQuestion = new Question(originalQuestion.getText(), clonedQuiz);
            clonedQuestion.setType(originalQuestion.getType());
            clonedQuestion.setPoints(originalQuestion.getPoints());
            questionRepository.save(clonedQuestion);

            for (AnswerOption originalOption : originalQuestion.getAnswerOptions()) {
                AnswerOption clonedOption = new AnswerOption(
                        originalOption.getText(),
                        originalOption.isCorrect(),
                        clonedQuestion
                );
                // Каскадно сохранится через Question
            }
        }

        return clonedQuiz;
    }

    private Integer calculateMaxScore(Quiz quiz) {
        return quiz.getQuestions().stream()
                .mapToInt(Question::getPoints)
                .sum();
    }
}



