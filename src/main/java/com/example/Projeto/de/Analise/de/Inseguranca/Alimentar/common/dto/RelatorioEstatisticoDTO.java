package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.ClassificacaoInseguranca;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Escolaridade;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Genero;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.RacaCor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Map;

@Data
@Schema(description = "DTO para relatórios estatísticos de segurança alimentar")
public class RelatorioEstatisticoDTO {

    @Schema(description = "Média de idade dos respondentes", example = "34.5")
    private Double mediaIdade;

    @Schema(description = "Distribuição por gênero")
    private Map<Genero, Long> distribuicaoGenero;

    @Schema(description = "Distribuição por escolaridade")
    private Map<Escolaridade, Long> distribuicaoEscolaridade;

    @Schema(description = "Distribuição por raça/cor")
    private Map<RacaCor, Long> distribuicaoRacaCor;

    @Schema(description = "Percentual de insegurança alimentar", example = "65.2")
    private Double porcentagemInsegurancaAlimentar;

    @Schema(description = "Correlação entre consumo de alimentos básicos")
    private Map<String, Object> correlacaoConsumoAlimentar;

    @Schema(description = "Classificação da insegurança alimentar")
    private Map<ClassificacaoInseguranca, Long> classificacaoInseguranca;

}