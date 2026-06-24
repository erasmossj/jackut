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
import br.ufal.ic.jackut.utils.Validador;

import java.util.List;
import java.util.ArrayList;

public class AmizadeService {
    private List<Relacionamento> relacionamentos;

    public AmizadeService() {
        this.relacionamentos = AmizadeRepository.load();
    }

    public void adicionarAmigo(String sessionId, String amigoLogin, SessionService sessionService) 
            throws SessaoInvalidaException, UsuarioNaoCadastradoException, RelacionamentoInvalidoException, 
                   AmizadeJaAdicionadaException, AmizadePendenteException, br.ufal.ic.jackut.exceptions.FalhaAoSalvarException {
        Usuario usuarioLogado = sessionService.obterUsuarioDaSessao(sessionId);
        Validador.validarSessao(usuarioLogado);

        String meuLogin = usuarioLogado.getLogin();

        Validador.validarUsuarioExistente(amigoLogin);

        Relacionamento novoRelacionamento = RelacionamentoFactory.criarRelacionamento("amizade", meuLogin, amigoLogin);
        novoRelacionamento.validar();

        for (Relacionamento rel : relacionamentos) {
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

        relacionamentos.add(novoRelacionamento);
        AmizadeRepository.save(relacionamentos);
    }

    public boolean ehAmigo(String login1, String login2) {
        for (Relacionamento rel : relacionamentos) {
            if (rel.isAceito() && 
                ((rel.getSolicitante().equals(login1) && rel.getSolicitado().equals(login2)) ||
                 (rel.getSolicitante().equals(login2) && rel.getSolicitado().equals(login1)))) {
                return true;
            }
        }
        return false;
    }

    public String getAmigos(String login) {
        List<String> meusAmigos = new ArrayList<>();
        for (Relacionamento rel : relacionamentos) {
            if (rel.isAceito()) {
                if (rel.getSolicitante().equals(login)) {
                    meusAmigos.add(rel.getSolicitado());
                } else if (rel.getSolicitado().equals(login)) {
                    meusAmigos.add(rel.getSolicitante());
                }
            }
        }
        return "{" + String.join(",", meusAmigos) + "}";
    }

    public void clear() {
        this.relacionamentos = new ArrayList<>();
        AmizadeRepository.clear();
    }

    public List<Relacionamento> getAmizades() {
        return this.relacionamentos;
    }
}
