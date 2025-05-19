package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.controller;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.payload.JwtResponse;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.AuthRequest;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service.AuthService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/auth")
@Service
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        authService.generateResetToken(email);
        return ResponseEntity.ok("Email de recuperação enviado");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }
}
