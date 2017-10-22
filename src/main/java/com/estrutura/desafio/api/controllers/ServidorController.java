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
import com.estrutura.desafio.api.enums.MensagemEnum;
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
		
		if(!this.servidorService.findById(servidor.getId()).isPresent()) result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		else servidor = this.servidorService.save(servidor);
		
		response.setData(servidor);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<Response<Servidor>> deletarServidor(@RequestBody Servidor servidor) throws NoSuchAlgorithmException {
		Response<Servidor> response = new Response<Servidor>();
		
		if(!this.servidorService.findById(servidor.getId()).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		this.servidorService.delete(servidor);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<Servidor>>> todosServidor(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
		@RequestParam(value = "direcao", defaultValue = "DESC") String direcao,
		@RequestParam(value = "ordem", defaultValue = "id") String ordem
	) {
		Response<Page<Servidor>> response = new Response<Page<Servidor>>();
		response.setData(this.servidorService.findAll(new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Servidor>> procurarServidorPeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		return this.verificarBuscaDoServidor(new Response<Servidor>(), this.servidorService.findById(id));
	}
	
	@GetMapping(value = "/ip/{ip}")
	public ResponseEntity<Response<Servidor>> procurarServidorPeloIp(@PathVariable("ip") String ip) throws NoSuchAlgorithmException {
		 return this.verificarBuscaDoServidor(new Response<Servidor>(), this.servidorService.findByIp(ip));
	}
	
	private ResponseEntity<Response<Servidor>> verificarBuscaDoServidor(Response<Servidor> response, Optional<Servidor> servidor){
		if(!servidor.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(servidor.get());
		return ResponseEntity.ok(response);
		
	}
}
