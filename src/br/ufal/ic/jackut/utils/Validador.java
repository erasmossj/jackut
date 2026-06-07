package br.ufal.ic.jackut.utils;

import br.ufal.ic.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.jackut.exceptions.SenhaInvalidaException;
import br.ufal.ic.jackut.exceptions.SessaoInvalidaException;
import br.ufal.ic.jackut.exceptions.LoginOuSenhaInvalidoException;
import br.ufal.ic.jackut.models.Usuario;

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

    public static void validarSessao(Usuario usuarioLogado) throws SessaoInvalidaException {
        if (usuarioLogado == null) {
            throw new SessaoInvalidaException();
        }
    }

    public static void validarCredenciais(String senha, Usuario usuario) throws LoginOuSenhaInvalidoException {
        if (usuario == null || !senhaValida(senha) || !senha.equals(usuario.getSenha())) {
            throw new LoginOuSenhaInvalidoException();
        }
    }
}

