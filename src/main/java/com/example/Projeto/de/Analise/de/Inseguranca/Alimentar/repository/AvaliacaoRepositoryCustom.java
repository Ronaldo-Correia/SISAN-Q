package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository;


import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;

import java.util.List;
import java.util.Map;

public interface AvaliacaoRepositoryCustom {
    List<Avaliacao> findByFiltros(Map<String, String> filtros);
}

