package br.ufal.ic.jackut.models;

import br.ufal.ic.jackut.exceptions.RelacionamentoInvalidoException;
import br.ufal.ic.jackut.exceptions.AutoAmizadeException;

public class Amizade extends Relacionamento {
    public Amizade() {
        super();
    }

    public Amizade(String solicitante, String solicitado, boolean aceito) {
        super(solicitante, solicitado, aceito);
    }

    @Override
    public String tipo() { return "Amizade"; }

    @Override
    public void validar() throws RelacionamentoInvalidoException {
        if (getSolicitante().equals(getSolicitado())) {
            throw new AutoAmizadeException();
        }
    }

    @Override
    public void aplicar() {
        setAceito(true);
    }
}


