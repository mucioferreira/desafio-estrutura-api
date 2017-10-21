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
	public ResponseEntity<Response<Usuario>> cadastrarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws NoSuchAlgorithmException {
		Response<Usuario> response = new Response<Usuario>();
		
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else usuario = this.usuarioService.save(usuario);
		
		response.setData(usuario);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<Usuario>> modificarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws NoSuchAlgorithmException {
		Response<Usuario> response = new Response<Usuario>();
		
		if(!this.buscarUsuario(usuario).isPresent()) result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
		
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else usuario = this.usuarioService.save(usuario);
		
		response.setData(usuario);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<Usuario>> deletarUsuario(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<Usuario> response = new Response<Usuario>();
		
		if(!this.buscarUsuario(id).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		this.usuarioService.delete(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<Usuario>>> todosUsuario(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
		@RequestParam(value = "direcao", defaultValue = "DESC") String direcao,
		@RequestParam(value = "ordem", defaultValue = "id") String ordem
	) {
		Response<Page<Usuario>> response = new Response<Page<Usuario>>();
		response.setData(this.usuarioService.findAll(new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Usuario>> procurarUsuarioPeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<Usuario> response = new Response<Usuario>();
		Optional<Usuario> usuario = this.buscarUsuario(id);
		
		if(!usuario.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		response.setData(usuario.get());
		return ResponseEntity.ok(response);
	}

	private Optional<Usuario> buscarUsuario(Usuario usuario) {
		return (usuario.getId() != null) ? this.usuarioService.findById(usuario.getId()) : Optional.empty();
	}
	
	private Optional<Usuario> buscarUsuario(Long id) {
		return (id != null) ? this.usuarioService.findById(id) : Optional.empty();
	}
}
