package br.ufal.ic.jackut.exceptions;

public class SenhaInvalidaException extends Exception {
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }

    public SenhaInvalidaException(String message) {
        super(message);
    }
}