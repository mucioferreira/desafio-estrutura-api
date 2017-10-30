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

import com.estrutura.desafio.api.dtos.NoDaRedeDTO;
import com.estrutura.desafio.api.entities.NoDaRede;
import com.estrutura.desafio.api.entities.Servidor;
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
	public ResponseEntity<Response<NoDaRedeDTO>> cadastrarNoDaRede(@Valid @RequestBody NoDaRedeDTO noDaRedeDTO, BindingResult result) throws NoSuchAlgorithmException {
		Response<NoDaRedeDTO> response = new Response<NoDaRedeDTO>();
		Optional<Servidor> servidor = this.servidorService.findById(noDaRedeDTO.getServidor());
		Optional<NoDaRede> proximoNo = noDaRedeDTO.getProximoNoOpt().isPresent() ? this.noDaRedeService.findById(noDaRedeDTO.getProximoNoOpt().get()) : Optional.empty();

		this.validarDadosExistentes(noDaRedeDTO, servidor, proximoNo, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		
		NoDaRede noDaRede = this.noDaRedeService.save(this.converterParaNoDaRede(noDaRedeDTO, servidor.get(), proximoNo.get()));
		response.setData(this.converterParaDTO(noDaRede));
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Response<NoDaRedeDTO>> modificarNoDaRede(@Valid @RequestBody NoDaRedeDTO noDaRedeDTO, BindingResult result) throws NoSuchAlgorithmException {
		Response<NoDaRedeDTO> response = new Response<NoDaRedeDTO>();
		
		Optional<NoDaRede> noDaRede = noDaRedeDTO.getIdOpt().isPresent() ? this.noDaRedeService.findById(noDaRedeDTO.getProximoNoOpt().get()) : Optional.empty();
		Optional<Servidor> servidor = this.servidorService.findById(noDaRedeDTO.getServidor());
		Optional<NoDaRede> proximoNo = noDaRedeDTO.getProximoNoOpt().isPresent() ? this.noDaRedeService.findById(noDaRedeDTO.getProximoNoOpt().get()) : Optional.empty();

		if(!noDaRedeDTO.getIdOpt().isPresent()) result.addError(new ObjectError("usuarioDaRede", String.valueOf(MensagemEnum.NENHUM_NO_DA_REDE)));
		else if(!noDaRede.isPresent()) result.addError(new ObjectError("usuarioDaRede", String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO)));
		this.validarDadosExistentes(noDaRedeDTO, servidor, proximoNo, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
			
			
		NoDaRede n = this.noDaRedeService.save(this.converterParaNoDaRede(noDaRedeDTO, servidor.get(), proximoNo.get()));
		response.setData(this.converterParaDTO(n));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<NoDaRedeDTO>> procurarNoDaRedePeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<NoDaRedeDTO> response = new Response<NoDaRedeDTO>();
		Optional<NoDaRede> noDaRede = this.noDaRedeService.findById(id);
		
		if(!noDaRede.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		response.setData(this.converterParaDTO(noDaRede.get()));
		return ResponseEntity.ok(response);
	} 
	
	@GetMapping(value = "/servidor/{id}")
	public ResponseEntity<Response<Page<NoDaRedeDTO>>> procurarNoDaRedePeloIdDoServidor(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		Response<Page<NoDaRedeDTO>> response = new Response<Page<NoDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<NoDaRedeDTO> nosDaRedeDto = this.noDaRedeService.findByServidorId(id, pageRequest).map(noDaRede -> this.converterParaDTO(noDaRede));
		response.setData(nosDaRedeDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<Page<NoDaRedeDTO>>> todosNosoDaRede(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		Response<Page<NoDaRedeDTO>> response = new Response<Page<NoDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<NoDaRedeDTO> nosDaRedeDto = this.noDaRedeService.findAll(pageRequest).map(noDaRede -> this.converterParaDTO(noDaRede));
		response.setData(nosDaRedeDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/servidor/ip/{ip}")
	public ResponseEntity<Response<Page<NoDaRedeDTO>>> procurarNoDaRedePeloIpDoServidor(
			@PathVariable("ip") String ip,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		Response<Page<NoDaRedeDTO>> response = new Response<Page<NoDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<NoDaRedeDTO> nosDaRedeDto = this.noDaRedeService.findByServidorIp(ip, pageRequest).map(noDaRede -> this.converterParaDTO(noDaRede));
		response.setData(nosDaRedeDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<Response<NoDaRedeDTO>> deletarNoDaRede(@RequestBody NoDaRedeDTO noDaRedeDto) throws NoSuchAlgorithmException {
		Response<NoDaRedeDTO> response = new Response<NoDaRedeDTO>();
		
		if(!this.noDaRedeService.findById(noDaRedeDto.getId()).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		this.noDaRedeService.delete(this.converterParaNoDaRede(noDaRedeDto));
		return ResponseEntity.ok(response);
	} 
	
	private void validarDadosExistentes(NoDaRedeDTO noDaRedeDTO, Optional<Servidor> servidor, Optional<NoDaRede> proximoNo, BindingResult result) {
		if(!servidor.isPresent()) result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
		if(noDaRedeDTO.getProximoNoOpt().isPresent() && !proximoNo.isPresent()) result.addError(new ObjectError("proximoNo", String.valueOf(MensagemEnum.PROXIMO_NO_NAO_ENCONTRADO)));
	}

	
	private NoDaRede converterParaNoDaRede(NoDaRedeDTO noDaRedeDTO, Servidor servidor, NoDaRede proximoNo) {
		NoDaRede noDaRede = new NoDaRede();
		noDaRedeDTO.getIdOpt().ifPresent(id -> noDaRede.setId(id));
		noDaRede.setServidor(servidor);
		noDaRedeDTO.getProximoNoOpt().ifPresent(no -> noDaRede.setProximoNo(proximoNo));
		noDaRede.setAmbienteDaRede(noDaRedeDTO.getAmbienteDaRede());
		noDaRedeDTO.getDescricaoDaRedeOpt().ifPresent(desc -> noDaRede.setDescricaoDaRede(desc));
		return noDaRede;
	}
	
	private NoDaRede converterParaNoDaRede(NoDaRedeDTO noDaRedeDTO) {
		NoDaRede noDaRede = new NoDaRede();
		noDaRedeDTO.getIdOpt().ifPresent(id -> noDaRede.setId(id));
		return noDaRede;
	}

	private NoDaRedeDTO converterParaDTO(NoDaRede noDaRede) {
		NoDaRedeDTO noDaRedeDTO = new NoDaRedeDTO();
		noDaRede.getIdOpt().ifPresent(id -> noDaRedeDTO.setId(id));
		noDaRedeDTO.setServidor(noDaRede.getServidor().getId());
		noDaRede.getProximoNoOpt().ifPresent(proximo -> noDaRedeDTO.setProximoNo(proximo.getId()));
		noDaRede.getDescricaoDaRedeOpt().ifPresent(descricao -> noDaRedeDTO.setDescricaoDaRede(descricao));
		noDaRedeDTO.setAmbienteDaRede(noDaRede.getAmbienteDaRede());
		return noDaRedeDTO;
	}
	
}
