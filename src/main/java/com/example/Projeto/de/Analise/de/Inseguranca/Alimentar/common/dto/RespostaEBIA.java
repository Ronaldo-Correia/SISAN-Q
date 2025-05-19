package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "DTO para armazenar as respostas da Escala Brasileira de Insegurança Alimentar (EBIA)")
public class RespostaEBIA {

    public enum RespostaEBIAEnum {
        SIM, NAO, NAO_SABE
    }

    @NotNull(message = "Respostas EBIA são obrigatórias")
    @Schema(description = "Mapa de perguntas e respostas da EBIA (1-8)",
            example = "{\"1\": \"SIM\", \"2\": \"NÃO\"}")
    private Map<Integer, RespostaEBIAEnum> respostas;
}