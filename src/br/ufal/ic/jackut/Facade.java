package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exceptions.*;
import br.ufal.ic.jackut.services.UsuarioService;
import br.ufal.ic.jackut.services.SessionService;
import br.ufal.ic.jackut.services.AmizadeService;
import br.ufal.ic.jackut.services.RecadoService;
import br.ufal.ic.jackut.repository.*;

public class Facade {
	private UsuarioService usuarioService;
	private SessionService sessionService;
	private AmizadeService amizadeService;
	private RecadoService recadoService;

	public Facade() {
		this.usuarioService = new UsuarioService(UsuarioRepository.load());
		this.sessionService = new SessionService();
		this.amizadeService = new AmizadeService();
		this.recadoService = new RecadoService();
	}

	public void zerarSistema() {
		UsuarioRepository.clear();
		usuarioService.clear();
		sessionService.limpar();
		amizadeService.clear();
		recadoService.clear();
	}

	public String getAtributoUsuario(String login, String atributo)
			throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
		return this.usuarioService.getAtributoUsuario(login, atributo);
	}

	public void criarUsuario(String login, String senha, String nome)
			throws ContaNaExisteException, LoginInvalidoException, SenhaInvalidaException {
		this.usuarioService.criarUsuario(login, senha, nome);
	}

	public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
		return this.usuarioService.abrirSessao(login, senha, sessionService);
	}

	public void editarPerfil(String id, String atributo, String valor) throws SessaoInvalidaException {
		this.usuarioService.editarPerfil(id, atributo, valor, sessionService);
	}

	public void adicionarAmigo(String id, String amigo) throws SessaoInvalidaException, UsuarioNaoCadastradoException, 
			RelacionamentoInvalidoException, AmizadeJaAdicionadaException, AmizadePendenteException, FalhaAoSalvarException {
		this.amizadeService.adicionarAmigo(id, amigo, sessionService);
	}

	public String getAmigos(String login) {
		return this.amizadeService.getAmigos(login);
	}

	public boolean ehAmigo(String login1, String login2) {
		return this.amizadeService.ehAmigo(login1, login2);
	}

	public void enviarRecado(String id, String destinatario, String recado) throws SessaoInvalidaException, UsuarioNaoCadastradoException, 
			AutoRecadoException, FalhaAoSalvarException {
		this.recadoService.enviarRecado(id, destinatario, recado, sessionService);
	}

	public String lerRecado(String id) throws SessaoInvalidaException, NaoHaRecadosException, FalhaAoSalvarException {
		return this.recadoService.lerRecado(id, sessionService);
	}

	public void encerrarSistema() throws FalhaAoSalvarException {
		UsuarioRepository.save(usuarioService.getUsuariosList());
		AmizadeRepository.save(amizadeService.getAmizades());
		RecadoRepository.save(recadoService.getRecados());
	}
}
