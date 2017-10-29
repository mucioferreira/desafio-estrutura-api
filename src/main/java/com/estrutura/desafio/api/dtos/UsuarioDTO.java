package com.estrutura.desafio.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioDTO {
	
	private Optional<Long> id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres.")
	private String nome;
	
	public UsuarioDTO() {
		this.id = Optional.empty();
	}

	public UsuarioDTO(Long id, String nome) {
		this.id = Optional.of(id);
		this.nome = nome;
	}
	
	public UsuarioDTO(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id.get();
	}
	
	@JsonIgnore
	public Optional<Long> getIdOpt() {
		return id;
	}

	public void setId(Long id) {
		this.id = Optional.of(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
