package br.ufal.ic.jackut.models;

import br.ufal.ic.jackut.exceptions.RelacionamentoInvalidoException;
import br.ufal.ic.jackut.exceptions.AutoIdolatriaException;

public class Idolatria extends Relacionamento {
    public Idolatria() {
        super();
    }

    public Idolatria(String solicitante, String solicitado, boolean aceito) {
        super(solicitante, solicitado, aceito);
    }

    @Override
    public String tipo() { return "Idolatria"; }

    @Override
    public void validar() throws RelacionamentoInvalidoException {
        if (getSolicitante().equals(getSolicitado())) {
            throw new AutoIdolatriaException();
        }
    }

    @Override
    public void aplicar() {
        setAceito(true);
    }
}


