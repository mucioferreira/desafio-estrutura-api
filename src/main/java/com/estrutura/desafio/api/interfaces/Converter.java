package com.estrutura.desafio.api.interfaces;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.estrutura.desafio.api.dtos.NoDaRedeDTO;
import com.estrutura.desafio.api.dtos.ServidorDTO;
import com.estrutura.desafio.api.dtos.UsuarioDTO;
import com.estrutura.desafio.api.dtos.UsuarioDaRedeDTO;
import com.estrutura.desafio.api.entities.NoDaRede;
import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.entities.UsuarioDaRede;

@Component
public interface Converter {
	
	public UsuarioDTO converterParaDTO(Usuario usuario);

	public ServidorDTO converterParaDTO(Servidor servidor);

	public UsuarioDaRedeDTO converterParaDTO(UsuarioDaRede usuarioDaRede);

	public NoDaRedeDTO converterParaDTO(NoDaRede noDaRede);

	public Usuario converterParaEntidade(UsuarioDTO usuarioDto);

	public Servidor converterParaEntidade(ServidorDTO servidorDto);

	public UsuarioDaRede converterParaEntidade(UsuarioDaRedeDTO usuarioDaRedeDTO, NoDaRede servidor, Usuario usuario);

	public NoDaRede converterParaEntidade(NoDaRedeDTO noDaRedeDTO, Servidor servidor, Optional<NoDaRede> proximoNo);

	public NoDaRede converterParaEntidade(NoDaRedeDTO noDaRedeDTO);
	
	public UsuarioDaRede converterParaEntidade(UsuarioDaRedeDTO usuarioDaRedeDTO);

}
