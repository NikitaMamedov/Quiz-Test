package com.example.quiz_application.controller;


import com.example.quiz_application.model.Question;
import com.example.quiz_application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsByQuizId(@PathVariable Long quizId) {
        return questionService.getQuestionsByQuizId(quizId);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        try {
            Question createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.ok(createdQuestion);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question questionDetails) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id, questionDetails);
            return ResponseEntity.ok(updatedQuestion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
