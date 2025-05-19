package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Dependentes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RelatorioConsumoDTO {
    private Map<Dependentes, Map<String, Long>> consumoPorDependente;
}