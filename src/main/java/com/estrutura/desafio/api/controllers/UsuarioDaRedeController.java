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

import com.estrutura.desafio.api.dtos.UsuarioDaRedeDTO;
import com.estrutura.desafio.api.entities.NoDaRede;
import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.entities.UsuarioDaRede;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.interfaces.Converter;
import com.estrutura.desafio.api.response.Response;
import com.estrutura.desafio.api.services.NoDaRedeService;
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
	private NoDaRedeService noDaRedeService;
	
	@Autowired
	private Converter converter;
	
	@PostMapping
	public ResponseEntity<Response<UsuarioDaRedeDTO>> cadastrarUsuarioDaRede(@Valid @RequestBody UsuarioDaRedeDTO usuarioDaRedeDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		Optional<NoDaRede> noDaRede = this.noDaRedeService.findById(usuarioDaRedeDto.getNoDaRede().getId());
		Optional<Usuario> usuario = this.usuarioService.findById(usuarioDaRedeDto.getUsuario().getId());
		
		this.validarDadosExistentes(noDaRede, usuario, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		
		UsuarioDaRede usuarioDaRede = this.usuarioDaRedeService.save(this.converter.converterParaEntidade(usuarioDaRedeDto, noDaRede.get(), usuario.get()));
		response.setData(this.converter.converterParaDTO(usuarioDaRede));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<UsuarioDaRedeDTO>> modificarUsuarioDaRede(@Valid @RequestBody UsuarioDaRedeDTO usuarioDaRedeDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		
		Optional<UsuarioDaRede> usuarioDaRede = usuarioDaRedeDto.getIdOpt().isPresent() ? this.usuarioDaRedeService.findById(usuarioDaRedeDto.getId()) : Optional.empty();
		Optional<NoDaRede> noDaRede = this.noDaRedeService.findById(usuarioDaRedeDto.getNoDaRede().getId());
		Optional<Usuario> usuario = this.usuarioService.findById(usuarioDaRedeDto.getUsuario().getId());

		if(!usuarioDaRedeDto.getIdOpt().isPresent()) result.addError(new ObjectError("usuarioDaRede", String.valueOf(MensagemEnum.NENHUM_ID_DO_USUARIO_DA_REDE)));
		else if(!usuarioDaRede.isPresent()) result.addError(new ObjectError("usuarioDaRede", String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO)));
		this.validarDadosExistentes(noDaRede, usuario, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
			
		UsuarioDaRede u = this.usuarioDaRedeService.save(this.converter.converterParaEntidade(usuarioDaRedeDto, noDaRede.get(), usuario.get()));
		response.setData(this.converter.converterParaDTO(u));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDaRedeDTO>> procurarUsuarioDaRedePeloId(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		Optional<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findById(id);
		
		if(!usuarioDaRede.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		response.setData(this.converter.converterParaDTO(usuarioDaRede.get()));
		return ResponseEntity.ok(response);
	} 
	
	@GetMapping
	public ResponseEntity<Response<Page<UsuarioDaRedeDTO>>> todosUsuariosDaRede(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
		@RequestParam(value = "direcao", defaultValue = "DESC") String direcao,
		@RequestParam(value = "ordem", defaultValue = "id") String ordem
	) {
		Response<Page<UsuarioDaRedeDTO>> response = new Response<Page<UsuarioDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<UsuarioDaRedeDTO> usuariosDaRedeDTO = this.usuarioDaRedeService.findAll(pageRequest).map(usuario -> this.converter.converterParaDTO(usuario));
		response.setData(usuariosDaRedeDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "todos")
	public ResponseEntity<Response<List<UsuarioDaRedeDTO>>> buscarServidoresPeloAmbiente() throws NoSuchAlgorithmException {
		Response<List<UsuarioDaRedeDTO>> response = new Response<List<UsuarioDaRedeDTO>>();
		List<UsuarioDaRedeDTO> usuariosDaRedeDto = new ArrayList<UsuarioDaRedeDTO>();
		this.usuarioDaRedeService.findAll().forEach(usuarioDaRede -> usuariosDaRedeDto.add(this.converter.converterParaDTO(usuarioDaRede)));
		response.setData(usuariosDaRedeDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/usuario/{id}")
	public ResponseEntity<Response<Page<UsuarioDaRedeDTO>>> procurarUsuarioDaRedePeloIdDoUsuario(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		Response<Page<UsuarioDaRedeDTO>> response = new Response<Page<UsuarioDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<UsuarioDaRedeDTO> usuariosDaRedeDTO = this.usuarioDaRedeService.findByUsuarioId(id, pageRequest).map(usuarioDaRede -> this.converter.converterParaDTO(usuarioDaRede));
		response.setData(usuariosDaRedeDTO);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<Response<UsuarioDaRedeDTO>> deletarUsuarioDaRede(@RequestBody UsuarioDaRedeDTO usuarioDaRedeDto) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		
		if(!this.usuarioDaRedeService.findById(usuarioDaRedeDto.getId()).isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
			
		this.usuarioDaRedeService.delete(this.converter.converterParaEntidade(usuarioDaRedeDto));
		return ResponseEntity.ok(response);
	}
	
	private void validarDadosExistentes(Optional<NoDaRede> noDaRede, Optional<Usuario> usuario, BindingResult result) {
		if(!noDaRede.isPresent()) result.addError(new ObjectError("noDaRede", String.valueOf(MensagemEnum.NO_DA_REDE_NAO_ENCONTRADO)));
		if(!usuario.isPresent()) result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
	}

}
