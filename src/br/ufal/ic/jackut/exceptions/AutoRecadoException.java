package br.ufal.ic.jackut.exceptions;

public class AutoRecadoException extends Exception {
    public AutoRecadoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
