package br.ufal.ic.jackut.exceptions;

public class AtributoNaoPreenchidoException extends Exception {
	public AtributoNaoPreenchidoException() {
		super("Atributo não preenchido.");
	}

	public AtributoNaoPreenchidoException(String message) {
		super(message);
	}
}


