package br.ufal.ic.jackut.exceptions;

public class AutoPaqueraException extends RelacionamentoInvalidoException {
    public AutoPaqueraException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }

    public AutoPaqueraException(String message) {
        super(message);
    }
}