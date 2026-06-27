package br.ufal.ic.jackut.services;

import br.ufal.ic.jackut.exceptions.*;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.models.Usuario;
import br.ufal.ic.jackut.utils.Validador;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
	private List<Usuario> usuariosList;

	public UsuarioService(List<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}

	public List<Usuario> getUsuariosList() {
		return usuariosList;
	}

	public void addUsuario(Usuario usuario) {
		this.usuariosList.add(usuario);
		UsuarioRepository.save(usuariosList);
	}

	public String getAtributoUsuario(String login, String atributo)
			throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
		Usuario usuario = usuariosList.stream()
				.filter(u -> u.getLogin().equals(login))
				.findFirst()
				.orElseThrow(UsuarioNaoCadastradoException::new);

		switch (atributo) {
			case "nome":
				return usuario.getNome();
			case "login":
				return usuario.getLogin();
			case "senha":
				return usuario.getSenha();
			default:
				String valor = usuario.getAtributo(atributo);
				if (valor == null) {
					throw new AtributoNaoPreenchidoException();
				}
				return valor;
		}
	}

	public void criarUsuario(String login, String senha, String nome)
			throws ContaNaExisteException, LoginInvalidoException, SenhaInvalidaException {
		Validador.validarLogin(login);
		Validador.validarSenha(senha);

		if (usuariosList.stream().anyMatch(u -> u.getLogin().equals(login))) {
			throw new ContaNaExisteException();
		}

		Usuario usuario = new Usuario(login, nome, senha);
		addUsuario(usuario);
	}

	public String abrirSessao(String login, String senha, SessionService sessionService)
			throws LoginOuSenhaInvalidoException {
		Usuario usuario = usuariosList.stream()
				.filter(u -> Validador.loginValido(login) && u.getLogin().equals(login))
				.findFirst()
				.orElse(null);

		Validador.validarCredenciais(senha, usuario);

		return sessionService.criarSessao(usuario);
	}

	public void editarPerfil(String id, String atributo, String valor, SessionService sessionService)
			throws SessaoInvalidaException {
		Usuario usuario = sessionService.obterUsuarioDaSessao(id);
		Validador.validarSessao(usuario);

		usuario.setAtributo(atributo, valor);
		UsuarioRepository.save(usuariosList);
	}

	public void removerUsuario(String id, SessionService sessionService) throws SessaoInvalidaException {
		Usuario usuario = sessionService.obterUsuarioDaSessao(id);
		Validador.validarSessao(usuario);

		usuariosList.removeIf(u -> u.getLogin().equals(usuario.getLogin()));
		UsuarioRepository.save(usuariosList);
		sessionService.fecharSessoesDoUsuario(usuario.getLogin());
	}

	public void clear() {
		this.usuariosList = new ArrayList<>();
	}
}