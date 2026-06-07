package br.ufal.ic.jackut.services;

import br.ufal.ic.jackut.models.Amizade;
import br.ufal.ic.jackut.models.Usuario;
import br.ufal.ic.jackut.repository.AmizadeRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.exceptions.SessaoInvalidaException;
import br.ufal.ic.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exceptions.AutoAmizadeException;
import br.ufal.ic.jackut.exceptions.AmizadeJaAdicionadaException;
import br.ufal.ic.jackut.exceptions.AmizadePendenteException;
import br.ufal.ic.jackut.utils.Validador;

import java.util.List;
import java.util.ArrayList;

public class AmizadeService {
    private List<Amizade> amizades;

    public AmizadeService() {
        this.amizades = AmizadeRepository.load();
    }

    public void adicionarAmigo(String sessionId, String amigoLogin, SessionService sessionService) 
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, AutoAmizadeException, 
                   AmizadeJaAdicionadaException, AmizadePendenteException, br.ufal.ic.jackut.exceptions.FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        String meuLogin = usuarioLogado.getLogin();

        Validador.validarUsuarioExistente(amigoLogin);

        if (meuLogin.equals(amigoLogin)) {
            throw new AutoAmizadeException();
        }

        for (Amizade amizade : amizades) {
            if ((amizade.getSolicitante().equals(meuLogin) && amizade.getSolicitado().equals(amigoLogin)) ||
                (amizade.getSolicitante().equals(amigoLogin) && amizade.getSolicitado().equals(meuLogin))) {
                
                if (amizade.isAceito()) {
                    throw new AmizadeJaAdicionadaException();
                }

                if (amizade.getSolicitante().equals(meuLogin)) {
                    throw new AmizadePendenteException();
                } else {
                    amizade.setAceito(true);
                    AmizadeRepository.save(amizades);
                    return;
                }
            }
        }

        amizades.add(new Amizade(meuLogin, amigoLogin, false));
        AmizadeRepository.save(amizades);
    }

    public boolean ehAmigo(String login1, String login2) {
        for (Amizade amizade : amizades) {
            if (amizade.isAceito() && 
                ((amizade.getSolicitante().equals(login1) && amizade.getSolicitado().equals(login2)) ||
                 (amizade.getSolicitante().equals(login2) && amizade.getSolicitado().equals(login1)))) {
                return true;
            }
        }
        return false;
    }

    public String getAmigos(String login) {
        List<String> meusAmigos = new ArrayList<>();
        for (Amizade amizade : amizades) {
            if (amizade.isAceito()) {
                if (amizade.getSolicitante().equals(login)) {
                    meusAmigos.add(amizade.getSolicitado());
                } else if (amizade.getSolicitado().equals(login)) {
                    meusAmigos.add(amizade.getSolicitante());
                }
            }
        }
        return "{" + String.join(",", meusAmigos) + "}";
    }

    public void clear() {
        this.amizades = new ArrayList<>();
        AmizadeRepository.clear();
    }

    public List<Amizade> getAmizades() {
        return this.amizades;
    }
}
