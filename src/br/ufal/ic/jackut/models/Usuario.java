package br.ufal.ic.jackut.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {
	private String login;
	private String nome;
	private String senha;
	private Map<String, String> atributos;

	public Usuario() {
		this.atributos = new HashMap<>();
	}

	public Usuario(String login, String nome, String senha) {
		this.login = login;
		this.nome = nome;
		this.senha = senha;
		this.atributos = new HashMap<>();
	}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Map<String, String> getAtributos() {
		return new java.util.HashMap<>(atributos);
	}

	public void setAtributos(Map<String, String> atributos) {
		this.atributos = atributos;
	}

	public String getAtributo(String atributo) {
		return atributos.get(atributo);
	}

	public void setAtributo(String atributo, String valor) {
		this.atributos.put(atributo, valor);
	}
}

