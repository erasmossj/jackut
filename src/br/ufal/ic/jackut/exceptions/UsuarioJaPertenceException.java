package br.ufal.ic.jackut.exceptions;

public class UsuarioJaPertenceException extends Exception {
    public UsuarioJaPertenceException() {
        super("Usuario já faz parte dessa comunidade.");
    }

    public UsuarioJaPertenceException(String message) {
        super(message);
    }
}