package com.example.quiz_application.repository;


import com.example.quiz_application.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByActiveTrue();
    List<Quiz> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT COUNT(a) FROM Attempt a WHERE a.quiz.id = :quizId")
    Long countAttemptsByQuizId(Long quizId);
}
