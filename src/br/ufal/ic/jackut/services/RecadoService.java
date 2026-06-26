package br.ufal.ic.jackut.services;

import br.ufal.ic.jackut.models.Recado;
import br.ufal.ic.jackut.models.Usuario;
import br.ufal.ic.jackut.repository.RecadoRepository;
import br.ufal.ic.jackut.exceptions.SessaoInvalidaException;
import br.ufal.ic.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exceptions.AutoRecadoException;
import br.ufal.ic.jackut.exceptions.NaoHaRecadosException;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;
import br.ufal.ic.jackut.utils.Validador;

import java.util.List;
import java.util.ArrayList;

public class RecadoService {
    private List<Recado> recados;

    public RecadoService() {
        this.recados = RecadoRepository.load();
    }

    public void enviarRecado(String sessionId, String destinatario, String texto, SessionService sessionService)
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, AutoRecadoException,
            br.ufal.ic.jackut.exceptions.FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        String meuLogin = usuarioLogado.getLogin();

        Validador.validarUsuarioExistente(destinatario);

        if (meuLogin.equals(destinatario)) {
            throw new AutoRecadoException();
        }

        recados.add(new Recado(meuLogin, destinatario, texto));
        RecadoRepository.save(recados);
    }

    public String lerRecado(String sessionId, SessionService sessionService)
            throws SessaoInvalidaException, NaoHaRecadosException, br.ufal.ic.jackut.exceptions.FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        String meuLogin = usuarioLogado.getLogin();

        for (int i = 0; i < recados.size(); i++) {
            Recado recado = recados.get(i);
            if (recado.getDestinatario().equals(meuLogin)) {
                String lido = recado.getTexto();
                recados.remove(i);
                RecadoRepository.save(recados);
                return lido;
            }
        }

        throw new NaoHaRecadosException();
    }

    public void clear() {
        this.recados = new ArrayList<>();
        RecadoRepository.clear();
    }

    public void removerUsuario(String login) {
        recados.removeIf(r -> r.getRemetente().equals(login) || r.getDestinatario().equals(login));
        try {
            RecadoRepository.save(recados);
        } catch (FalhaAoSalvarException e) {
        }
    }

    public List<Recado> getRecados() {
        return this.recados;
    }
}
