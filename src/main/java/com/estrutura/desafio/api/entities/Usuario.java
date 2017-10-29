package com.estrutura.desafio.api.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UsuarioDaRede.class)
	private List<UsuarioDaRede> usuarioDaRede;
	
	public Usuario() { }
	
	public Usuario(String nome) {
		this.nome = nome;
	}
	
	public Usuario(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<UsuarioDaRede> getUsuarioDaRede() {
		return usuarioDaRede;
	}

	public void setUsuarioDaRede(List<UsuarioDaRede> usuarioDaRede) {
		this.usuarioDaRede = usuarioDaRede;
	}

}
