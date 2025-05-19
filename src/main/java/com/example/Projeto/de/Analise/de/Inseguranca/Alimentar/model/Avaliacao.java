package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.ClassificacaoInseguranca;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    // Relacionamento com QuestionarioSocioeconomico (Cascade: PERSIST e MERGE)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "questionario_id", referencedColumnName = "id")
    private QuestionarioSocioeconomico questionario;

    // Relacionamento com MarcadoresConsumo (Cascade: ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consumo_id", referencedColumnName = "id")
    private MarcadoresConsumo marcadoresConsumo;

    // Relacionamento com EBIA (Cascade: ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ebia_id", referencedColumnName = "id")
    private EBIA ebia;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "concluida")
    private boolean concluida;

    @Enumerated(EnumType.STRING)
    private ClassificacaoInseguranca classificacao;
}