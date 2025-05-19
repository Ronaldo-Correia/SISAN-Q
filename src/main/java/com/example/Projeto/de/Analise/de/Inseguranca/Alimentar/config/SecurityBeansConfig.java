package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.config;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.UserRepository;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.jwt.JwtAuthenticationFilter;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.jwt.JwtAuthenticationProvider;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.jwt.JwtService;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }


    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsServiceImpl userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        return new JwtAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsServiceImpl userDetailsService
    ) {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

}