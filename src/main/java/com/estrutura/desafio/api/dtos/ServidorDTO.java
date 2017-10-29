package com.estrutura.desafio.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.estrutura.desafio.api.enums.TipoServidorEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ServidorDTO {
	
	private Optional<Long> id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres.")
	private String nome;
	
	@NotEmpty(message = "IP não pode ser vazio.")
	@Length(min = 3, message = "IP deve conter mais de 3 caracteres.")
	private String ip;
	
	@NotNull(message = "Tipo do Servidor não pode ser vazio.")
	private TipoServidorEnum tipoServidor;
	
	public ServidorDTO() {
		this.id = Optional.empty();
	}
	
	public ServidorDTO(Long id, String nome, String ip, TipoServidorEnum tipoServidor) {
		this.id = Optional.of(id);
		this.nome = nome;
		this.ip = ip;
		this.tipoServidor = tipoServidor;
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
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public TipoServidorEnum getTipoServidor() {
		return tipoServidor;
	}
	
	public String getNomeTipoServidor() {
		return TipoServidorEnum.getNomeTipoServidor(tipoServidor);
	}
	
	public void setTipoServidor(TipoServidorEnum tipoServidor) {
		this.tipoServidor = tipoServidor;
	}
	
}
