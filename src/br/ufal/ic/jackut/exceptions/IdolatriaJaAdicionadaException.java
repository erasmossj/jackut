package br.ufal.ic.jackut.exceptions;

public class IdolatriaJaAdicionadaException extends RelacionamentoJaExisteException {
    public IdolatriaJaAdicionadaException() {
        super("Usuário já está adicionado como ídolo.");
    }
}