package br.ufal.ic.jackut.exceptions;

public class AmizadePendenteException extends Exception {
    public AmizadePendenteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
