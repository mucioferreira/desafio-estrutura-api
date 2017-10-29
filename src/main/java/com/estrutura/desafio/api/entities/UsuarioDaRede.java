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

@Entity
@Table(name = "usuariosdarede")
public class UsuarioDaRede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Servidor.class)
    @JoinColumn(name = "servidor", nullable = false)
    private Servidor servidor;
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;
    
	@Column(name = "descricao")
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
