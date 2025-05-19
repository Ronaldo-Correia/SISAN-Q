package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "usuarios")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_admin")
    private boolean admin = false;

    // Regular getters and setters for the password reset fields
    // (these are NOT overrides)

    @Column(name = "reset_token")
    private String passwordResetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime passwordResetTokenExpiry;


    @OneToMany(mappedBy = "user")
    private List<Avaliacao> avaliacoes;

    // UserDetails interface methods (these ARE overrides)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(admin ? "ROLE_ADMIN" : "ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", admin=" + admin +
                ", resetToken='" + passwordResetTokenExpiry + '\'' +
                '}';
    }
}