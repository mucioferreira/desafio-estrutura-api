package com.estrutura.desafio.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioDaRedeDTO {

	private Optional<Long> id;
	
	@NotNull(message = "Servidor não pode ser vazio.")
	private Long servidor;
	
	@NotNull(message = "Usuário não pode ser vazio.")
	private Long usuario;
	
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	private String descricaoDaRede;
	
	public UsuarioDaRedeDTO() {
		this.id = Optional.empty();
	}
	
	public UsuarioDaRedeDTO(Long id, Long servidor, Long usuario, String descricaoDaRede) {
		this.id = Optional.of(id);
		this.servidor = servidor;
		this.usuario = usuario;
		this.descricaoDaRede = descricaoDaRede;
	}
	
	public UsuarioDaRedeDTO(Long servidor, Long usuario, String descricaoDaRede) {
		this.servidor = servidor;
		this.usuario = usuario;
		this.descricaoDaRede = descricaoDaRede;
	}

	public Long getId() {
		return id.get();
	}
	
    @JsonIgnore
	public Optional<Long> getIdOpt() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public Long getServidor() {
		return servidor;
	}

	public void setServidor(Long servidor) {
		this.servidor = servidor;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}
	
	@JsonIgnore
	public Optional<String> getDescricaoDaRedeOpt() {
		return Optional.of(descricaoDaRede);
	}

	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
}
