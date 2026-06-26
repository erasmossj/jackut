package br.ufal.ic.jackut.models;

import br.ufal.ic.jackut.exceptions.RelacionamentoInvalidoException;
import br.ufal.ic.jackut.exceptions.AutoInimizadeException;

public class Inimizade extends Relacionamento {
    public Inimizade() {
        super();
    }

    public Inimizade(String solicitante, String solicitado, boolean aceito) {
        super(solicitante, solicitado, aceito);
    }

    @Override
    public String tipo() { return "Inimizade"; }

    @Override
    public void validar() throws RelacionamentoInvalidoException {
        if (getSolicitante().equals(getSolicitado())) {
            throw new AutoInimizadeException();
        }
    }

    @Override
    public void aplicar() {
        setAceito(true);
    }
}


