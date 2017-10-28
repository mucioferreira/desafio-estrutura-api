package com.estrutura.desafio.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estrutura.desafio.api.dtos.UsuarioDTO;
import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.response.Response;
import com.estrutura.desafio.api.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		this.validarDadosExistentes(usuarioDto, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		
		Usuario usuario = this.usuarioService.save(this.converterParaUsuario(usuarioDto));
		
		response.setData(this.converterParaDTO(usuario));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<UsuarioDTO>> modificarUsuario(@Valid @RequestBody UsuarioDTO usuarioDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		if(!this.buscarUsuario(usuarioDto.getId()).isPresent()) result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
		this.validarDadosExistentes(usuarioDto, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		
		Usuario usuario = this.usuarioService.save(this.converterParaUsuario(usuarioDto));
		
		response.setData(this.converterParaDTO(usuario));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<Response<UsuarioDTO>> deletarUsuario(@RequestBody UsuarioDTO usuarioDto) throws NoSuchAlgorithmException {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		if(!this.buscarUsuario(usuarioDto.getId()).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		try {
			this.usuarioService.delete(this.converterParaUsuario(usuarioDto));
		} catch (Exception e) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_NAO_PODE_SER_EXCLUIDO));
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<UsuarioDTO>>> todosUsuario(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
		@RequestParam(value = "direcao", defaultValue = "DESC") String direcao,
		@RequestParam(value = "ordem", defaultValue = "id") String ordem
	) {
		Response<Page<UsuarioDTO>> response = new Response<Page<UsuarioDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<UsuarioDTO> usuariosDto = this.usuarioService.findAll(pageRequest).map(usuario -> this.converterParaDTO(usuario));
		response.setData(usuariosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDTO>> procurarUsuarioPeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		Optional<Usuario> usuario = this.buscarUsuario(id);
		
		if(!usuario.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		response.setData(this.converterParaDTO(usuario.get()));
		return ResponseEntity.ok(response);
	}
	
	private Optional<Usuario> buscarUsuario(Long id) {
		return (id != null) ? this.usuarioService.findById(id) : Optional.empty();
	}
	
	private Usuario converterParaUsuario(UsuarioDTO usuarioDto) {
		Usuario usuario = new Usuario(usuarioDto.getNome());
		usuarioDto.getIdOpt().ifPresent(id -> usuario.setId(id));
		return usuario;
	}
	
	private UsuarioDTO converterParaDTO(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getUsuarioDaRede());
	}
	
	private void validarDadosExistentes(UsuarioDTO usuarioDto, BindingResult result) {
		this.usuarioService.findByNome(usuarioDto.getNome())
			.ifPresent(erro -> result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_JA_EXISTE))));
	}
}
