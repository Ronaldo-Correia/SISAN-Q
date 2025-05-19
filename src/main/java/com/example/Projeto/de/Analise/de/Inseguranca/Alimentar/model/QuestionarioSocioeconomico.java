package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "questionarios_socioeconomicos")
public class QuestionarioSocioeconomico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Min(value = 0, message = "Idade não pode ser negativa")
    @Max(value = 120, message = "Idade máxima permitida é 120 anos")
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gênero é obrigatório")
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Raça/Cor é obrigatória")
    private RacaCor racaCor;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Escolaridade é obrigatória")
    private Escolaridade escolaridade;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Estado civil é obrigatório")
    private EstadoCivil estadoCivil;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Situação de emprego é obrigatória")
    private Emprego emprego;

    @ElementCollection
    @CollectionTable(name = "auxilios", joinColumns = @JoinColumn(name = "questionario_id"))
    @Enumerated(EnumType.STRING)
    private Set<Auxilio> auxilios;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Número de dependentes é obrigatório")
    private Dependentes dependentes;

    @Enumerated(EnumType.STRING)
    private Religiao religiao;

    @OneToOne(mappedBy = "questionario")
    private Avaliacao avaliacao;
}
