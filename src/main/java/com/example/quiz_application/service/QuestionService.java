package com.example.quiz_application.service;


import com.example.quiz_application.model.Question;
import com.example.quiz_application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizService quizService;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public Question createQuestion(Question question) {
        if (!question.getQuiz().isEditable()) {
            throw new IllegalStateException("Cannot add questions to quiz with existing attempts");
        }
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id)
                .map(question -> {
                    if (!question.getQuiz().isEditable()) {
                        throw new IllegalStateException("Cannot edit question in quiz with existing attempts");
                    }
                    question.setText(questionDetails.getText());
                    question.setType(questionDetails.getType());
                    question.setPoints(questionDetails.getPoints());
                    return questionRepository.save(question);
                })
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        if (!question.getQuiz().isEditable()) {
            throw new IllegalStateException("Cannot delete question from quiz with existing attempts");
        }
        questionRepository.delete(question);
    }
}
