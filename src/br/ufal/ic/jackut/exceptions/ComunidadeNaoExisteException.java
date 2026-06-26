package br.ufal.ic.jackut.exceptions;

public class ComunidadeNaoExisteException extends Exception {
    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }

    public ComunidadeNaoExisteException(String message) {
        super(message);
    }
}