#  Online Quiz Application

Spring Boot REST API для системы онлайн-тестирования.

! О проекте

Система позволяет создавать тесты, вопросы, варианты ответов и отслеживать попытки пользователей.

! Архитектура

! Сущности:
- User - Пользователи системы
- Quiz - Тесты с вопросами
- Question - Вопросы теста
- AnswerOption - Варианты ответов
- Attempt - Попытки прохождения тестов
- UserAnswer - Ответы пользователей


! API Endpoints

! Пользователи
- GET /api/users - список пользователей
- POST /api/users - создать пользователя
- GET /api/users/{id} - получить пользователя
- PUT /api/users/{id} - обновить пользователя
- DELETE /api/users/{id} - удалить пользователя

! Тесты
- GET /api/quizzes - список тестов
- POST /api/quizzes - создать тест
- GET /api/quizzes/{id} - получить тест
- PUT /api/quizzes/{id} - обновить тест
- DELETE /api/quizzes/{id} - удалить тест

! Вопросы
- GET /api/questions - список вопросов
- POST /api/questions - создать вопрос
- GET /api/questions/{id} - получить вопрос
- PUT /api/questions/{id} - обновить вопрос
- DELETE /api/questions/{id} - удалить вопрос

! Попытки
- POST /api/attempts/start - начать попытку
- POST /api/attempts/{id}/submit - отправить ответы
- POST /api/attempts/{id}/complete - завершить попытку
- GET /api/attempts - все попытки
- GET /api/attempts/user/{userId} - попытки пользователя

