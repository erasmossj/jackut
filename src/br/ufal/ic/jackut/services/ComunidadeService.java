package br.ufal.ic.jackut.services;

import br.ufal.ic.jackut.models.Comunidade;
import br.ufal.ic.jackut.models.MensagemComunidade;
import br.ufal.ic.jackut.models.Usuario;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.exceptions.ComunidadeJaExisteException;
import br.ufal.ic.jackut.exceptions.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.exceptions.UsuarioJaPertenceException;
import br.ufal.ic.jackut.exceptions.SessaoInvalidaException;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;
import br.ufal.ic.jackut.exceptions.ApenasMembrosException;
import br.ufal.ic.jackut.exceptions.MensagemSemConteudoException;
import br.ufal.ic.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exceptions.NaoHaMensagensException;
import br.ufal.ic.jackut.utils.Validador;

import java.util.List;

public class ComunidadeService {
    private List<Comunidade> comunidades;
    private int proximaOrdemEntrada;

    public ComunidadeService() {
        this.comunidades = ComunidadeRepository.load();
        int maiorOrdem = -1;
        for (Comunidade com : comunidades) {
            for (Integer ordem : com.getOrdemEntrada().values()) {
                if (ordem != null && ordem > maiorOrdem) {
                    maiorOrdem = ordem;
                }
            }
        }
        this.proximaOrdemEntrada = maiorOrdem + 1;
    }

    public void criarComunidade(String sessionId, String nome, String descricao, SessionService sessionService)
            throws SessaoInvalidaException, ComunidadeJaExisteException, FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        for (Comunidade com : comunidades) {
            if (com.getNome().equals(nome)) {
                throw new ComunidadeJaExisteException();
            }
        }

        Comunidade nova = new Comunidade(nome, descricao, usuarioLogado.getLogin());
        nova.registrarEntrada(usuarioLogado.getLogin(), proximaOrdemEntrada++);
        comunidades.add(nova);
        ComunidadeRepository.save(comunidades);
    }

    public Comunidade getComunidade(String nome) throws ComunidadeNaoExisteException {
        for (Comunidade com : comunidades) {
            if (com.getNome().equals(nome)) {
                return com;
            }
        }
        throw new ComunidadeNaoExisteException();
    }

    public String getComunidades(String login) throws UsuarioNaoCadastradoException {
        Validador.validarUsuarioExistente(login);
        List<Comunidade> doUsuario = new java.util.ArrayList<>();
        for (Comunidade com : comunidades) {
            if (com.ehMembro(login)) {
                doUsuario.add(com);
            }
        }
        doUsuario.sort((a, b) -> {
            Integer ordemA = a.getOrdemEntradaDe(login);
            Integer ordemB = b.getOrdemEntradaDe(login);
            int oa = (ordemA != null) ? ordemA : Integer.MAX_VALUE;
            int ob = (ordemB != null) ? ordemB : Integer.MAX_VALUE;
            return Integer.compare(oa, ob);
        });
        List<String> nomes = new java.util.ArrayList<>();
        for (Comunidade com : doUsuario) {
            nomes.add(com.getNome());
        }
        return "{" + String.join(",", nomes) + "}";
    }

    public void participarComunidade(String sessionId, String nomeComunidade, SessionService sessionService)
            throws SessaoInvalidaException, ComunidadeNaoExisteException, UsuarioJaPertenceException,
            FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        Comunidade comunidade = null;
        for (Comunidade com : comunidades) {
            if (com.getNome().equals(nomeComunidade)) {
                comunidade = com;
                break;
            }
        }

        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }

        if (comunidade.ehMembro(usuarioLogado.getLogin())) {
            throw new UsuarioJaPertenceException();
        }

        comunidade.adicionarMembro(usuarioLogado.getLogin());
        comunidade.registrarEntrada(usuarioLogado.getLogin(), proximaOrdemEntrada++);
        ComunidadeRepository.save(comunidades);
    }

    public void enviarMensagem(String sessionId, String nomeComunidade, String mensagemTexto,
            SessionService sessionService)
            throws SessaoInvalidaException, ComunidadeNaoExisteException, ApenasMembrosException,
            MensagemSemConteudoException, FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        Comunidade comunidade = null;
        for (Comunidade com : comunidades) {
            if (com.getNome().equals(nomeComunidade)) {
                comunidade = com;
                break;
            }
        }

        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }

        if (mensagemTexto == null || mensagemTexto.isEmpty()) {
            throw new MensagemSemConteudoException();
        }

        MensagemComunidade mensagem = new MensagemComunidade(usuarioLogado.getLogin(), nomeComunidade, mensagemTexto);
        comunidade.adicionarMensagem(mensagem);
        ComunidadeRepository.save(comunidades);
    }

    public String lerMensagem(String sessionId, SessionService sessionService)
            throws SessaoInvalidaException, NaoHaMensagensException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);
        String login = usuarioLogado.getLogin();

        for (Comunidade com : comunidades) {
            if (com.ehMembro(login)) {
                java.util.Map<String, Integer> lidas = com.getMensagensLidas();
                int idx = lidas.getOrDefault(login, 0);
                if (idx < com.getMensagens().size()) {
                    String texto = com.getMensagens().get(idx).getTexto();
                    lidas.put(login, idx + 1);
                    try {
                        ComunidadeRepository.save(comunidades);
                    } catch (Exception e) {
                    }
                    return texto;
                }
            }
        }
        throw new NaoHaMensagensException();
    }

    public void removerUsuario(String login) throws FalhaAoSalvarException {
        comunidades.removeIf(c -> c.getAdministrador().equals(login));
        for (Comunidade com : comunidades) {
            if (com.ehMembro(login)) {
                com.removerMembro(login);
            }
        }
        ComunidadeRepository.save(comunidades);
    }

    public List<Comunidade> getComunidadesList() {
        return this.comunidades;
    }

    public void clear() {
        this.comunidades.clear();
        ComunidadeRepository.clear();
    }
}