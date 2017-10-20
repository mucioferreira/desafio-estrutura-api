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
public class UsuarioDaRede {
	
	private Long id;
	private Servidor servidor;
	private Usuario usuario;
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

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_usuario_rede")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotEmpty(message = "Servidor não pode ser vazio.")
	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotEmpty(message = "Usuário não pode ser vazio.")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
