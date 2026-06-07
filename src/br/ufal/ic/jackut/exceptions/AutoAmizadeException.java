package br.ufal.ic.jackut.exceptions;

public class AutoAmizadeException extends Exception {
    public AutoAmizadeException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
