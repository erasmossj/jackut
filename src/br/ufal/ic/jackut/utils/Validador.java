package br.ufal.ic.jackut.utils;

import br.ufal.ic.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.jackut.exceptions.SenhaInvalidaException;

public class Validador {
    private Validador() {}

    public static boolean loginValido(final String login) {
        return login != null && !login.isEmpty();
    }

    public static boolean senhaValida(final String senha) {
        return senha != null && !senha.isEmpty();
    }

    public static void validarLogin(final String login) throws LoginInvalidoException {
        if (!loginValido(login)) throw new LoginInvalidoException();
    }

    public static void validarSenha(final String senha) throws SenhaInvalidaException {
        if (!senhaValida(senha)) throw new SenhaInvalidaException();
    }
}

