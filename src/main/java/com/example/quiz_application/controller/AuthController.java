package com.example.quiz_application.controller;

import com.example.quiz_application.dto.AuthResponse;
import com.example.quiz_application.dto.RegistrationRequest;
import com.example.quiz_application.model.User;
import com.example.quiz_application.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register( @RequestBody RegistrationRequest request) {
        try {
            User user = authService.registerUser(request);
            AuthResponse response = new AuthResponse(
                    "User registered successfully",
                    user.getId(),
                    user.getUsername()
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(e.getMessage(), null, null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(e.getMessage(), null, null));
        }
    }
}