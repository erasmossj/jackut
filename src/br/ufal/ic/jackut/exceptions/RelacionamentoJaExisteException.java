package br.ufal.ic.jackut.exceptions;

public class RelacionamentoJaExisteException extends Exception {
    public RelacionamentoJaExisteException() {
        super("Relacionamento já existe.");
    }

    public RelacionamentoJaExisteException(String msg) {
        super(msg);
    }
}