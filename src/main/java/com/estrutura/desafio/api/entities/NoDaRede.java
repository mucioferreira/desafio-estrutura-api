package com.estrutura.desafio.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;

@Entity
@Table(name = "nosdarede")
public class NoDaRede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Servidor.class)
	@JoinColumn(name = "servidor", nullable = false)
	private Servidor servidor;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = NoDaRede.class)
	@JoinColumn(name = "proximo_no")
	private NoDaRede proximoNo;
	
	@Column(name = "ambiente", nullable = false)
	private AmbienteDaRedeEnum ambienteDaRede;
	
	@Column(name = "descricao")
	private String descricaoDaRede;
	
	public NoDaRede() {	}

	public NoDaRede(Servidor servidor, NoDaRede proximoNo, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.servidor = servidor;
		this.proximoNo = proximoNo;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}
	
	public NoDaRede(Servidor servidor, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.servidor = servidor;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}
	
	public NoDaRede(Long id, Servidor servidor, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.id = id;
		this.servidor = servidor;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}

	public NoDaRede(Long id, Servidor servidor, NoDaRede proximoNo, String descricaoDaRede, AmbienteDaRedeEnum ambienteDaRede) {
		this.id = id;
		this.servidor = servidor;
		this.proximoNo = proximoNo;
		this.descricaoDaRede = descricaoDaRede;
		this.ambienteDaRede = ambienteDaRede;
	}

	public Long getId() {
		return id;
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

	public void setProximoNo(NoDaRede proximoNo) {
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
