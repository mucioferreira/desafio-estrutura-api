package com.estrutura.desafio.api.entities;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ta_usuario_rede")
public class UsuarioDaRede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_usuario_rede")
	private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Servidor.class)
    @JoinColumn(name = "cod_servidor", nullable = false)
	private Servidor servidor;
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
    @JoinColumn(name = "cod_usuario", nullable = false)
	private Usuario usuario;
    
	@Column(name = "desc_rede")
	@Length(max = 255, message = "Descrição deve conter até 255 caracteres.")
	private String descricaoDaRede;
	
	public UsuarioDaRede() {	}
	
	public UsuarioDaRede(Servidor servidor, Usuario usuario, String descricaoDaRede) {
		this.servidor = servidor;
		this.usuario = usuario;
		this.descricaoDaRede = descricaoDaRede;
	}

	public UsuarioDaRede(Long id, Servidor servidor, Usuario usuario, String descricaoDaRede) {
		this.id = id;
		this.servidor = servidor;
		this.usuario = usuario;
		this.descricaoDaRede = descricaoDaRede;
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
    
    @Transient
    @JsonIgnore
	public Optional<Servidor> getServidorOpt() {
		return Optional.of(servidor);
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Usuario getUsuario() {
		return usuario;
	}
    
    @Transient
    @JsonIgnore
	public Optional<Usuario> getUsuarioOpt() {
		return Optional.of(usuario);
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}

	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
}
