package com.estrutura.desafio.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
import com.estrutura.desafio.api.interfaces.Converter;
import com.estrutura.desafio.api.response.Response;
import com.estrutura.desafio.api.services.ServidorService;

@RestController
@RequestMapping("/api/servidor")
@CrossOrigin(origins = "*")
public class ServidorController {
	
	
	@Autowired
	private ServidorService servidorService;
	
	@Autowired
	private Converter converter;
	
	@PostMapping
	public ResponseEntity<Response<ServidorDTO>> cadastrarServidor(@Valid @RequestBody ServidorDTO servidorDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<ServidorDTO> response = new Response<ServidorDTO>();
		
		this.validarDadosExistentes(servidorDto, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
	
		Servidor servidor = this.servidorService.save(this.converter.converterParaEntidade(servidorDto));
		response.setData(this.converter.converterParaDTO(servidor));
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
		response.setData(this.converter.converterParaDTO(s));
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
			this.servidorService.delete(this.converter.converterParaEntidade(servidorDto));
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
		Page<ServidorDTO> servidoresDto = this.servidorService.findAll(pageRequest).map(servidor -> this.converter.converterParaDTO(servidor));
		response.setData(servidoresDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ServidorDTO>> procurarServidorPeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<ServidorDTO> response = new Response<ServidorDTO>();
		Optional<Servidor> servidor = this.servidorService.findById(id);
		
		if(!servidor.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converter.converterParaDTO(servidor.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(params = "ip")
	public ResponseEntity<Response<List<ServidorDTO>>> buscarServidoresPeloIp(@RequestParam("ip") String ip) throws NoSuchAlgorithmException {
		Response<List<ServidorDTO>> response = new Response<List<ServidorDTO>>();
		List<ServidorDTO> servidoresDto = new ArrayList<ServidorDTO>();
		if(ip != "") this.servidorService.findByLikeIp(ip).forEach(servidor -> servidoresDto.add(this.converter.converterParaDTO(servidor)));
		response.setData(servidoresDto);
		return ResponseEntity.ok(response);
	}
	
	private void validarDadosExistentes(ServidorDTO servidorDto, BindingResult result) {
		this.servidorService.findByIp(servidorDto.getIp())
			.ifPresent(erro -> result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_JA_EXISTE))));
	}
		
	private Servidor modificarServidor(Servidor servidor, ServidorDTO servidorDto) {
		servidor.setId(servidorDto.getId());
		servidor.setNome(servidorDto.getNome());
		servidor.setIp(servidorDto.getIp());
		servidor.setTipoServidor(servidorDto.getTipoServidor());
		return servidor;
	}
}
