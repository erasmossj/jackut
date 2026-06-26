package br.ufal.ic.jackut.exceptions;

public class ComunidadeJaExisteException extends Exception {
    public ComunidadeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }

    public ComunidadeJaExisteException(String message) {
        super(message);
    }
}