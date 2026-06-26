package br.ufal.ic.jackut.models;

import br.ufal.ic.jackut.exceptions.RelacionamentoInvalidoException;
import br.ufal.ic.jackut.exceptions.AutoPaqueraException;

public class Paquera extends Relacionamento {
    public Paquera() {
        super();
    }

    public Paquera(String solicitante, String solicitado, boolean aceito) {
        super(solicitante, solicitado, aceito);
    }

    @Override
    public String tipo() { return "Paquera"; }

    @Override
    public void validar() throws RelacionamentoInvalidoException {
        if (getSolicitante().equals(getSolicitado())) {
            throw new AutoPaqueraException();
        }
    }

    @Override
    public void aplicar() {
        setAceito(true);
    }
}


