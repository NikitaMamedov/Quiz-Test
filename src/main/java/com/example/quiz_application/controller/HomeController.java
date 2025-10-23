package com.example.quiz_application.controller;

import com.example.quiz_application.model.Quiz;
import com.example.quiz_application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public String home() {
        List<Quiz> quizzes = quizService.getActiveQuizzes();

        StringBuilder response = new StringBuilder();
        response.append("✅ <h1>Quiz Application успешно запущена!</h1><br><br>");
        response.append("<h2>Доступные тесты:</h2><br>");

        if (quizzes.isEmpty()) {
            response.append("❌ Нет доступных тестов<br>");
        } else {
            for (Quiz quiz : quizzes) {
                response.append("• <strong>").append(quiz.getTitle()).append("</strong>");
                response.append(" - ").append(quiz.getDescription());
                response.append(" (ID: ").append(quiz.getId()).append(")");
                response.append(" <a href='/api/quizzes/").append(quiz.getId()).append("'>Подробнее</a><br>");
            }
        }

        response.append("<br><h2>Доступные endpoints:</h2>");
        response.append("• <a href='/api/users'>/api/users</a> - Пользователи<br>");
        response.append("• <a href='/api/quizzes'>/api/quizzes</a> - Все тесты<br>");
        response.append("• <a href='/api/questions'>/api/questions</a> - Все вопросы<br>");
        response.append("• <a href='/api/attempts'>/api/attempts</a> - Попытки<br>");
        response.append("• <a href='/start-test'>/start-test</a> - Начать тест<br>");
        response.append("• <a href='/h2-console'>/h2-console</a> - База данных<br>");
        response.append("• <a href='/health'>/health</a> - Проверка работы<br>");

        return response.toString();
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Сервер работает! База данных создана. Все таблицы готовы.";
    }

    @GetMapping("/start-test")
    public String startTest() {
        return "<h2>🎯 Как начать тестирование:</h2>" +
                "<ol>" +
                "<li>Создайте пользователя: POST /api/users</li>" +
                "<li>Посмотрите доступные тесты: GET /api/quizzes</li>" +
                "<li>Начните попытку: POST /api/attempts/start</li>" +
                "<li>Завершите попытку: POST /api/attempts/{id}/complete</li>" +
                "</ol>" +
                "<br><strong>Пример начала теста:</strong><br>" +
                "POST /api/attempts/start<br>" +
                "Body: {\"userId\": 1, \"quizId\": 1}";
    }
}