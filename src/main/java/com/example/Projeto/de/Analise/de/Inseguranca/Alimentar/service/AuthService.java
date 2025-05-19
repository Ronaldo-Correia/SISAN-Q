package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.UserRepository;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.jwt.JwtService;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.payload.JwtResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Você pode adicionar outras validações para o campo senha aqui.
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("A senha deve ter pelo menos 6 caracteres");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setAdmin(user.isAdmin());
        newUser.setNome(user.getNome());  // Caso você adicione esse campo ao modelo User
        return userRepository.save(newUser);
    }

    public JwtResponse login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Conta desativada");
        }

        var jwtToken = jwtService.generateToken(user);

        return new JwtResponse(jwtToken, user.getId(), user.getEmail(), user.isAdmin() ? "ADMIN" : "USER");
    }

    public void generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);  // Changed from setResetToken to setPasswordResetToken
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        // Enviar email com o token
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null); // 👈 Não lance exceção aqui!
    }
}