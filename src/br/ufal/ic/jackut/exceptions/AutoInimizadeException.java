package br.ufal.ic.jackut.exceptions;

public class AutoInimizadeException extends RelacionamentoInvalidoException {
    public AutoInimizadeException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }

    public AutoInimizadeException(String message) {
        super(message);
    }
}