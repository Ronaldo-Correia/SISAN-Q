package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.UsoDispositivosRefeicao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "DTO para armazenar os marcadores de consumo alimentar")
public class RespostaConsumoDTO {

    @NotNull(message = "Informações sobre refeições são obrigatórias")
    @Schema(description = "Refeições realizadas diariamente",
            allowableValues = {"Café da manhã", "Lanche da manhã", "Almoço",
                    "Lanche da tarde", "Jantar", "Ceia/lanche da noite"})
    private Set<String> refeicoes;

    @Schema(description = "Consumo de alimentos no dia anterior")
    private ConsumoDiaAnterior consumoDiaAnterior;

    @Data
    public static class ConsumoDiaAnterior {

        @NotNull
        private UsoDispositivosRefeicao usoDispositivosRefeicao;

        @NotNull
        @Schema(description = "Consumiu feijão?")
        private Boolean feijao;

        @NotNull
        @Schema(description = "Consumiu frutas frescas?")
        private Boolean frutasFrescas;

        @NotNull
        @Schema(description = "Consumiu verduras/legumes?")
        private Boolean verdurasLegumes;

        @NotNull
        @Schema(description = "Consumiu hambúrguer/embutidos?")
        private Boolean hamburguerEmbutidos;

        @NotNull
        @Schema(description = "Consumiu bebidas adoçadas?")
        private Boolean bebidasAdocadas;

        @NotNull
        @Schema(description = "Consumiu alimentos industrializados salgados?")
        private Boolean industrializadosSalgados;

        @NotNull
        @Schema(description = "Consumiu doces/guloseimas?")
        private Boolean docesGuloseimas;

    }
}