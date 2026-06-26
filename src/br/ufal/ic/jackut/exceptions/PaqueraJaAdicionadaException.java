package br.ufal.ic.jackut.exceptions;

public class PaqueraJaAdicionadaException extends RelacionamentoJaExisteException {
    public PaqueraJaAdicionadaException() {
        super("Usuário já está adicionado como paquera.");
    }
}