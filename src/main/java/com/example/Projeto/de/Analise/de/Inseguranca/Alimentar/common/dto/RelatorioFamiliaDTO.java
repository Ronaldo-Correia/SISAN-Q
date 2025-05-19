package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.ClassificacaoInseguranca;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Dependentes;
import lombok.Data;

import java.util.Map;

@Data
public class RelatorioFamiliaDTO {
    private Map<Dependentes, Long> distribuicaoDependentes;
    private Map<Dependentes, Map<ClassificacaoInseguranca, Long>> insegurancaPorDependentes;
    private Map<Dependentes, Map<String, Long>> consumoPorDependentes;
}