package br.ufal.ic.jackut.exceptions;

public class ContaNaExisteException extends Exception {
    public ContaNaExisteException() {
        super("Conta com esse nome já existe.");
    }

    public ContaNaExisteException(String message) {
        super(message);
    }
}


