package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "DTO principal contendo todos os dados da avaliação de insegurança alimentar")
public class AvaliacaoDTO {

    @Schema(description = "ID único da avaliação", example = "1")
    private Long id;

    @Schema(description = "Data de cadastro da avaliação", example = "2024-01-01T10:00:00")
    private LocalDateTime dataCadastro;

    @Schema(description = "ID do usuário que realizou a avaliação")
    private Long userId;  // Adicione este campo

    @NotNull(message = "Dados socioeconômicos são obrigatórios")
    @Valid
    @Schema(description = "Seção do questionário socioeconômico")
    private QuestionarioSocioeconomicoDTO questionario;

    @NotNull(message = "Dados de consumo alimentar são obrigatórios")
    @Valid
    @Schema(description = "Seção de marcadores de consumo alimentar")
    private RespostaConsumoDTO consumo;

    @NotNull(message = "Respostas EBIA são obrigatórias")
    @Valid
    @Schema(description = "Seção da Escala Brasileira de Insegurança Alimentar")
    private RespostaEBIA ebia;
}