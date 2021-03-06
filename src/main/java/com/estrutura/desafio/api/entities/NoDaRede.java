package com.estrutura.desafio.api.entities;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;

@Entity
@Table(name = "tb_no_da_rede")
public class NoDaRede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_no_da_rede")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Servidor.class)
	@JoinColumn(name = "cod_servidor", nullable = false)
	private Servidor servidor;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = NoDaRede.class)
	@JoinColumn(name = "cod_proximo_no", nullable = true)
	private NoDaRede proximoNo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "amb_no_da_rede", nullable = false)
	private AmbienteDaRedeEnum ambienteDaRede;
	
	@Column(name = "desc_no_da_rede")
	private String descricaoDaRede;
	
	public NoDaRede() {	}

	public Long getId() {
		return id;
	}
	
	@Transient
	public Optional<Long> getIdOpt() {
		return Optional.of(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public NoDaRede getProximoNo() {
		return proximoNo;
	}
	
	@Transient
	public Optional<NoDaRede> getProximoNoOpt() {
		return (this.proximoNo != null) ? Optional.of(this.proximoNo) : Optional.empty();
	}

	public void setProximoNo(NoDaRede proximoNo) {
		this.proximoNo = proximoNo;
	}

	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}

	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
    @Transient
	public Optional<String> getDescricaoDaRedeOpt() {
		return Optional.of(descricaoDaRede);
	}

	public AmbienteDaRedeEnum getAmbienteDaRede() {
		return ambienteDaRede;
	}

	public void setAmbienteDaRede(AmbienteDaRedeEnum ambienteDaRede) {
		this.ambienteDaRede = ambienteDaRede;
	}
	
}
