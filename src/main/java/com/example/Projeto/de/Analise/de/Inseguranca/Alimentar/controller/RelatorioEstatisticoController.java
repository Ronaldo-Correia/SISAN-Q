package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.controller;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.DashboardDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service.RelatorioEstatisticoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioEstatisticoController {
    private final RelatorioEstatisticoService relatorioService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDadosDashboard() {
        return ResponseEntity.ok(relatorioService.gerarDadosDashboard());
    }


}