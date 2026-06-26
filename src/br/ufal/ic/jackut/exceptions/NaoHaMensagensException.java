package br.ufal.ic.jackut.exceptions;

public class NaoHaMensagensException extends Exception {
    public NaoHaMensagensException() {
        super("Não há mensagens.");
    }

    public NaoHaMensagensException(String message) {
        super(message);
    }
}