package br.ufal.ic.jackut.services;

import br.ufal.ic.jackut.models.Session;
import br.ufal.ic.jackut.models.Usuario;
import br.ufal.ic.jackut.repository.SessionRepository;
import java.util.List;
import java.util.UUID;

public class SessionService {
	private List<Session> sessoes;

	public SessionService() {
		this.sessoes = SessionRepository.load();
	}

	public String criarSessao(Usuario usuario) {
		String sessionId = UUID.randomUUID().toString();
		this.sessoes.add(new Session(sessionId, usuario));
		salvarSessoes();
		return sessionId;
	}

	public Usuario obterUsuarioDaSessao(String sessionId) {
		return this.sessoes.stream()
				.filter(s -> s.getId().equals(sessionId))
				.map(Session::getUsuario)
				.findFirst()
				.orElse(null);
	}

	public boolean sessaoValida(String sessionId) {
		return this.sessoes.stream().anyMatch(s -> s.getId().equals(sessionId));
	}

	public void fecharSessao(String sessionId) {
		this.sessoes.removeIf(s -> s.getId().equals(sessionId));
		salvarSessoes();
	}

	public void fecharSessoesDoUsuario(String login) {
		this.sessoes.removeIf(s -> s.getUsuario() != null && s.getUsuario().getLogin().equals(login));
		salvarSessoes();
	}

	public void limpar() {
		this.sessoes.clear();
		SessionRepository.clear();
	}

	private void salvarSessoes() {
		SessionRepository.save(this.sessoes);
	}
}