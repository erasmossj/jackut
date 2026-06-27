package br.ufal.ic.jackut.exceptions;

public class InimigoException extends Exception {
    public InimigoException() {
        super("É seu inimigo.");
    }

    public InimigoException(String msg) {
        super(msg);
    }
}
