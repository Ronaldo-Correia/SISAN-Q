package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;

@Data
@Entity
@Table(name = "ebia_respostas")
public class EBIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "ebia_perguntas_respostas", joinColumns = @JoinColumn(name = "ebia_id"))
    @MapKeyColumn(name = "pergunta_numero")
    @Column(name = "resposta")
    @Enumerated(EnumType.STRING)
    private Map<Integer, Resposta> respostas;

    public enum Resposta {
        SIM, NAO, NAO_SABE
    }
}