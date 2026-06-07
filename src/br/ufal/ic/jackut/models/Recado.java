package br.ufal.ic.jackut.models;

public class Recado {
    private String remetente;
    private String destinatario;
    private String texto;

    public Recado() {
    }

    public Recado(String remetente, String destinatario, String texto) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.texto = texto;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
