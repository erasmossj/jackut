package br.ufal.ic.jackut.models;

public class MensagemComunidade extends Mensagem {
    private String comunidade;

    public MensagemComunidade() {
        super();
    }

    public MensagemComunidade(String remetente, String comunidade, String texto) {
        super(remetente, texto);
        this.comunidade = comunidade;
    }

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }
}
