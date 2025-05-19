package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.AvaliacaoRepository;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.HomeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final AvaliacaoRepository avaliacaoRepository;

    public HomeDTO getHomeData(User user) {
        HomeDTO dto = new HomeDTO();
        dto.setSaudacao("Bem-vindo, " + user.getEmail());

        Optional<Avaliacao> ultimaAvaliacao = avaliacaoRepository.findTopByUserOrderByDataCadastroDesc(user);
        ultimaAvaliacao.ifPresent(av -> {
            dto.setUltimaAvaliacao(av.getDataCadastro());
            if (av.getClassificacao() != null) {
                dto.setClassificacaoInseguranca(av.getClassificacao().name());
            } else {
                dto.setClassificacaoInseguranca("N/A");
            }
        });

        Long pendentes = avaliacaoRepository.countByUserAndConcluidaFalse(user);
        dto.setAvaliacoesPendentes(pendentes != null ? pendentes : 0L);

        return dto;
    }
}