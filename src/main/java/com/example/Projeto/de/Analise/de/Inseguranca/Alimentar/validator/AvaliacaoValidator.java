package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.validator;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.AvaliacaoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.QuestionarioSocioeconomicoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.RespostaConsumoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.RespostaEBIA;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions.DadosInvalidosException;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class AvaliacaoValidator {

    public void validar(AvaliacaoDTO avaliacaoDTO) throws DadosInvalidosException {
        Set<String> erros = new HashSet<>();

        validarQuestionario(avaliacaoDTO.getQuestionario(), erros);
        validarConsumo(avaliacaoDTO.getConsumo(), erros);
        validarEBIA(avaliacaoDTO.getEbia(), erros);

        if (!erros.isEmpty()) {
            throw new DadosInvalidosException("Dados inválidos: " + String.join(", ", erros));
        }
    }

    public void validarCompleto(AvaliacaoDTO dto) throws DadosInvalidosException {
        Set<String> erros = new HashSet<>();

        // Validação básica de estrutura
        if(dto.getQuestionario() == null) erros.add("Questionário socioeconômico obrigatório");
        if(dto.getConsumo() == null) erros.add("Dados de consumo obrigatórios");
        if(dto.getEbia() == null) erros.add("Respostas EBIA obrigatórias");

        // Validações detalhadas
        validarQuestionario(dto.getQuestionario(), erros);
        validarConsumo(dto.getConsumo(), erros);
        validarEBIA(dto.getEbia(), erros);

        if(!erros.isEmpty()) {
            throw new DadosInvalidosException("Erros de validação: " + String.join(", ", erros));
        }
    }



    private void validarQuestionario(QuestionarioSocioeconomicoDTO questionario, Set<String> erros) {
        if (questionario == null) {
            erros.add("Questionário socioeconômico não fornecido");
            return;
        }

        if (questionario.getNome() == null || questionario.getNome().isBlank()) {
            erros.add("Nome é obrigatório");
        }

        if (questionario.getIdade() != null && (questionario.getIdade() < 0 || questionario.getIdade() > 120)) {
            erros.add("Idade deve estar entre 0 e 120 anos");
        }

        if (questionario.getGenero() == null) {
            erros.add("Gênero é obrigatório");
        }

        if (questionario.getEscolaridade() == null) {
            erros.add("Escolaridade é obrigatória");
        }
    }

    private void validarConsumo(RespostaConsumoDTO consumo, Set<String> erros) {
        if (consumo == null) {
            erros.add("Dados de consumo não fornecidos");
            return;
        }

        if (consumo.getRefeicoes() == null || consumo.getRefeicoes().isEmpty()) {
            erros.add("Pelo menos uma refeição diária deve ser informada");
        }

        if (consumo.getConsumoDiaAnterior() == null) {
            erros.add("Consumo do dia anterior não fornecido");
            return;
        }

        if (consumo.getConsumoDiaAnterior().getFeijao() == null) {
            erros.add("Informe se consumiu feijão no dia anterior");
        }
    }

    private void validarEBIA(RespostaEBIA ebia, Set<String> erros) {
        if (ebia == null || ebia.getRespostas() == null) {
            erros.add("Respostas EBIA não fornecidas");
            return;
        }

        if (ebia.getRespostas().size() != 8) {
            erros.add("Todas as 8 perguntas da EBIA devem ser respondidas");
        }

        ebia.getRespostas().forEach((pergunta, resposta) -> {
            if (resposta == null) {
                erros.add("Resposta não fornecida para pergunta EBIA " + pergunta);
            }
        });
    }


}