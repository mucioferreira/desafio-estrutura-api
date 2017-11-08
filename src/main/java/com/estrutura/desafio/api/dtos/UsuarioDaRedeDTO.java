package com.estrutura.desafio.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioDaRedeDTO {

	private Optional<Long> id;
	
	@NotNull(message = "Nó da Rede não pode ser vazio.")
	private NoDaRedeDTO noDaRede;
	
	@NotNull(message = "Usuário não pode ser vazio.")
	private UsuarioDTO usuario;
	
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	private String descricao;
	
	public UsuarioDaRedeDTO() {
		this.id = Optional.empty();
		this.descricao = new String();
	}
	
	public Long getId() {
		return id.isPresent() ? id.get() : null;
	}

	@JsonIgnore
	public Optional<Long> getIdOpt() {
		return id;
	}

	public void setId(Long id) {
		this.id = Optional.of(id);
	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public NoDaRedeDTO getNoDaRede() {
		return noDaRede;
	}

	public void setNoDaRede(NoDaRedeDTO noDaRede) {
		this.noDaRede = noDaRede;
	}

	@JsonIgnore
	public Optional<String> getDescricaoOpt() {
		return Optional.of(descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
