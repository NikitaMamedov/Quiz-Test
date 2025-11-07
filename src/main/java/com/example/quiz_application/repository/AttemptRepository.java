package com.example.quiz_application.repository;

import com.example.quiz_application.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUserId(Long userId);
    List<Attempt> findByQuizId(Long quizId);
    Optional<Attempt> findByUserIdAndQuizIdAndStatus(Long userId, Long quizId, Attempt.AttemptStatus status);

    @Query("SELECT a FROM Attempt a WHERE a.user.id = :userId AND a.quiz.id = :quizId ORDER BY a.startedAt DESC")
    List<Attempt> findUserAttemptsForQuiz(Long userId, Long quizId);

    Long countByQuizId(Long quizId);


    @Query("SELECT AVG(a.score) FROM Attempt a WHERE a.quiz.id = :quizId AND a.score IS NOT NULL")

    Double findAverageScoreByQuizId(Long quizId);

    @Query("SELECT a FROM Attempt a WHERE a.user.id = :userId AND a.quiz.id = :quizId ORDER BY a.score DESC")

    List<Attempt> findTopByUserIdAndQuizIdOrderByScoreDesc(Long userId, Long quizId);

    @Query("SELECT a.quiz.id FROM Attempt a WHERE a.user.id = :userId GROUP BY a.quiz.id ORDER BY COUNT(a) DESC LIMIT 1")

    Long findMostFrequentQuizByUserId(Long userId);

}
