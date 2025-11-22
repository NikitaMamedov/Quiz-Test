package com.example.quiz_application.config;

import com.example.quiz_application.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для API
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless сессии
                )
                .authorizeHttpRequests(authz -> authz
                        // Публичные endpoints
                        .requestMatchers("/", "/health", "/api/auth/**", "/h2-console/**").permitAll()

                        // Users endpoints - доступны аутентифицированным пользователям
                        .requestMatchers("/api/users/**").authenticated()

                        // Quizzes endpoints - чтение доступно всем, создание/изменение - аутентифицированным
                        .requestMatchers("/api/quizzes", "/api/quizzes/active", "/api/quizzes/{id}").permitAll()
                        .requestMatchers("/api/quizzes/**").authenticated()

                        // Questions endpoints - чтение доступно всем, создание/изменение - аутентифицированным
                        .requestMatchers("/api/questions", "/api/questions/{id}", "/api/questions/quiz/**").permitAll()
                        .requestMatchers("/api/questions/**").authenticated()

                        // Attempts endpoints - требуют аутентификации
                        .requestMatchers("/api/attempts/**").authenticated()

                        // Analytics endpoints - требуют аутентификации
                        .requestMatchers("/api/analytics/**").authenticated()

                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}) // Включаем Basic Auth
                .headers(headers -> headers.frameOptions().disable()) // Для H2 console
                .userDetailsService(userDetailsService);

        return http.build();
    }
}