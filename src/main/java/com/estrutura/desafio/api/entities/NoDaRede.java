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

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "ta_no_rede")
public class NoDaRede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_no_rede")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Servidor.class)
	@JoinColumn(name = "cod_primeiro_servidor", nullable = false)
	private Servidor primeiroServidor;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Servidor.class)
	@JoinColumn(name = "cod_segundo_servidor", nullable = false)
	private Servidor segundoServidor;
	
	@Column(name = "desc_no_rede")
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	private String descricaoDaRede;
	
	public NoDaRede() {	}

	public NoDaRede(Servidor primeiroServidor, Servidor segundoServidor, String descricaoDaRede) {
		this.primeiroServidor = primeiroServidor;
		this.segundoServidor = segundoServidor;
		this.descricaoDaRede = descricaoDaRede;
	}

	public NoDaRede(Long id, Servidor primeiroServidor, Servidor segundoServidor, String descricaoDaRede) {
		this.id = id;
		this.primeiroServidor = primeiroServidor;
		this.segundoServidor = segundoServidor;
		this.descricaoDaRede = descricaoDaRede;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Servidor getPrimeiroServidor() {
		return primeiroServidor;
	}

	public void setPrimeiroServidor(Servidor primeiroServidor) {
		this.primeiroServidor = primeiroServidor;
	}

	public Servidor getSegundoServidor() {
		return segundoServidor;
	}

	public void setSegundoServidor(Servidor segundoServidor) {
		this.segundoServidor = segundoServidor;
	}

	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}

	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
}
