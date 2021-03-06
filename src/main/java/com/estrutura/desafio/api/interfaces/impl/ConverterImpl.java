package com.estrutura.desafio.api.interfaces.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estrutura.desafio.api.dtos.NoDaRedeDTO;
import com.estrutura.desafio.api.dtos.ServidorDTO;
import com.estrutura.desafio.api.dtos.UsuarioDTO;
import com.estrutura.desafio.api.dtos.UsuarioDaRedeDTO;
import com.estrutura.desafio.api.entities.NoDaRede;
import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.entities.UsuarioDaRede;
import com.estrutura.desafio.api.interfaces.Converter;

@Component
@Transactional
public class ConverterImpl implements Converter {
	
	public UsuarioDTO converterParaDTO(Usuario usuario) {
		 UsuarioDTO usuarioDTO = new UsuarioDTO();
		 usuarioDTO.setId(usuario.getId());
		 usuarioDTO.setNome(usuario.getNome());
		return usuarioDTO;
	}
	
	public ServidorDTO converterParaDTO(Servidor servidor) {
		ServidorDTO servidorDto = new ServidorDTO();
		servidorDto.setId(servidor.getId());
		servidorDto.setNome(servidor.getNome());
		servidorDto.setIp(servidor.getIp());
		servidorDto.setTipoServidor(servidor.getTipoServidor());
		return servidorDto;
	}
	
	public UsuarioDaRedeDTO converterParaDTO(UsuarioDaRede usuarioDaRede) {
		UsuarioDaRedeDTO usuarioDaRedeDto = new UsuarioDaRedeDTO();
		usuarioDaRede.getIdOpt().ifPresent(id -> usuarioDaRedeDto.setId(id));
		usuarioDaRedeDto.setNoDaRede(this.converterParaDTO(usuarioDaRede.getNoDaRede()));
		usuarioDaRedeDto.setUsuario(this.converterParaDTO(usuarioDaRede.getUsuario()));
		usuarioDaRede.getDescricaoDaRedeOpt().ifPresent(descricao -> usuarioDaRedeDto.setDescricao(descricao));
		return usuarioDaRedeDto;
	}
	
	public NoDaRedeDTO converterParaDTO(NoDaRede noDaRede) {
		if(noDaRede == null) return null;
		NoDaRedeDTO noDaRedeDTO = new NoDaRedeDTO();
		noDaRedeDTO.setId(noDaRede.getId());
		noDaRedeDTO.setServidor(this.converterParaDTO(noDaRede.getServidor()));
		noDaRede.getProximoNoOpt().ifPresent(proximo -> noDaRedeDTO.setProximo(proximo.getId()));
		noDaRede.getDescricaoDaRedeOpt().ifPresent(descricao -> noDaRedeDTO.setDescricaoDaRede(descricao));
		noDaRedeDTO.setAmbienteDaRede(noDaRede.getAmbienteDaRede());
		return noDaRedeDTO;
	}
	
	public Usuario converterParaEntidade(UsuarioDTO usuarioDto) {
		Usuario usuario = new Usuario();
		usuarioDto.getIdOpt().ifPresent(id -> usuario.setId(id));
		usuario.setNome(usuarioDto.getNome());
		return usuario;
	}
	
	public Servidor converterParaEntidade(ServidorDTO servidorDto) {
		Servidor servidor = new Servidor();
		servidorDto.getIdOpt().ifPresent(id -> servidor.setId(id));
		servidor.setNome(servidorDto.getNome());
		servidor.setIp(servidorDto.getIp());
		servidor.setTipoServidor(servidorDto.getTipoServidor());
		return servidor;
	}
	
	public UsuarioDaRede converterParaEntidade(UsuarioDaRedeDTO usuarioDaRedeDTO, NoDaRede servidor, Usuario usuario) {
		UsuarioDaRede usuarioDaRede = new UsuarioDaRede();
		usuarioDaRedeDTO.getIdOpt().ifPresent(id -> usuarioDaRede.setId(id));
		usuarioDaRede.setNoDaRede(servidor);
		usuarioDaRede.setUsuario(usuario);
		usuarioDaRedeDTO.getDescricaoOpt().ifPresent(desc -> usuarioDaRede.setDescricaoDaRede(desc));
		return usuarioDaRede;
	}
	
	public NoDaRede converterParaEntidade(NoDaRedeDTO noDaRedeDTO, Servidor servidor, Optional<NoDaRede> proximoNo) {
		NoDaRede noDaRede = new NoDaRede();
		noDaRedeDTO.getIdOpt().ifPresent(id -> noDaRede.setId(id));
		noDaRede.setServidor(servidor);
		proximoNo.ifPresent(proximo -> noDaRede.setProximoNo(proximo));
		noDaRede.setAmbienteDaRede(noDaRedeDTO.getAmbienteDaRede());
		noDaRedeDTO.getDescricaoDaRedeOpt().ifPresent(desc -> noDaRede.setDescricaoDaRede(desc));
		return noDaRede;
	}

	public NoDaRede converterParaEntidade(NoDaRedeDTO noDaRedeDTO) {
		NoDaRede noDaRede = new NoDaRede();
		noDaRedeDTO.getIdOpt().ifPresent(id -> noDaRede.setId(id));
		return noDaRede;
	}
	
	public UsuarioDaRede converterParaEntidade(UsuarioDaRedeDTO usuarioDaRedeDTO) {
		UsuarioDaRede usuarioDaRede = new UsuarioDaRede();
		usuarioDaRedeDTO.getIdOpt().ifPresent(id -> usuarioDaRede.setId(id));
		return usuarioDaRede;
	}

}
