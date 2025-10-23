package com.example.quiz_application.controller;

import com.example.quiz_application.model.AnswerOption;
import com.example.quiz_application.model.Question;
import com.example.quiz_application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/answer-options")
@CrossOrigin(origins = "*")
public class AnswerOptionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerOption>> getAnswerOptionsByQuestion(@PathVariable Long questionId) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get().getAnswerOptions());
        }
        return ResponseEntity.notFound().build();
    }
}