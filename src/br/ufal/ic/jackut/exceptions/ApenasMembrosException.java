package br.ufal.ic.jackut.exceptions;

public class ApenasMembrosException extends Exception {
    public ApenasMembrosException() {
        super("Apenas membros podem publicar.");
    }
}
