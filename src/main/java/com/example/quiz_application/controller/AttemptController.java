package com.example.quiz_application.controller;

import com.example.quiz_application.model.Attempt;
import com.example.quiz_application.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class  AttemptController {

    @Autowired
    private AttemptService attemptService;

    @PostMapping("/start")
    public ResponseEntity<Attempt> startAttempt(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long quizId = request.get("quizId");

        try {
            Attempt attempt = attemptService.startAttempt(userId, quizId);
            return ResponseEntity.ok(attempt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{attemptId}/complete")
    public ResponseEntity<Attempt> completeAttempt(@PathVariable Long attemptId) {
        try {
            Attempt attempt = attemptService.completeAttempt(attemptId);
            return ResponseEntity.ok(attempt);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public List<Attempt> getUserAttempts(@PathVariable Long userId) {
        return attemptService.getUserAttempts(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attempt> getAttemptById(@PathVariable Long id) {
        return attemptService.getAttemptById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Attempt> getAllAttempts() {
        return attemptService.getAllAttempts();
    }

    // В существующий AttemptController.java добавить:

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<Attempt> submitAnswers(@PathVariable Long attemptId,
                                                 @RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            Map<Long, Long> answers = (Map<Long, Long>) request.get("answers");

            Attempt attempt = attemptService.submitAnswers(attemptId, answers);
            return ResponseEntity.ok(attempt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
