package br.ufal.ic.jackut.exceptions;

public class UsuarioNaoCadastradoException extends Exception {
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }

    public UsuarioNaoCadastradoException(String message) {
        super(message);
    }
}