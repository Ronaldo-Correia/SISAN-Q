package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Busca usuário por email (usado na autenticação)
    Optional<User> findByEmail(String email);

    // Verifica se o email já está cadastrado (para evitar duplicatas)
    boolean existsByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);
}