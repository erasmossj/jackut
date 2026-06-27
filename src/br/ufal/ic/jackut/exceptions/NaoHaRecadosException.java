package br.ufal.ic.jackut.exceptions;

public class NaoHaRecadosException extends Exception {
    public NaoHaRecadosException() {
        super("Não há recados.");
    }

    public NaoHaRecadosException(String message) {
        super(message);
    }
}