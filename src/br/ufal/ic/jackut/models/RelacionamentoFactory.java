package br.ufal.ic.jackut.models;

import br.ufal.ic.jackut.exceptions.RelacionamentoInvalidoException;

public class RelacionamentoFactory {
    public static Relacionamento criarRelacionamento(String tipo, String solicitante, String solicitado) throws RelacionamentoInvalidoException {
        switch (tipo.toLowerCase()) {
            case "amizade":
                return new Amizade(solicitante, solicitado, false);
            case "idolatria":
                return new Idolatria(solicitante, solicitado, false);
            case "paquera":
                return new Paquera(solicitante, solicitado, false);
            case "inimizade":
                return new Inimizade(solicitante, solicitado, false);
            default:
                throw new br.ufal.ic.jackut.exceptions.TipoRelacionamentoInvalidoException();
        }
    }
}
