package com.estrutura.desafio.api.entities;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.estrutura.desafio.api.enums.TipoServidorEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "tb_servidor")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Servidor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_servidor")
	private Long id;
	
	@Column(name = "nme_servidor", nullable = false)
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres.")
	private String nome;
	
	@Column(name = "ip_servidor", nullable = false)
	@NotEmpty(message = "IP não pode ser vazio.")
	private String ip;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_servidor", nullable = false)
	private TipoServidorEnum tipoServidor;

	@OneToMany(mappedBy = "primeiroServidor", fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = NoDaRede.class)
	private List<NoDaRede> noDaRede;
	
	public Servidor() {}
	
	public Servidor(String nome, String ip, TipoServidorEnum tipoServidor) {
		this.nome = nome;
		this.ip = ip;
		this.tipoServidor = tipoServidor;
	}

	public Servidor(Long id, String nome, String ip, TipoServidorEnum tipoServidor) {
		this.id = id;
		this.nome = nome;
		this.ip = ip;
		this.tipoServidor = tipoServidor;
	}

	public Long getId() {
		return id;
	}
	
	@Transient
	@JsonIgnore
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
		return tipoServidor;
	}
	
	@Transient
	public String getNomeTipoServidor() {
		return TipoServidorEnum.getNomeTipoServidor(tipoServidor);
	}

	public void setTipoServidor(TipoServidorEnum tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

	public List<NoDaRede> getNoDaRede() {
		return noDaRede;
	}

	public void setNoDaRede(List<NoDaRede> noDaRede) {
		this.noDaRede = noDaRede;
	}
	
}
