package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions.InvalidTokenException;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions.WeakPasswordException;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public String createPasswordResetTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24)); // 24 hours expiry
        userRepository.save(user);
        return token;
    }

    public void resetPassword(String token, String newPassword)
            throws InvalidTokenException, WeakPasswordException {

        // 1. Find user by token
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid token"));

        // 2. Check if token is expired
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token has expired");
        }

        // 3. Validate password strength
        if (newPassword == null || newPassword.length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters");
        }

        // 4. Update user
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null); // Clear the token
        user.setPasswordResetTokenExpiry(null); // Clear the expiry
        userRepository.save(user);
    }

    public void generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);  // Using correct setter
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    public void resetPasswordByEmail(String email, String novaSenha) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

        user.setPassword(passwordEncoder.encode(novaSenha));
        userRepository.save(user);
    }
}