package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.QuestionarioSocioeconomico;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface QuestionarioRepository extends JpaRepository<QuestionarioSocioeconomico, Long> {

    @Query("SELECT new map(q.dependentes as dependentes, COUNT(q) as total, a.classificacao as classificacao) " +
            "FROM QuestionarioSocioeconomico q " +
            "JOIN q.avaliacao a " +
            "GROUP BY q.dependentes, a.classificacao")
    List<Map<String, Object>> findFamiliaInsegurancaData();

}
