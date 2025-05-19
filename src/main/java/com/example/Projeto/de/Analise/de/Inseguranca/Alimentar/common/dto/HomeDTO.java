package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HomeDTO {
    private String saudacao;
    private LocalDateTime ultimaAvaliacao;
    private String classificacaoInseguranca; // Alterado para String para melhor manipulação
    private Long avaliacoesPendentes;
}