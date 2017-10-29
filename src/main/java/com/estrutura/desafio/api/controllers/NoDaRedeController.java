package com.estrutura.desafio.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import com.estrutura.desafio.api.entities.NoDaRede;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.response.Response;
import com.estrutura.desafio.api.services.NoDaRedeService;
import com.estrutura.desafio.api.services.ServidorService;

@RestController
@RequestMapping("/api/no-da-rede")
@CrossOrigin(origins = "*")
public class NoDaRedeController {
	
	@Autowired
	private NoDaRedeService noDaRedeService;
	
	@Autowired
	private ServidorService servidorService;
	
	@PostMapping
	public ResponseEntity<Response<NoDaRede>> cadastrarNoDaRede(@Valid @RequestBody NoDaRede noDaRede, BindingResult result) throws NoSuchAlgorithmException {
		// TODO cadastrarNoDaRede
		return null;
//		Response<NoDaRede> response = new Response<NoDaRede>();
//		
//		if(!this.servidorService.findById(noDaRede.getServidor().getId()).isPresent()) result.addError(new ObjectError("primeiroServidor", String.valueOf(MensagemEnum.PRIMEIRO_SERVIDOR_NAO_ENCONTRADO)));
//		if(!this.noDaRedeService.findById(noDaRede.getProximoNo().getId()).isPresent()) result.addError(new ObjectError("segundoServidor", String.valueOf(MensagemEnum.SEGUNDO_SERVIDOR_NAO_ENCONTRADO)));
//		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
//		else noDaRede = this.noDaRedeService.save(noDaRede);
//		
//		response.setData(noDaRede);
//		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Response<NoDaRede>> modificarNoDaRede(@Valid @RequestBody NoDaRede noDaRede, BindingResult result) throws NoSuchAlgorithmException {
		// TODO modificarNoDaRede
		return null;
//		Response<NoDaRede> response = new Response<NoDaRede>();
//	
//		if(!this.noDaRedeService.findById(noDaRede.getId()).isPresent()) result.addError(new ObjectError("noDaRede", String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO)));
//		if(!this.servidorService.findById(noDaRede.getServidor().getId()).isPresent()) result.addError(new ObjectError("primeiroServidor", String.valueOf(MensagemEnum.PRIMEIRO_SERVIDOR_NAO_ENCONTRADO)));
//		if(!this.noDaRedeService.findById(noDaRede.getProximoNo().getId()).isPresent()) result.addError(new ObjectError("segundoServidor", String.valueOf(MensagemEnum.SEGUNDO_SERVIDOR_NAO_ENCONTRADO)));
//		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
//		else noDaRede = this.noDaRedeService.save(noDaRede);
//		
//		response.setData(noDaRede);
//		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<NoDaRede>> procurarNoDaRedePeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		// TODO procurarNoDaRedePeloId
		return null;
//		Response<NoDaRede> response = new Response<NoDaRede>();
//		Optional<NoDaRede> noDaRede = this.noDaRedeService.findById(id);
//		
//		if(!noDaRede.isPresent()) {
//			response.getErrors().add(String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO));
//			return ResponseEntity.badRequest().body(response);
//		}
//			
//		response.setData(noDaRede.get());
//		return ResponseEntity.ok(response);
	} 
	
	@GetMapping(value = "/servidor/{id}")
	public ResponseEntity<Response<Page<NoDaRede>>> procurarNoDaRedePeloIdDoServidor(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		// TODO procurarNoDaRedePeloIdDoServidor
		return null;
//		return this.verificarBuscaDoNoDaRede(new Response<Page<NoDaRede>>(), this.noDaRedeService.findByServidorId(id, new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
	}
	
	@GetMapping(value = "/servidor/ip/{ip}")
	public ResponseEntity<Response<Page<NoDaRede>>> procurarNoDaRedePeloIpDoServidor(
			@PathVariable("ip") String ip,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		// TODO procurarNoDaRedePeloIpDoServidor
		return null;
//		return this.verificarBuscaDoNoDaRede(new Response<Page<NoDaRede>>(), this.noDaRedeService.findByServidorIp(ip, new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem)));
	}
	
	@DeleteMapping
	public ResponseEntity<Response<NoDaRede>> deletarNoDaRede(@RequestBody NoDaRede noDaRede) throws NoSuchAlgorithmException {
		// TODO deletarNoDaRede
		return null;
//		Response<NoDaRede> response = new Response<NoDaRede>();
//		
//		if(!this.noDaRedeService.findById(noDaRede.getId()).isPresent()) {
//			response.getErrors().add(String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO));
//			return ResponseEntity.badRequest().body(response);
//		}
//			
//		this.noDaRedeService.delete(noDaRede);
//		return ResponseEntity.ok(response);
	}
	
	private ResponseEntity<Response<Page<NoDaRede>>> verificarBuscaDoNoDaRede(Response<Page<NoDaRede>> response, Page<NoDaRede> noDaRede) {
		if(noDaRede.getTotalElements() <= 0) {
			response.getErrors().add(String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(noDaRede);
		return ResponseEntity.ok(response);
	}
}
