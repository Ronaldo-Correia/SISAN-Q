package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    public boolean isValidToken(String token) {
        return userRepository.findByPasswordResetToken(token)
                .map(user -> user.getPasswordResetTokenExpiry() != null &&
                        user.getPasswordResetTokenExpiry().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}