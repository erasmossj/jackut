package br.ufal.ic.jackut.exceptions;

public class InimizadeJaAdicionadaException extends RelacionamentoJaExisteException {
    public InimizadeJaAdicionadaException() {
        super("Usuário já está adicionado como inimigo.");
    }
}