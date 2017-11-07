package com.estrutura.desafio.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NoDaRedeDTO {

	private Optional<Long> id;
	
	@NotNull(message = "Servidor não pode ser vazio.")
	private ServidorDTO servidor;
	
	private Long proximo;
	
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	private String descricaoDaRede;
	
	@NotNull(message = "Ambiente da Rede não pode ser vazio.")
	private AmbienteDaRedeEnum ambienteDaRede;
	
	public NoDaRedeDTO() {
		this.id = Optional.empty();
		this.descricaoDaRede = new String();
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
	
	public ServidorDTO getServidor() {
		return servidor;
	}
	
	public void setServidor(ServidorDTO servidor) {
		this.servidor = servidor;
	}
	
	public Long getProximo() {
		return proximo;
	}
	
	public void setProximo(Long proximo) {
		this.proximo = proximo;
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
	
	public AmbienteDaRedeEnum getAmbienteDaRede() {
		return ambienteDaRede;
	}
	
	public String getNomeAmbienteDaRede() {
		return AmbienteDaRedeEnum.getNomeTipoServidor(ambienteDaRede);
	}
	
	public void setAmbienteDaRede(AmbienteDaRedeEnum ambienteDaRede) {
		this.ambienteDaRede = ambienteDaRede;
	}
	
}
