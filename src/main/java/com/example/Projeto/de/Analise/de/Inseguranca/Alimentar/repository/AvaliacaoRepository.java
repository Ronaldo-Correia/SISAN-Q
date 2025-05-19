package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.*;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>,AvaliacaoRepositoryCustom {

    // Consultas customizadas para relatórios

    @Query("SELECT AVG(q.idade) FROM QuestionarioSocioeconomico q")
    Double calcularMediaIdade();

    @Query("SELECT COUNT(a) FROM Avaliacao a " +
            "WHERE a.marcadoresConsumo.consumoDiaAnterior.feijao = true")
    Long countByConsumoFeijao();

    @Query("SELECT NEW map(a.ebia.respostas as respostas, COUNT(a) as total) " +
            "FROM Avaliacao a GROUP BY a.ebia.respostas")
    List<Map<String, Object>> agregarRespostasEBIA();

    @Query("SELECT (COUNT(a) * 100.0 / (SELECT COUNT(a) FROM Avaliacao a)) " +
            "FROM Avaliacao a WHERE SIZE(a.ebia.respostas) > 2")
    Double calcularInsegurancaAlimentar();

    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.marcadoresConsumo.consumoDiaAnterior.feijao = true")
    Long countByConsumoFeijao(boolean consumiu);

    @Query("SELECT NEW map(q.genero as chave, COUNT(q) as valor) " +
            "FROM QuestionarioSocioeconomico q GROUP BY q.genero")
    Map<Genero, Long> contarPorGenero();

    @Query("SELECT CASE " +
            "WHEN SIZE(a.ebia.respostas) > 4 THEN 'INSEGURANÇA_GRAVE' " +
            "WHEN SIZE(a.ebia.respostas) > 2 THEN 'INSEGURANÇA_MODERADA' " +
            "ELSE 'SEGURANÇA_ALIMENTAR' END as classificacao, " +
            "COUNT(a) as total " +
            "FROM Avaliacao a " +
            "GROUP BY classificacao")
    List<Map<String, Long>> classificarInsegurancaAlimentar();

    List<Avaliacao> findByUser(User user);

    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.user = :user AND a.concluida = false")
    Long countByUserAndConcluidaFalse(@Param("user") User user);

    @Query("SELECT a FROM Avaliacao a WHERE a.user = :user ORDER BY a.dataCadastro DESC LIMIT 1")
    Optional<Avaliacao> findTopByUserOrderByDataCadastroDesc(@Param("user") User user);

    @Query("SELECT a FROM Avaliacao a JOIN FETCH a.questionario q WHERE q.dependentes IS NOT NULL AND a.classificacao IS NOT NULL")
    List<Avaliacao> findAvaliacoesValidas();

    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.questionario.dependentes = :dependentes")
    Long countByDependentes(@Param("dependentes") Dependentes dependentes);

    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.questionario.dependentes = :dependentes AND a.classificacao = :classificacao")
    Long countByDependentesAndClassificacao(
            @Param("dependentes") Dependentes dependentes,
            @Param("classificacao") ClassificacaoInseguranca classificacao);

    @Query("SELECT DISTINCT a FROM Avaliacao a " +
            "JOIN FETCH a.questionario q " +
            "JOIN FETCH a.marcadoresConsumo mc " +
            "WHERE q.dependentes IS NOT NULL " +
            "AND mc.consumoDiaAnterior.feijao IS NOT NULL " +
            "AND mc.consumoDiaAnterior.frutasFrescas IS NOT NULL")
    List<Avaliacao> findAvaliacoesComConsumoValido();

    @Query("SELECT a FROM Avaliacao a JOIN FETCH a.questionario q " +
            "WHERE q.dependentes IS NOT NULL AND a.classificacao IS NOT NULL")
    List<Avaliacao> findAvaliacoesValidasParaRelatorio();

    @Query("SELECT a FROM Avaliacao a JOIN FETCH a.questionario q WHERE q.dependentes IS NOT NULL")
    List<Avaliacao> findAllWithQuestionarioAndDependentes();


}