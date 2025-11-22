package com.example.quiz_application.service;

import com.example.quiz_application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class DataInitializerService implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PasswordEncoder passwordEncoder; // ДОБАВИТЬ ЭТУ СТРОКУ

    @Override
    public void run(String... args) throws Exception {
        initializeTestData();
    }

    public void initializeTestData() {
        // Создаем тестовых пользователей С ПАРОЛЯМИ
        User user1 = new User("frog", "frog@example.com", "Никита", "Мамедов",
                passwordEncoder.encode("SecurePass123!"));

        User user2 = new User("trump", "trump@example.com", "Трамп", "Байден",
                passwordEncoder.encode("StrongPass456!"));

        userService.createUser(user1);
        userService.createUser(user2);

        // Создаем тестовый квиз
        Quiz javaQuiz = new Quiz("History Test", "Тест по истории");
        javaQuiz.setTimeLimit(30);
        quizService.createQuiz(javaQuiz);

        // Создаем вопросы для квиза
        Question question1 = new Question("Когда началась 1 мировая война?", javaQuiz);
        question1.setPoints(2);
        questionService.createQuestion(question1);

        // Варианты ответов для первого вопроса
        AnswerOption option1_1 = new AnswerOption("1914", true, question1);
        AnswerOption option1_2 = new AnswerOption("1900", false, question1);
        AnswerOption option1_3 = new AnswerOption("1940", false, question1);
        AnswerOption option1_4 = new AnswerOption("1925", false, question1);

        question1.setAnswerOptions(Arrays.asList(option1_1, option1_2, option1_3, option1_4));

        Question question2 = new Question("Назовите президентов Российской Федерации?", javaQuiz);
        question2.setType(Question.QuestionType.MULTIPLE_CHOICE);
        question2.setPoints(3);
        questionService.createQuestion(question2);

        // Варианты ответов для второго вопроса
        AnswerOption option2_1 = new AnswerOption("Путин", true, question2);
        AnswerOption option2_2 = new AnswerOption("Медведев", true, question2);
        AnswerOption option2_3 = new AnswerOption("Петр 1", false, question2);
        AnswerOption option2_4 = new AnswerOption("Ельцин", true, question2);
        AnswerOption option2_5 = new AnswerOption("Сталин", false, question2);

        question2.setAnswerOptions(Arrays.asList(option2_1, option2_2, option2_3, option2_4, option2_5));

        Question question3 = new Question("В каком году появился первый Iphone", javaQuiz);
        question3.setPoints(2);
        questionService.createQuestion(question3);

        // Варианты ответов для третьего вопроса
        AnswerOption option3_1 = new AnswerOption("2007", false, question3);
        AnswerOption option3_2 = new AnswerOption("2005", true, question3);
        AnswerOption option3_3 = new AnswerOption("2000", false, question3);
        AnswerOption option3_4 = new AnswerOption("2010", false, question3);

        question3.setAnswerOptions(Arrays.asList(option3_1, option3_2, option3_3, option3_4));

        System.out.println("✅ Тестовые данные созданы!");
        System.out.println("👤 Пользователи: frog, trump");
        System.out.println("🔐 Пароли: SecurePass123!, StrongPass456!");
        System.out.println("📝 Тест: History test");
        System.out.println("❓ Вопросов: 3");
    }
}