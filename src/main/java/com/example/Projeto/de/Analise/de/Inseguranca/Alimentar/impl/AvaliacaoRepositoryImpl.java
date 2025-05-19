package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.impl;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.enums.*;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.Avaliacao;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.QuestionarioSocioeconomico;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.repository.AvaliacaoRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class AvaliacaoRepositoryImpl implements AvaliacaoRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<Avaliacao> findByFiltros(Map<String, String> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Avaliacao> query = cb.createQuery(Avaliacao.class);
        Root<Avaliacao> root = query.from(Avaliacao.class);

        // Join com as entidades relacionadas
        Join<Avaliacao, QuestionarioSocioeconomico> socio = root.join("questionario", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        // Mapeamento de filtros para campos
        Map<String, Function<String, Predicate>> filterHandlers = new HashMap<>();

        filterHandlers.put("genero", value ->
                cb.equal(socio.get("genero"), Genero.valueOf(value.toUpperCase())));

        filterHandlers.put("racaCor", value ->
                cb.equal(socio.get("racaCor"), RacaCor.valueOf(value.toUpperCase())));

        filterHandlers.put("escolaridade", value ->
                cb.equal(socio.get("escolaridade"), Escolaridade.valueOf(value.toUpperCase())));

        filterHandlers.put("emprego", value ->
                cb.equal(socio.get("emprego"), Emprego.valueOf(value.toUpperCase())));

        filterHandlers.put("dependentes", value ->
                cb.equal(socio.get("dependentes"), Dependentes.valueOf(value.toUpperCase())));

        filterHandlers.put("idadeMin", value -> {
            try {
                return cb.ge(socio.get("idade"), Integer.parseInt(value));
            } catch (NumberFormatException e) {
                return cb.conjunction();
            }
        });

        filterHandlers.put("idadeMax", value -> {
            try {
                return cb.le(socio.get("idade"), Integer.parseInt(value));
            } catch (NumberFormatException e) {
                return cb.conjunction();
            }
        });

        // Aplicar filtros
        filters.forEach((key, value) -> {
            if (value != null && !value.trim().isEmpty()) {
                Function<String, Predicate> handler = filterHandlers.get(key);
                if (handler != null) {
                    predicates.add(handler.apply(value.trim()));
                }
            }
        });

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}