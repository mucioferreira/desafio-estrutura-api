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
@Table(name = "tb_usuario_da_rede")
public class UsuarioDaRede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idt_usuario_da_rede")
	private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = NoDaRede.class)
    @JoinColumn(name = "cod_no_da_rede", nullable = false)
    private NoDaRede noDaRede;
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
    @JoinColumn(name = "cod_usuario", nullable = false)
    private Usuario usuario;
    
	@Column(name = "desc_usuario_da_rede")
	private String descricaoDaRede;
	
	public UsuarioDaRede() {	}

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

	public NoDaRede getNoDaRede() {
		return noDaRede;
	}

	public void setNoDaRede(NoDaRede noDaRede) {
		this.noDaRede = noDaRede;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricaoDaRede() {
		return descricaoDaRede;
	}
	
    @Transient
	public Optional<String> getDescricaoDaRedeOpt() {
		return Optional.of(descricaoDaRede);
	}

	public void setDescricaoDaRede(String descricaoDaRede) {
		this.descricaoDaRede = descricaoDaRede;
	}
	
}
