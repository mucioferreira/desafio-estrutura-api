package com.estrutura.desafio.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NoDaRedeDTO {

	private Optional<Long> id;
	
	@NotNull(message = "Servidor não pode ser vazio.")
	private Long servidor;
	
	private Long proximoNo;
	
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	private String descricaoDaRede;
	
	@NotNull(message = "Ambiente da Rede não pode ser vazio.")
	private AmbienteDaRedeEnum ambienteDaRede;
	
	public NoDaRedeDTO() {
		this.id = Optional.empty();
	}
	
	public NoDaRedeDTO(Long id, Long servidor, Long proximoNo, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.id = Optional.of(id);
		this.servidor = servidor;
		this.proximoNo = proximoNo;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}

	public NoDaRedeDTO(Long servidor, Long proximoNo, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.servidor = servidor;
		this.proximoNo = proximoNo;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}

	public NoDaRedeDTO(Long servidor, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.servidor = servidor;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}
	
	public NoDaRedeDTO(Long id, String descricaoDaRede, Long servidor, AmbienteDaRedeEnum ambienteDaRede) {
		this.id = Optional.of(id);
		this.servidor = servidor;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
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
	
	public Long getProximoNo() {
		return proximoNo;
	}
	
	public void setProximoNo(Long proximoNo) {
		this.proximoNo = proximoNo;
	}
	
	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}
	
	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
	public AmbienteDaRedeEnum getAmbienteDaRede() {
		return ambienteDaRede;
	}
	
	public void setAmbienteDaRede(AmbienteDaRedeEnum ambienteDaRede) {
		this.ambienteDaRede = ambienteDaRede;
	}
	
}
