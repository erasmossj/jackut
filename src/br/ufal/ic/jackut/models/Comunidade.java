package br.ufal.ic.jackut.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comunidade implements Serializable {
    private String nome;
    private String descricao;
    private String administrador;
    private List<String> membros;
    private List<MensagemComunidade> mensagens;
    private java.util.Map<String, Integer> mensagensLidas;
    private java.util.Map<String, Integer> ordemEntrada;

    public Comunidade() {
        this.membros = new ArrayList<>();
        this.mensagens = new ArrayList<>();
        this.mensagensLidas = new java.util.HashMap<>();
        this.ordemEntrada = new java.util.HashMap<>();
    }

    public Comunidade(String nome, String descricao, String administrador) {
        this.nome = nome;
        this.descricao = descricao;
        this.administrador = administrador;
        this.membros = new ArrayList<>();
        this.mensagens = new ArrayList<>();
        this.mensagensLidas = new java.util.HashMap<>();
        this.ordemEntrada = new java.util.HashMap<>();
        this.membros.add(administrador);
        this.mensagensLidas.put(administrador, 0);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public List<String> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    public void setMembros(List<String> membros) {
        this.membros = new ArrayList<>(membros);
    }

    public void adicionarMembro(String membro) {
        if (!membros.contains(membro)) {
            membros.add(membro);
            mensagensLidas.put(membro, mensagens.size());
        }
    }

    public void removerMembro(String membro) {
        membros.remove(membro);
        mensagensLidas.remove(membro);
        ordemEntrada.remove(membro);
    }

    public boolean ehMembro(String usuarioLogin) {
        return membros.contains(usuarioLogin);
    }

    public java.util.Map<String, Integer> getOrdemEntrada() {
        return new java.util.HashMap<>(ordemEntrada);
    }

    public void setOrdemEntrada(java.util.Map<String, Integer> ordemEntrada) {
        this.ordemEntrada = ordemEntrada;
    }

    public void registrarEntrada(String membro, int ordem) {
        this.ordemEntrada.put(membro, ordem);
    }

    public Integer getOrdemEntradaDe(String membro) {
        return ordemEntrada.get(membro);
    }

    public List<MensagemComunidade> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MensagemComunidade> mensagens) {
        this.mensagens = mensagens;
    }

    public void adicionarMensagem(MensagemComunidade mensagem) {
        this.mensagens.add(mensagem);
    }

    public java.util.Map<String, Integer> getMensagensLidas() {
        return mensagensLidas;
    }

    public void setMensagensLidas(java.util.Map<String, Integer> mensagensLidas) {
        this.mensagensLidas = mensagensLidas;
    }
}