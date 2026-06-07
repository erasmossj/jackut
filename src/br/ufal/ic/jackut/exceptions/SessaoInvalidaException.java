package br.ufal.ic.jackut.exceptions;

public class SessaoInvalidaException extends Exception {
	public SessaoInvalidaException() {
		super("Usuário não cadastrado.");
	}

	public SessaoInvalidaException(String message) {
		super(message);
	}
}


