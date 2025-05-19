package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Escolaridade;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.RacaCor;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface RelatorioRepository extends JpaRepository<Avaliacao, Long> {

    // Consultas complexas para geração de relatórios

    @Query("SELECT NEW map(q.escolaridade as chave, COUNT(q) as valor) " +
            "FROM QuestionarioSocioeconomico q GROUP BY q.escolaridade")
    List<Map<Escolaridade, Long>> contarPorEscolaridade();

    @Query("SELECT NEW map(q.racaCor as chave, COUNT(q) as valor) " +
            "FROM QuestionarioSocioeconomico q GROUP BY q.racaCor")
    List<Map<RacaCor, Long>> contarPorRacaCor();

    @Query("SELECT NEW map(" +
            "a.marcadoresConsumo.consumoDiaAnterior.feijao as feijao, " +
            "a.marcadoresConsumo.consumoDiaAnterior.frutasFrescas as frutas, " +
            "COUNT(a) as total) " +
            "FROM Avaliacao a " +
            "GROUP BY feijao, frutas")
    List<Map<String, Object>> correlacionarConsumoAlimentar();

    @Query("SELECT CASE " +
            "WHEN SIZE(a.ebia.respostas) > 4 THEN 'INSEGURANÇA GRAVE' " +
            "WHEN SIZE(a.ebia.respostas) > 2 THEN 'INSEGURANÇA MODERADA' " +
            "ELSE 'SEGURANÇA ALIMENTAR' END as classificacao, " +
            "COUNT(a) as total " +
            "FROM Avaliacao a " +
            "GROUP BY classificacao")
    List<Map<String, Long>> classificarInsegurancaAlimentar();
}