package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "DTO para o questionário socioeconômico")
public class QuestionarioSocioeconomicoDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do respondente", example = "Maria da Silva")
    private String nome;

    @NotNull(message = "Idade é obrigatória")
    @Min(value = 0, message = "Idade não pode ser negativa")
    @Max(value = 120, message = "Idade máxima permitida é 120 anos")
    @Schema(description = "Idade do respondente", example = "35")
    private Integer idade;

    @NotNull(message = "Gênero é obrigatório")
    @Schema(description = "Gênero do respondente", example = "FEMININO")
    private Genero genero;

    @NotNull(message = "Raça/Cor é obrigatória")
    @Schema(description = "Raça/Cor do respondente", example = "PARDO")
    private RacaCor racaCor;

    @NotNull(message = "Escolaridade é obrigatória")
    @Schema(description = "Nível de escolaridade", example = "ENSINO_MEDIO_COMPLETO")
    private Escolaridade escolaridade;

    @NotNull(message = "Estado civil é obrigatório")
    @Schema(description = "Estado civil do respondente", example = "CASADO")
    private EstadoCivil estadoCivil;

    @NotNull(message = "Situação de emprego é obrigatória")
    @Schema(description = "Situação de emprego atual", example = "DESEMPREGADO")
    private Emprego emprego;

    @Schema(description = "Auxílios governamentais recebidos", example = "Bolsa Família, Auxílio Emergencial")
    private String auxilios;

    @NotNull(message = "Número de dependentes é obrigatório")
    @Schema(description = "Quantidade de dependentes familiares", example = "DOIS")
    private Dependentes dependentes;

    @Schema(description = "Religião do respondente", example = "CATOLICO")
    private Religiao religiao;

}
