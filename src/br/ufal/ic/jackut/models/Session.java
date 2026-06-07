package br.ufal.ic.jackut.models;

import java.io.Serializable;

public class Session implements Serializable {
	private String id;
	private Usuario usuario;

	public Session() {
	}

	public Session(String id, Usuario usuario) {
		this.id = id;
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}


