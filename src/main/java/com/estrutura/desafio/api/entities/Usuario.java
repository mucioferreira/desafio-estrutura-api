package com.estrutura.desafio.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	private Long id;
	private String nome;
	
	public Usuario() { }
	
	public Usuario(String nome) {
		this.nome = nome;
	}
	
	public Usuario(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_usuairo")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "nme_usuario", nullable = false)
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
