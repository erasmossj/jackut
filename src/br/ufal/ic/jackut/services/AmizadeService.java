package br.ufal.ic.jackut.services;

import br.ufal.ic.jackut.models.Relacionamento;
import br.ufal.ic.jackut.models.RelacionamentoFactory;
import br.ufal.ic.jackut.models.Usuario;
import br.ufal.ic.jackut.repository.AmizadeRepository;
import br.ufal.ic.jackut.exceptions.SessaoInvalidaException;
import br.ufal.ic.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exceptions.AmizadeJaAdicionadaException;
import br.ufal.ic.jackut.exceptions.AmizadePendenteException;
import br.ufal.ic.jackut.exceptions.RelacionamentoInvalidoException;
import br.ufal.ic.jackut.exceptions.RelacionamentoJaExisteException;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;
import br.ufal.ic.jackut.exceptions.IdolatriaJaAdicionadaException;
import br.ufal.ic.jackut.exceptions.PaqueraJaAdicionadaException;
import br.ufal.ic.jackut.exceptions.InimizadeJaAdicionadaException;
import br.ufal.ic.jackut.exceptions.InimigoException;
import br.ufal.ic.jackut.utils.Validador;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.List;
import java.util.ArrayList;

public class AmizadeService {
    private List<Relacionamento> relacionamentos;

    public AmizadeService() {
        this.relacionamentos = AmizadeRepository.load();
    }

    public void verificarInimigo(String meuLogin, String alvoLogin) throws InimigoException {
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Inimizade")) {
                if ((rel.getSolicitante().equals(meuLogin) && rel.getSolicitado().equals(alvoLogin)) ||
                        (rel.getSolicitante().equals(alvoLogin) && rel.getSolicitado().equals(meuLogin))) {
                    String nome = "";
                    for (Usuario u : UsuarioRepository.load()) {
                        if (u.getLogin().equals(alvoLogin)) {
                            nome = u.getNome();
                        }
                    }
                    throw new InimigoException("Função inválida: " + nome + " é seu inimigo.");
                }
            }
        }
    }

    public void adicionarAmigo(String sessionId, String amigoLogin, SessionService sessionService)
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, RelacionamentoInvalidoException,
            AmizadeJaAdicionadaException, AmizadePendenteException,
            FalhaAoSalvarException, InimigoException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        String meuLogin = usuarioLogado.getLogin();
        Validador.validarUsuarioExistente(amigoLogin);

        verificarInimigo(meuLogin, amigoLogin);

        Relacionamento novoRelacionamento = RelacionamentoFactory.criarRelacionamento("amizade", meuLogin, amigoLogin);
        novoRelacionamento.validar();

        for (Relacionamento rel : relacionamentos) {
            if (rel.getClass().equals(novoRelacionamento.getClass())) {
                if ((rel.getSolicitante().equals(meuLogin) && rel.getSolicitado().equals(amigoLogin)) ||
                        (rel.getSolicitante().equals(amigoLogin) && rel.getSolicitado().equals(meuLogin))) {

                    if (rel.isAceito()) {
                        throw new AmizadeJaAdicionadaException();
                    }

                    if (rel.getSolicitante().equals(meuLogin)) {
                        throw new AmizadePendenteException();
                    } else {
                        rel.aplicar();
                        AmizadeRepository.save(relacionamentos);
                        return;
                    }
                }
            }
        }

        relacionamentos.add(novoRelacionamento);
        AmizadeRepository.save(relacionamentos);
    }

    public boolean ehAmigo(String login1, String login2) {
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Amizade")) {
                if (rel.isAceito() &&
                        ((rel.getSolicitante().equals(login1) && rel.getSolicitado().equals(login2)) ||
                                (rel.getSolicitante().equals(login2) && rel.getSolicitado().equals(login1)))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getAmigos(String login) {
        List<String> meusAmigos = new ArrayList<>();
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Amizade")) {
                if (rel.isAceito()) {
                    if (rel.getSolicitante().equals(login)) {
                        meusAmigos.add(rel.getSolicitado());
                    } else if (rel.getSolicitado().equals(login)) {
                        meusAmigos.add(rel.getSolicitante());
                    }
                }
            }
        }
        return "{" + String.join(",", meusAmigos) + "}";
    }

    public void clear() {
        this.relacionamentos = new ArrayList<>();
        AmizadeRepository.clear();
    }

    public void removerUsuario(String login) {
        relacionamentos.removeIf(rel -> rel.getSolicitante().equals(login) || rel.getSolicitado().equals(login));
        try {
            AmizadeRepository.save(relacionamentos);
        } catch (FalhaAoSalvarException e) {
        }
    }

    public void adicionarRelacionamento(String sessionId, String tipo, String alvo, SessionService sessionService)
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, RelacionamentoInvalidoException,
            RelacionamentoJaExisteException, FalhaAoSalvarException, InimigoException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        String meuLogin = usuarioLogado.getLogin();
        Validador.validarUsuarioExistente(alvo);

        if (!tipo.equalsIgnoreCase("inimizade")) {
            verificarInimigo(meuLogin, alvo);
        }

        Relacionamento novoRelacionamento = RelacionamentoFactory.criarRelacionamento(tipo, meuLogin, alvo);
        novoRelacionamento.validar();
        novoRelacionamento.aplicar();

        for (Relacionamento rel : relacionamentos) {
            if (rel.getClass().equals(novoRelacionamento.getClass())) {
                if ((rel.getSolicitante().equals(meuLogin) && rel.getSolicitado().equals(alvo))) {
                    if (tipo.equalsIgnoreCase("idolatria"))
                        throw new IdolatriaJaAdicionadaException();
                    if (tipo.equalsIgnoreCase("paquera"))
                        throw new PaqueraJaAdicionadaException();
                    if (tipo.equalsIgnoreCase("inimizade"))
                        throw new InimizadeJaAdicionadaException();
                    throw new RelacionamentoJaExisteException();
                }
            }
        }

        relacionamentos.add(novoRelacionamento);
        AmizadeRepository.save(relacionamentos);
    }

    public boolean ehFa(String login, String idolo) {
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Idolatria")) {
                if (rel.getSolicitante().equals(login) && rel.getSolicitado().equals(idolo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getFas(String login) {
        List<String> fas = new ArrayList<>();
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Idolatria")) {
                if (rel.getSolicitado().equals(login)) {
                    fas.add(rel.getSolicitante());
                }
            }
        }
        return "{" + String.join(",", fas) + "}";
    }

    public boolean ehPaquera(String id, String paquera, SessionService sessionService) throws Exception {
        Usuario u = sessionService.obterUsuarioDaSessao(id);
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Paquera")) {
                if (rel.getSolicitante().equals(u.getLogin()) && rel.getSolicitado().equals(paquera)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPaqueras(String id, SessionService sessionService) throws Exception {
        Usuario u = sessionService.obterUsuarioDaSessao(id);
        List<String> paqueras = new ArrayList<>();
        for (Relacionamento rel : relacionamentos) {
            if (rel.tipo().equals("Paquera")) {
                if (rel.getSolicitante().equals(u.getLogin())) {
                    paqueras.add(rel.getSolicitado());
                }
            }
        }
        return "{" + String.join(",", paqueras) + "}";
    }

    public List<Relacionamento> getAmizades() {
        return this.relacionamentos;
    }
}
