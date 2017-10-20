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
@Table(name = "tb_servidor")
public class Servidor {

	private Long id;
	private String nome;
	private String ip;
	private TipoServidor tipoServidor;
	
	public Servidor() {}
	
	public Servidor(String nome, String ip, TipoServidor tipoServidor) {
		this.nome = nome;
		this.ip = ip;
		this.tipoServidor = tipoServidor;
	}

	public Servidor(Long id, String nome, String ip, TipoServidor tipoServidor) {
		this.id = id;
		this.nome = nome;
		this.ip = ip;
		this.tipoServidor = tipoServidor;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_servidor")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "nme_servidor", nullable = false)
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	@Column(name = "ip_servidor", nullable = false)
	@NotEmpty(message = "IP não pode ser vazio.")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@NotEmpty(message = "Tipo do Servidor não pode ser vazio.")
	public TipoServidor getTipoServidor() {
		return tipoServidor;
	}

	public void setTipoServidor(TipoServidor tipoServidor) {
		this.tipoServidor = tipoServidor;
	}
	
}
