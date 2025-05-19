package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Refeicao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.RespostaConsumoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.UsoDispositivosRefeicao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "marcadores_consumo")
public class MarcadoresConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(targetClass = Refeicao.class)
    @CollectionTable(name = "refeicoes_diarias", joinColumns = @JoinColumn(name = "consumo_id"))
    @Enumerated(EnumType.STRING)
    private Set<Refeicao> refeicoesDiarias;

    @Embedded
    private ConsumoDiaAnterior consumoDiaAnterior;

    // Classe embutida para consumo do dia anterior
    @Embeddable
    @Data
    public static class ConsumoDiaAnterior {
        private Boolean feijao;
        private Boolean frutasFrescas;
        private Boolean verdurasLegumes;
        private Boolean hamburguerEmbutidos;
        private Boolean bebidasAdocadas;
        private Boolean industrializadosSalgados;
        private Boolean docesGuloseimas;
        @Enumerated(EnumType.STRING)
        private UsoDispositivosRefeicao usoDispositivosRefeicao;

        // Método para conversão do DTO para a entidade
        public static ConsumoDiaAnterior fromDTO(RespostaConsumoDTO.ConsumoDiaAnterior dto) {
            ConsumoDiaAnterior entity = new ConsumoDiaAnterior();
            entity.setFeijao(dto.getFeijao());
            entity.setFrutasFrescas(dto.getFrutasFrescas());
            entity.setVerdurasLegumes(dto.getVerdurasLegumes());
            entity.setHamburguerEmbutidos(dto.getHamburguerEmbutidos());
            entity.setBebidasAdocadas(dto.getBebidasAdocadas());
            entity.setIndustrializadosSalgados(dto.getIndustrializadosSalgados());
            entity.setDocesGuloseimas(dto.getDocesGuloseimas());
            entity.setUsoDispositivosRefeicao(dto.getUsoDispositivosRefeicao());
            return entity;
        }

    }
}
