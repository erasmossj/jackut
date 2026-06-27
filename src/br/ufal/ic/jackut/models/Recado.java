package br.ufal.ic.jackut.models;

public class Recado extends Mensagem {
    private String destinatario;

    public Recado() {
        super();
    }

    public Recado(String remetente, String destinatario, String texto) {
        super(remetente, texto);
        this.destinatario = destinatario;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}
