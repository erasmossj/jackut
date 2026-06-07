package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exceptions.*;
import br.ufal.ic.jackut.services.UsuarioService;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class Facade {
    private UsuarioService usuarioService;

    public Facade() {
        this.usuarioService = new UsuarioService(UsuarioRepository.load());
    }

    public void zerarSistema() {
        UsuarioRepository.clear();
        usuarioService.clear();
    }

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        return this.usuarioService.getAtributoUsuario(login, atributo);
    }

    public void criarUsuario(String login, String senha, String nome) throws ContaNaExisteException, LoginInvalidoException, SenhaInvalidaException {
        this.usuarioService.criarUsuario(login, senha, nome);
    }

    public void abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        this.usuarioService.abrirSessao(login, senha);
    }

    public void encerrarSistema() throws FalhaAoSalvarException {
        UsuarioRepository.save(usuarioService.getUsuariosList());
    }
}
