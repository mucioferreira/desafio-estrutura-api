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

import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.response.Response;
import com.estrutura.desafio.api.services.ServidorService;

@RestController
@RequestMapping("/api/servidor")
@CrossOrigin(origins = "*")
public class ServidorController {
	
	
	@Autowired
	private ServidorService servidorService;
	
	@PostMapping
	public ResponseEntity<Response<Servidor>> cadastrarServidor(@Valid @RequestBody Servidor servidor, BindingResult result) throws NoSuchAlgorithmException {
		Response<Servidor> response = new Response<Servidor>();
		
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else servidor = this.servidorService.save(servidor);
		
		response.setData(servidor);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<Servidor>> modificarServidor(@Valid @RequestBody Servidor servidor, BindingResult result) throws NoSuchAlgorithmException {
		Response<Servidor> response = new Response<Servidor>();
		
		if(!this.buscarServidor(servidor).isPresent()) result.addError(new ObjectError("servidor", "Servidor não encontrado."));
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else servidor = this.servidorService.save(servidor);
		
		response.setData(servidor);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<Servidor>> deletarServidor(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<Servidor> response = new Response<Servidor>();
		
		if(!this.buscarServidor(id).isPresent()) {
			response.getErrors().add("Usuário não encontrado.");
			return ResponseEntity.badRequest().body(response);
		}
			
		this.servidorService.delete(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<Servidor>>> todosServidor(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "Qtdpagina", defaultValue = "10") int qtdPagina,
		@RequestParam(value = "direcao", defaultValue = "DESC") String direcao,
		@RequestParam(value = "ordem", defaultValue = "id") String ordem
	) {
		Response<Page<Servidor>> response = new Response<Page<Servidor>>();
		response.setData(this.servidorService.findAll(new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Servidor>> procurarServidorPeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<Servidor> response = new Response<Servidor>();
		Optional<Servidor> servidor = this.buscarServidor(id);
		
		if(!servidor.isPresent()) {
			response.getErrors().add("Servidor não encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(servidor.get());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/ip/{ip}")
	public ResponseEntity<Response<Servidor>> procurarServidorPeloIp(@PathVariable("ip") String ip) throws NoSuchAlgorithmException {
		Response<Servidor> response = new Response<Servidor>();
		Optional<Servidor> servidor = this.buscarServidor(ip);
		
		if(!servidor.isPresent()) {
			response.getErrors().add("Servidor não encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(servidor.get());
		return ResponseEntity.ok(response);
	}
	
	
	private Optional<Servidor> buscarServidor(Servidor servidor) {
		return (servidor.getId() != null) ? this.servidorService.findById(servidor.getId()) : Optional.empty();
	}
	
	private Optional<Servidor> buscarServidor(Long id) {
		return (id != null) ? this.servidorService.findById(id) : Optional.empty();
	}

	private Optional<Servidor> buscarServidor(String ip) {
		return (ip != null) ? this.servidorService.findByIp(ip) : Optional.empty();
	}
}
