package br.ufal.ic.jackut.exceptions;

public class RelacionamentoInvalidoException extends Exception {
    public RelacionamentoInvalidoException() {
        super("Relacionamento inválido.");
    }

    public RelacionamentoInvalidoException(String message) {
        super(message);
    }
}
