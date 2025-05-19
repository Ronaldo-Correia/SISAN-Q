package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.AvaliacaoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.QuestionarioSocioeconomicoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.RespostaConsumoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.RespostaEBIA;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Auxilio;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Refeicao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions.AvaliacaoNaoEncontradaException;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.*;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.AvaliacaoRepository;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.validator.AvaliacaoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoValidator avaliacaoValidator;

    @Transactional
    public AvaliacaoDTO criarAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        avaliacaoValidator.validar(avaliacaoDTO);
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setQuestionario(mapearQuestionario(avaliacaoDTO.getQuestionario()));
        avaliacao.setMarcadoresConsumo(mapearConsumo(avaliacaoDTO.getConsumo()));
        avaliacao.setEbia(mapearEBIA(avaliacaoDTO.getEbia()));
        return toDTO(avaliacaoRepository.save(avaliacao));
    }

    public AvaliacaoDTO criarAvaliacaoComUsuario(AvaliacaoDTO dto, User user) {
        avaliacaoValidator.validar(dto);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUser(user);
        avaliacao.setQuestionario(mapearQuestionario(dto.getQuestionario()));
        avaliacao.setMarcadoresConsumo(mapearConsumo(dto.getConsumo()));
        avaliacao.setEbia(mapearEBIA(dto.getEbia()));

        return toDTO(avaliacaoRepository.save(avaliacao));
    }

    @Transactional
    public List<AvaliacaoDTO> listarTodas() {
        return avaliacaoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public Optional<AvaliacaoDTO> buscarPorId(Long id) {
        return avaliacaoRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public AvaliacaoDTO atualizarAvaliacao(Long id, AvaliacaoDTO dto) {
        avaliacaoValidator.validar(dto);
        Avaliacao existente = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new AvaliacaoNaoEncontradaException("Avaliação não encontrada"));
        atualizarQuestionario(existente.getQuestionario(), dto.getQuestionario());
        atualizarConsumo(existente.getMarcadoresConsumo(), dto.getConsumo());
        atualizarEBIA(existente.getEbia(), dto.getEbia());
        return toDTO(avaliacaoRepository.save(existente));
    }

    @Transactional
    public void excluirAvaliacao(Long id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new AvaliacaoNaoEncontradaException("Avaliação não encontrada");
        }
        avaliacaoRepository.deleteById(id);
    }

    private QuestionarioSocioeconomico mapearQuestionario(QuestionarioSocioeconomicoDTO dto) {
        QuestionarioSocioeconomico q = new QuestionarioSocioeconomico();
        q.setNome(dto.getNome());
        q.setIdade(dto.getIdade());
        q.setGenero(dto.getGenero());
        q.setRacaCor(dto.getRacaCor());
        q.setEscolaridade(dto.getEscolaridade());
        q.setEstadoCivil(dto.getEstadoCivil());
        q.setEmprego(dto.getEmprego());

        // Correção: Converter String para Set<Auxilio>
        if (dto.getAuxilios() != null && !dto.getAuxilios().isEmpty()) {
            Set<Auxilio> auxilios = Arrays.stream(dto.getAuxilios().split(","))
                    .map(String::trim)
                    .map(Auxilio::valueOf)
                    .collect(Collectors.toSet());
            q.setAuxilios(auxilios);
        } else {
            q.setAuxilios(new HashSet<>());
        }

        q.setDependentes(dto.getDependentes());
        q.setReligiao(dto.getReligiao());
        return q;
    }

    private void atualizarQuestionario(QuestionarioSocioeconomico entity, QuestionarioSocioeconomicoDTO dto) {
        Optional.ofNullable(dto.getNome()).ifPresent(entity::setNome);
        Optional.ofNullable(dto.getIdade()).ifPresent(entity::setIdade);
        Optional.ofNullable(dto.getGenero()).ifPresent(entity::setGenero);
        Optional.ofNullable(dto.getRacaCor()).ifPresent(entity::setRacaCor);
        Optional.ofNullable(dto.getEscolaridade()).ifPresent(entity::setEscolaridade);
        Optional.ofNullable(dto.getEstadoCivil()).ifPresent(entity::setEstadoCivil);
        Optional.ofNullable(dto.getEmprego()).ifPresent(entity::setEmprego);

        // Correção: Atualizar auxílios
        if (dto.getAuxilios() != null) {
            Set<Auxilio> auxilios = Arrays.stream(dto.getAuxilios().split(","))
                    .map(String::trim)
                    .map(Auxilio::valueOf)
                    .collect(Collectors.toSet());
            entity.setAuxilios(auxilios);
        }

        Optional.ofNullable(dto.getDependentes()).ifPresent(entity::setDependentes);
        Optional.ofNullable(dto.getReligiao()).ifPresent(entity::setReligiao);
    }

    private MarcadoresConsumo mapearConsumo(RespostaConsumoDTO dto) {
        MarcadoresConsumo consumo = new MarcadoresConsumo();

        // Converte refeições (String para Enum Refeicao)
        if (dto.getRefeicoes() != null) {
            Set<Refeicao> refeicoes = dto.getRefeicoes().stream()
                    .map(Refeicao::valueOf)
                    .collect(Collectors.toSet());
            consumo.setRefeicoesDiarias(refeicoes);
        }

        // Mapeia consumo do dia anterior (campos individuais)
        if (dto.getConsumoDiaAnterior() != null) {
            MarcadoresConsumo.ConsumoDiaAnterior consumoEntity = new MarcadoresConsumo.ConsumoDiaAnterior();
            RespostaConsumoDTO.ConsumoDiaAnterior consumoDTO = dto.getConsumoDiaAnterior();

            consumoEntity.setFeijao(consumoDTO.getFeijao());
            consumoEntity.setFrutasFrescas(consumoDTO.getFrutasFrescas());
            consumoEntity.setVerdurasLegumes(consumoDTO.getVerdurasLegumes());
            consumoEntity.setHamburguerEmbutidos(consumoDTO.getHamburguerEmbutidos());
            consumoEntity.setBebidasAdocadas(consumoDTO.getBebidasAdocadas());
            consumoEntity.setIndustrializadosSalgados(consumoDTO.getIndustrializadosSalgados());
            consumoEntity.setDocesGuloseimas(consumoDTO.getDocesGuloseimas());

            consumo.setConsumoDiaAnterior(consumoEntity);
        }

        return consumo;
    }

    private void atualizarConsumo(MarcadoresConsumo entity, RespostaConsumoDTO dto) {
        // Atualiza refeições diárias (String → Refeicao)
        if (dto.getRefeicoes() != null) {
            Set<Refeicao> refeicoes = dto.getRefeicoes().stream()
                    .map(Refeicao::valueOf)
                    .collect(Collectors.toSet());
            entity.setRefeicoesDiarias(refeicoes);
        }

        // Atualiza consumo do dia anterior (apenas campos não nulos)
        if (dto.getConsumoDiaAnterior() != null) {
            RespostaConsumoDTO.ConsumoDiaAnterior consumoDTO = dto.getConsumoDiaAnterior();
            MarcadoresConsumo.ConsumoDiaAnterior consumoEntity = entity.getConsumoDiaAnterior();

            Optional.ofNullable(consumoDTO.getFeijao()).ifPresent(consumoEntity::setFeijao);
            Optional.ofNullable(consumoDTO.getFrutasFrescas()).ifPresent(consumoEntity::setFrutasFrescas);
            Optional.ofNullable(consumoDTO.getVerdurasLegumes()).ifPresent(consumoEntity::setVerdurasLegumes);
            Optional.ofNullable(consumoDTO.getHamburguerEmbutidos()).ifPresent(consumoEntity::setHamburguerEmbutidos);
            Optional.ofNullable(consumoDTO.getBebidasAdocadas()).ifPresent(consumoEntity::setBebidasAdocadas);
            Optional.ofNullable(consumoDTO.getIndustrializadosSalgados()).ifPresent(consumoEntity::setIndustrializadosSalgados);
            Optional.ofNullable(consumoDTO.getDocesGuloseimas()).ifPresent(consumoEntity::setDocesGuloseimas);
        }
    }

    private EBIA mapearEBIA(RespostaEBIA dto) {
        EBIA ebia = new EBIA();
        ebia.setRespostas(dto.getRespostas().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> EBIA.Resposta.valueOf(e.getValue().name()))));
        return ebia;
    }

    private void atualizarEBIA(EBIA entity, RespostaEBIA dto) {
        Optional.ofNullable(dto.getRespostas()).ifPresent(respostas ->
                entity.setRespostas(respostas.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> EBIA.Resposta.valueOf(e.getValue().name())))));
    }

    private AvaliacaoDTO toDTO(Avaliacao avaliacao) {
        return new AvaliacaoDTO(
        );
    }

    public List<AvaliacaoDTO> listarPorUsuario(User user) {
        return avaliacaoRepository.findByUser(user).stream()
                .map(this::toDTO)
                .toList();
    }

}
