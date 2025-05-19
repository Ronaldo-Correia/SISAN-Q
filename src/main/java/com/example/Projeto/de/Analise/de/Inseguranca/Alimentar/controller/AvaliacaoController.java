package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.controller;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.AvaliacaoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions.AvaliacaoNaoEncontradaException;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions.DadosInvalidosException;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/avaliacoes")
@Tag(name = "Avaliação de Insegurança Alimentar", description = "Gestão de formulários de avaliação nutricional")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    @Operation(summary = "Criar nova avaliação", description = "Registra um novo formulário completo de avaliação")
    @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso")
    public ResponseEntity<AvaliacaoDTO> criarAvaliacao(
            @Valid @RequestBody AvaliacaoDTO avaliacaoDTO // Adicionado @Valid
    ) throws DadosInvalidosException {
        AvaliacaoDTO novaAvaliacao = avaliacaoService.criarAvaliacao(avaliacaoDTO);
        return new ResponseEntity<>(novaAvaliacao, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Recupera todos os dados de uma avaliação específica")
    @ApiResponse(responseCode = "200", description = "Avaliação encontrada")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<AvaliacaoDTO> buscarPorId(@PathVariable Long id) {
        AvaliacaoDTO avaliacao = avaliacaoService.buscarPorId(id).orElseThrow(() -> new AvaliacaoNaoEncontradaException("Avaliação não encontrada com ID: " + id));
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping
    @Operation(summary = "Listar todas as avaliações", description = "Retorna todas as avaliações registradas no sistema")
    public ResponseEntity<List<AvaliacaoDTO>> listarTodas() {
        return ResponseEntity.ok(avaliacaoService.listarTodas());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação existente")
    public ResponseEntity<AvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoDTO avaliacaoDTO // Adicionado @Valid
    ) throws DadosInvalidosException {
        return ResponseEntity.ok(avaliacaoService.atualizarAvaliacao(id, avaliacaoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir avaliação", description = "Remove permanentemente uma avaliação do sistema")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirAvaliacao(@PathVariable Long id) {
        avaliacaoService.excluirAvaliacao(id);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<AvaliacaoDTO>> listarMinhasAvaliacoes(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(avaliacaoService.listarPorUsuario(user));
    }

}