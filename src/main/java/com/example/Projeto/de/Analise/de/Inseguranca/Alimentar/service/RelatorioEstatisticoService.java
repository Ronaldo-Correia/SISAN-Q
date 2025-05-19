package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.DashboardDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.*;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioEstatisticoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public DashboardDTO gerarDadosDashboard() {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();

        DashboardDTO dto = new DashboardDTO();

        dto.setDistribuicaoGenero(contarEnum(avaliacoes, a -> a.getQuestionario().getGenero()));
        dto.setDistribuicaoRacaCor(contarEnum(avaliacoes, a -> a.getQuestionario().getRacaCor()));
        dto.setDistribuicaoEscolaridade(contarEnum(avaliacoes, a -> a.getQuestionario().getEscolaridade()));
        dto.setDistribuicaoEstadoCivil(contarEnum(avaliacoes, a -> a.getQuestionario().getEstadoCivil()));
        dto.setDistribuicaoEmprego(contarEnum(avaliacoes, a -> a.getQuestionario().getEmprego()));
        dto.setDistribuicaoReligiao(contarEnum(avaliacoes, a -> a.getQuestionario().getReligiao()));
        dto.setDistribuicaoDependentes(contarEnum(avaliacoes, a -> a.getQuestionario().getDependentes()));

        dto.setDistribuicaoUsoDispositivos(contarEnum(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getUsoDispositivosRefeicao()));

        // Consumo alimentar
        dto.setConsumoFeijao(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getFeijao()));
        dto.setConsumoFrutas(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getFrutasFrescas()));
        dto.setConsumoVerduras(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getVerdurasLegumes()));
        dto.setConsumoEmbutidos(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getHamburguerEmbutidos()));
        dto.setConsumoBebidas(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getBebidasAdocadas()));
        dto.setConsumoMacarrao(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getIndustrializadosSalgados()));
        dto.setConsumoGuloseimas(contarBooleano(avaliacoes, a -> a.getMarcadoresConsumo().getConsumoDiaAnterior().getDocesGuloseimas()));

        // EBIA
        dto.setEbiaPreocupacao(contarEBIA(avaliacoes, 1));
        dto.setEbiaAcabaram(contarEBIA(avaliacoes, 2));
        dto.setEbiaSemDinheiro(contarEBIA(avaliacoes, 3));
        dto.setEbiaPoucos(contarEBIA(avaliacoes, 4));
        dto.setEbiaSemRefeicao(contarEBIA(avaliacoes, 5));
        dto.setEbiaFome(contarEBIA(avaliacoes, 6));
        dto.setEbiaUmaRefeicao(contarEBIA(avaliacoes, 7));

        // Insegurança alimentar
        dto.setClassificacaoInseguranca(contarEnum(avaliacoes, Avaliacao::getClassificacao));

        return dto;
    }

    private <T extends Enum<T>> Map<String, Long> contarEnum(List<Avaliacao> lista, Function<Avaliacao, T> getter) {
        return lista.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Enum::name, Collectors.counting()));
    }

    private Map<String, Long> contarBooleano(List<Avaliacao> lista, Function<Avaliacao, Boolean> getter) {
        return lista.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(b -> b ? "Sim" : "Não", Collectors.counting()));
    }

    private Map<String, Long> contarEBIA(List<Avaliacao> lista, int pergunta) {
        return lista.stream()
                .map(a -> a.getEbia().getRespostas().get(pergunta))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Enum::name, Collectors.counting()));
    }
}
