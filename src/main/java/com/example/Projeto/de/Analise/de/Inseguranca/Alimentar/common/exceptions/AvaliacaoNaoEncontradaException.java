package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.exceptions;

/**
 * Exceção lançada quando uma avaliação não é encontrada no sistema.
 */
public class AvaliacaoNaoEncontradaException extends RuntimeException {

    public AvaliacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public AvaliacaoNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}