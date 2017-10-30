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

import com.estrutura.desafio.api.dtos.ServidorDTO;
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
	public ResponseEntity<Response<ServidorDTO>> cadastrarServidor(@Valid @RequestBody ServidorDTO servidorDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<ServidorDTO> response = new Response<ServidorDTO>();
		
		this.validarDadosExistentes(servidorDto, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
	
		Servidor servidor = this.servidorService.save(this.converterParaServidor(servidorDto));
		response.setData(this.converterParaDTO(servidor));
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Response<ServidorDTO>> modificarServidor(@Valid @RequestBody ServidorDTO servidorDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<ServidorDTO> response = new Response<ServidorDTO>();
		Optional<Servidor> servidor = servidorDto.getIdOpt().isPresent() ? this.servidorService.findById(servidorDto.getId()) : Optional.empty();
		
		if(!servidorDto.getIdOpt().isPresent()) result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.NENHUM_ID_DO_SERVIDOR)));
		if(servidor.isPresent()) {
			if(!servidor.get().getIp().equals(servidorDto.getIp())) this.validarDadosExistentes(servidorDto, result);
		} else result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
		
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);

		Servidor s = this.servidorService.save(this.modificarServidor(servidor.get(), servidorDto));
		response.setData(this.converterParaDTO(s));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<Response<ServidorDTO>> deletarServidor(@RequestBody ServidorDTO servidorDto) throws NoSuchAlgorithmException {
		Response<ServidorDTO> response = new Response<ServidorDTO>();
		
		if(!this.servidorService.findById(servidorDto.getId()).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			this.servidorService.delete(this.converterParaServidor(servidorDto));
		} catch (Exception e) {
			response.getErrors().add(String.valueOf(MensagemEnum.SERVIDOR_NAO_PODE_SER_EXCLUIDO));
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<ServidorDTO>>> todosServidor(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
		@RequestParam(value = "direcao", defaultValue = "DESC") String direcao,
		@RequestParam(value = "ordem", defaultValue = "id") String ordem
	) {
		Response<Page<ServidorDTO>> response = new Response<Page<ServidorDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<ServidorDTO> servidoresDto = this.servidorService.findAll(pageRequest).map(servidor -> this.converterParaDTO(servidor));
		response.setData(servidoresDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ServidorDTO>> procurarServidorPeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		return this.verificarBuscaDoServidor(new Response<ServidorDTO>(), this.servidorService.findById(id));
	}
	
	@GetMapping(value = "/ip/{ip}")
	public ResponseEntity<Response<ServidorDTO>> procurarServidorPeloIp(@PathVariable("ip") String ip) throws NoSuchAlgorithmException {
		 return this.verificarBuscaDoServidor(new Response<ServidorDTO>(), this.servidorService.findByIp(ip));
	}
	
	private ResponseEntity<Response<ServidorDTO>> verificarBuscaDoServidor(Response<ServidorDTO> response, Optional<Servidor> servidor){
		if(!servidor.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterParaDTO(servidor.get()));
		return ResponseEntity.ok(response);
		
	}
	
	private void validarDadosExistentes(ServidorDTO servidorDto, BindingResult result) {
		this.servidorService.findByIp(servidorDto.getIp())
			.ifPresent(erro -> result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_JA_EXISTE))));
	}
	
	private Servidor converterParaServidor(ServidorDTO servidorDto) {
		Servidor servidor = new Servidor();
		servidorDto.getIdOpt().ifPresent(id -> servidor.setId(id));
		servidor.setNome(servidorDto.getNome());
		servidor.setIp(servidorDto.getIp());
		servidor.setTipoServidor(servidorDto.getTipoServidor());
		return servidor;
	}
	
	private ServidorDTO converterParaDTO(Servidor servidor) {
		ServidorDTO servidorDto = new ServidorDTO();
		servidorDto.setId(servidor.getId());
		servidorDto.setNome(servidor.getNome());
		servidorDto.setIp(servidor.getIp());
		servidorDto.setTipoServidor(servidor.getTipoServidor());
		return servidorDto;
	}
	
	private Servidor modificarServidor(Servidor servidor, ServidorDTO servidorDto) {
		servidor.setId(servidorDto.getId());
		servidor.setNome(servidorDto.getNome());
		servidor.setIp(servidorDto.getIp());
		servidor.setTipoServidor(servidorDto.getTipoServidor());
		return servidor;
	}
}
