package com.estrutura.desafio.api.entities;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.estrutura.desafio.api.enums.TipoServidorEnum;

@Entity
@Table(name = "tb_servidor")
public class Servidor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_servidor")
	private Long id;
	
	@Column(name = "nme_servidor", nullable = false)
	private String nome;
	
	@Column(name = "ip_servidor", nullable = false)
	private String ip;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tpo_servidor", nullable = false)
	private TipoServidorEnum tipo;
	
	public Servidor() {}

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
		return tipo;
	}

	public void setTipoServidor(TipoServidorEnum tipo) {
		this.tipo = tipo;
	}
	
}
