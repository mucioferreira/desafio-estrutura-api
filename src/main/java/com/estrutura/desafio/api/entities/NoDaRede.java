package com.estrutura.desafio.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ta_no_rede")
public class NoDaRede {
	
	private Long id;
	private Servidor primeiroServidor;
	private Servidor segundoServidor;
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

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_no_rede")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotEmpty(message = "Primeiro Servidor não pode ser vazio.")
	public Servidor getPrimeiroServidor() {
		return primeiroServidor;
	}

	public void setPrimeiroServidor(Servidor primeiroServidor) {
		this.primeiroServidor = primeiroServidor;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotEmpty(message = "Segundo Servidor não pode ser vazio.")
	public Servidor getSegundoServidor() {
		return segundoServidor;
	}

	public void setSegundoServidor(Servidor segundoServidor) {
		this.segundoServidor = segundoServidor;
	}

	
	@Column(name = "desc_no_rede")
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}

	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
}
