package br.ufal.ic.jackut.exceptions;

public class LoginOuSenhaInvalidoException extends Exception {
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inválidos.");
    }

    public LoginOuSenhaInvalidoException(String message) {
        super(message);
    }
}

