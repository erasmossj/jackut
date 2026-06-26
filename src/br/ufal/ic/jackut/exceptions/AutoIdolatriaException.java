package br.ufal.ic.jackut.exceptions;

public class AutoIdolatriaException extends RelacionamentoInvalidoException {
    public AutoIdolatriaException() {
        super("Usuário não pode ser fã de si mesmo.");
    }

    public AutoIdolatriaException(String message) {
        super(message);
    }
}