package br.ufal.ic.jackut;

import br.ufal.ic.jackut.services.*;
import br.ufal.ic.jackut.models.*;
import br.ufal.ic.jackut.repository.*;
import br.ufal.ic.jackut.exceptions.*;
import br.ufal.ic.jackut.utils.*;

public class Facade {
    public SessionService sessionService;
    public UsuarioService usuarioService;
    public AmizadeService amizadeService;
    public RecadoService recadoService;
    public ComunidadeService comunidadeService;

    public Facade() {
        this.sessionService = new SessionService();
        this.usuarioService = new UsuarioService(UsuarioRepository.load());
        this.amizadeService = new AmizadeService();
        this.recadoService = new RecadoService();
        this.comunidadeService = new ComunidadeService();
    }

    public void zerarSistema() {
        this.sessionService.limpar();
        this.usuarioService.clear();
        this.amizadeService.clear();
        this.recadoService.clear();
        this.comunidadeService.clear();
    }

    public void encerrarSistema() {
    }

    public void criarUsuario(String login, String senha, String nome)
            throws ContaNaExisteException, LoginInvalidoException, SenhaInvalidaException, FalhaAoSalvarException {
        this.usuarioService.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        return this.usuarioService.abrirSessao(login, senha, sessionService);
    }

    public String getAtributoUsuario(String login, String atributo)
            throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        return this.usuarioService.getAtributoUsuario(login, atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) throws SessaoInvalidaException {
        this.usuarioService.editarPerfil(id, atributo, valor, sessionService);
    }

    public void adicionarAmigo(String id, String amigo)
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, RelacionamentoInvalidoException,
            AmizadeJaAdicionadaException, AmizadePendenteException, FalhaAoSalvarException, InimigoException {
        this.amizadeService.adicionarAmigo(id, amigo, sessionService);
    }

    public boolean ehAmigo(String login, String amigo) {
        return this.amizadeService.ehAmigo(login, amigo);
    }

    public String getAmigos(String arg) throws SessaoInvalidaException {
        String login = arg;
        try {
            Usuario u = sessionService.obterUsuarioDaSessao(arg);
            if (u != null)
                login = u.getLogin();
        } catch (Exception e) {
        }
        return this.amizadeService.getAmigos(login);
    }

    public void enviarRecado(String id, String destinatario, String recado) throws SessaoInvalidaException,
            UsuarioNaoCadastradoException, AutoRecadoException, InimigoException, FalhaAoSalvarException {
        this.amizadeService.verificarInimigo(sessionService.obterUsuarioDaSessao(id).getLogin(), destinatario);
        this.recadoService.enviarRecado(id, destinatario, recado, sessionService);
    }

    public String lerRecado(String id)
            throws SessaoInvalidaException, br.ufal.ic.jackut.exceptions.NaoHaRecadosException, FalhaAoSalvarException {
        return this.recadoService.lerRecado(id, sessionService);
    }

    public void criarComunidade(String id, String nome, String descricao)
            throws SessaoInvalidaException, ComunidadeJaExisteException, FalhaAoSalvarException {
        this.comunidadeService.criarComunidade(id, nome, descricao, sessionService);
    }

    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return this.comunidadeService.getComunidade(nome).getDescricao();
    }

    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return this.comunidadeService.getComunidade(nome).getAdministrador();
    }

    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        return "{" + String.join(",", this.comunidadeService.getComunidade(nome).getMembros()) + "}";
    }

    public String getComunidades(String arg) throws UsuarioNaoCadastradoException {
        String login = arg;
        try {
            Usuario u = sessionService.obterUsuarioDaSessao(arg);
            if (u != null)
                login = u.getLogin();
        } catch (Exception e) {
        }
        return this.comunidadeService.getComunidades(login);
    }

    public void adicionarComunidade(String sessao, String nome) throws SessaoInvalidaException,
            ComunidadeNaoExisteException, UsuarioJaPertenceException, FalhaAoSalvarException {
        this.comunidadeService.participarComunidade(sessao, nome, sessionService);
    }

    public void enviarMensagem(String id, String comunidade, String mensagem) throws SessaoInvalidaException,
            ComunidadeNaoExisteException, ApenasMembrosException, MensagemSemConteudoException, FalhaAoSalvarException {
        this.comunidadeService.enviarMensagem(id, comunidade, mensagem, sessionService);
    }

    public String lerMensagem(String id) throws SessaoInvalidaException, NaoHaMensagensException {
        return this.comunidadeService.lerMensagem(id, sessionService);
    }

    public void adicionarIdolo(String id, String idolo) throws SessaoInvalidaException, UsuarioNaoCadastradoException,
            RelacionamentoInvalidoException, RelacionamentoJaExisteException, FalhaAoSalvarException, InimigoException {
        this.amizadeService.adicionarRelacionamento(id, "idolatria", idolo, sessionService);
    }

    public boolean ehFa(String login, String idolo) {
        return this.amizadeService.ehFa(login, idolo);
    }

    public String getFas(String login) {
        return this.amizadeService.getFas(login);
    }

    public void adicionarPaquera(String id, String paquera) throws SessaoInvalidaException,
            UsuarioNaoCadastradoException, RelacionamentoInvalidoException, RelacionamentoJaExisteException,
            FalhaAoSalvarException, InimigoException, UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        this.amizadeService.adicionarRelacionamento(id, "paquera", paquera, sessionService);
        String meuLogin = sessionService.obterUsuarioDaSessao(id).getLogin();

        boolean eleMePaquera = false;
        for (Relacionamento rel : this.amizadeService.getAmizades()) {
            if (rel.tipo().equals("Paquera") && rel.getSolicitante().equals(paquera)
                    && rel.getSolicitado().equals(meuLogin)) {
                eleMePaquera = true;
                break;
            }
        }

        if (eleMePaquera) {
            String meuNome = this.usuarioService.getAtributoUsuario(meuLogin, "nome");
            String nomeDele = this.usuarioService.getAtributoUsuario(paquera, "nome");

            this.recadoService.getRecados().add(new Recado("Jackut", meuLogin,
                    nomeDele + " é seu paquera - Recado do Jackut."));
            this.recadoService.getRecados().add(new Recado("Jackut", paquera,
                    meuNome + " é seu paquera - Recado do Jackut."));
            RecadoRepository.save(this.recadoService.getRecados());
        }
    }

    public boolean ehPaquera(String id, String paquera) {
        return this.amizadeService.ehPaquera(id, paquera, sessionService);
    }

    public String getPaqueras(String id) {
        return this.amizadeService.getPaqueras(id, sessionService);
    }

    public void adicionarInimigo(String id, String inimigo)
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, RelacionamentoInvalidoException,
            RelacionamentoJaExisteException, FalhaAoSalvarException, InimigoException {
        this.amizadeService.adicionarRelacionamento(id, "inimizade", inimigo, sessionService);
    }

    public void removerUsuario(String id) throws SessaoInvalidaException, FalhaAoSalvarException {
        Usuario u = sessionService.obterUsuarioDaSessao(id);
        Validador.validarSessao(u);
        String login = u.getLogin();

        this.amizadeService.removerUsuario(login);
        this.recadoService.removerUsuario(login);
        this.comunidadeService.removerUsuario(login);
        this.usuarioService.removerUsuario(id, sessionService);
    }
}
