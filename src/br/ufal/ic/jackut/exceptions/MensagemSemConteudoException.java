package br.ufal.ic.jackut.exceptions;

public class MensagemSemConteudoException extends Exception {
    public MensagemSemConteudoException() {
        super("Mensagem deve possuir conteúdo.");
    }
}
