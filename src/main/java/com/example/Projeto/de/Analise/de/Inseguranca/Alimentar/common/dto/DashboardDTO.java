package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.ClassificacaoInseguranca;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Escolaridade;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Genero;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.RacaCor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Map;

@Data
public class DashboardDTO {
    private Map<String, Long> distribuicaoGenero;
    private Map<String, Long> distribuicaoRacaCor;
    private Map<String, Long> distribuicaoEscolaridade;
    private Map<String, Long> distribuicaoEstadoCivil;
    private Map<String, Long> distribuicaoEmprego;
    private Map<String, Long> distribuicaoReligiao;
    private Map<String, Long> distribuicaoDependentes;
    private Map<String, Long> distribuicaoUsoDispositivos;
    private Map<String, Long> distribuicaoFaixaEtaria;
    private Map<String, Long> distribuicaoRegiao;

    private Map<String, Long> consumoFeijao;
    private Map<String, Long> consumoFrutas;
    private Map<String, Long> consumoVerduras;
    private Map<String, Long> consumoEmbutidos;
    private Map<String, Long> consumoBebidas;
    private Map<String, Long> consumoMacarrao;
    private Map<String, Long> consumoGuloseimas;

    private Map<String, Long> ebiaPreocupacao;
    private Map<String, Long> ebiaAcabaram;
    private Map<String, Long> ebiaSemDinheiro;
    private Map<String, Long> ebiaPoucos;
    private Map<String, Long> ebiaSemRefeicao;
    private Map<String, Long> ebiaFome;
    private Map<String, Long> ebiaUmaRefeicao;

    private Map<String, Long> classificacaoInseguranca;
}
