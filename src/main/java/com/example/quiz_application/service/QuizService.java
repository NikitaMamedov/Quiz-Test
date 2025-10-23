package com.example.quiz_application.service;

import com.example.quiz_application.model.Quiz;
import com.example.quiz_application.repository.QuizRepository;
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
}
