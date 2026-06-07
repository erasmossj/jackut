package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exceptions.*;
import br.ufal.ic.jackut.services.UsuarioService;
import br.ufal.ic.jackut.services.SessionService;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class Facade {
	private UsuarioService usuarioService;
	private SessionService sessionService;

	public Facade() {
		this.usuarioService = new UsuarioService(UsuarioRepository.load());
		this.sessionService = new SessionService();
	}

	public void zerarSistema() {
		UsuarioRepository.clear();
		usuarioService.clear();
		sessionService.limpar();
	}

	public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
		return this.usuarioService.getAtributoUsuario(login, atributo);
	}

	public void criarUsuario(String login, String senha, String nome) throws ContaNaExisteException, LoginInvalidoException, SenhaInvalidaException {
		this.usuarioService.criarUsuario(login, senha, nome);
	}

	public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
		return this.usuarioService.abrirSessao(login, senha, sessionService);
	}

	public void editarPerfil(String id, String atributo, String valor) throws SessaoInvalidaException {
		this.usuarioService.editarPerfil(id, atributo, valor, sessionService);
	}

	public void encerrarSistema() throws FalhaAoSalvarException {
		UsuarioRepository.save(usuarioService.getUsuariosList());
	}
}
