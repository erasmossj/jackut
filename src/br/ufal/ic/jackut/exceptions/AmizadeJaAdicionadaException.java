package br.ufal.ic.jackut.exceptions;

public class AmizadeJaAdicionadaException extends Exception {
    public AmizadeJaAdicionadaException() {
        super("Usuário já está adicionado como amigo.");
    }
}
