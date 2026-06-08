package br.ufal.ic.jackut.models;

public class Amizade {
    private String solicitante;
    private String solicitado;
    private boolean aceito;

    public Amizade() {
    }

    public Amizade(String solicitante, String solicitado, boolean aceito) {
        this.solicitante = solicitante;
        this.solicitado = solicitado;
        this.aceito = aceito;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(String solicitado) {
        this.solicitado = solicitado;
    }

    public boolean isAceito() {
        return aceito;
    }

    public void setAceito(boolean aceito) {
        this.aceito = aceito;
    }
}
