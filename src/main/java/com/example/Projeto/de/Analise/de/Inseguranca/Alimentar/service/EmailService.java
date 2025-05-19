package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Redefinição de Senha");
        message.setText("Para redefinir sua senha, use o seguinte token: " + token +
                "\n\nAcesse: http://seusite.com/reset-password?token=" + token);
        mailSender.send(message);
    }
}