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

import com.estrutura.desafio.api.entities.UsuarioDaRede;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.response.Response;
import com.estrutura.desafio.api.services.ServidorService;
import com.estrutura.desafio.api.services.UsuarioDaRedeService;
import com.estrutura.desafio.api.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario-da-rede")
@CrossOrigin(origins = "*")
public class UsuarioDaRedeController {
	
	@Autowired
	private UsuarioDaRedeService usuarioDaRedeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ServidorService servidorService;
	
	@PostMapping
	public ResponseEntity<Response<UsuarioDaRede>> cadastrarUsuarioDaRede(@Valid @RequestBody UsuarioDaRede usuarioDaRede, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDaRede> response = new Response<UsuarioDaRede>();
		
		if(!this.servidorService.findById(usuarioDaRede.getServidor().getId()).isPresent()) result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
		if(!this.usuarioService.findById(usuarioDaRede.getUsuario().getId()).isPresent()) result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else usuarioDaRede = this.usuarioDaRedeService.save(usuarioDaRede);
		
		response.setData(usuarioDaRede);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<UsuarioDaRede>> modificarUsuarioDaRede(@Valid @RequestBody UsuarioDaRede usuarioDaRede, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDaRede> response = new Response<UsuarioDaRede>();
	
		if(!this.usuarioDaRedeService.findById(usuarioDaRede.getId()).isPresent()) result.addError(new ObjectError("noDaRede", String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO)));
		if(!this.servidorService.findById(usuarioDaRede.getServidor().getId()).isPresent()) result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
		if(!this.usuarioService.findById(usuarioDaRede.getUsuario().getId()).isPresent()) result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else usuarioDaRede = this.usuarioDaRedeService.save(usuarioDaRede);
		
		response.setData(usuarioDaRede);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDaRede>> procurarUsuarioDaRedePeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<UsuarioDaRede> response = new Response<UsuarioDaRede>();
		Optional<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findById(id);
		
		if(!usuarioDaRede.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		response.setData(usuarioDaRede.get());
		return ResponseEntity.ok(response);
	} 
	
	@GetMapping(value = "/usuario/{id}")
	public ResponseEntity<Response<Page<UsuarioDaRede>>> procurarUsuarioDaRedePeloIdDoUsuario(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		return this.verificarBuscaDoUsuarioDaRede(new Response<Page<UsuarioDaRede>>(), this.usuarioDaRedeService.findByUsuarioId(id, new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
	}
	
	@GetMapping(value = "/servidor/{id}")
	public ResponseEntity<Response<Page<UsuarioDaRede>>> procurarUsuarioDaRedePeloIdDoServidor(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		return this.verificarBuscaDoUsuarioDaRede(new Response<Page<UsuarioDaRede>>(), this.usuarioDaRedeService.findByServidorId(id, new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
	}
	
	@GetMapping(value = "/servidor/ip/{ip}")
	public ResponseEntity<Response<Page<UsuarioDaRede>>> procurarUsuarioDaRedePeloIpDoServidor(
			@PathVariable("ip") String ip,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		return this.verificarBuscaDoUsuarioDaRede(new Response<Page<UsuarioDaRede>>(), this.usuarioDaRedeService.findByServidorIp(ip, new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
	}
	
	@DeleteMapping
	public ResponseEntity<Response<UsuarioDaRede>> deletarServidor(@RequestBody UsuarioDaRede usuarioDaRede) throws NoSuchAlgorithmException {
		Response<UsuarioDaRede> response = new Response<UsuarioDaRede>();
		
		if(!this.usuarioDaRedeService.findById(usuarioDaRede.getId()).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		this.usuarioDaRedeService.delete(usuarioDaRede);
		return ResponseEntity.ok(response);
	}
	
	private ResponseEntity<Response<Page<UsuarioDaRede>>> verificarBuscaDoUsuarioDaRede(Response<Page<UsuarioDaRede>> response, Page<UsuarioDaRede> usuarioDaRede) {
		if(usuarioDaRede.getTotalElements() <= 0) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(usuarioDaRede);
		return ResponseEntity.ok(response);
	}
	


}
