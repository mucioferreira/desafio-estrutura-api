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

import com.estrutura.desafio.api.dtos.UsuarioDaRedeDTO;
import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.entities.Usuario;
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
	public ResponseEntity<Response<UsuarioDaRedeDTO>> cadastrarUsuarioDaRede(@Valid @RequestBody UsuarioDaRedeDTO usuarioDaRedeDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		Optional<Servidor> servidor = this.servidorService.findById(usuarioDaRedeDto.getServidor());
		Optional<Usuario> usuario = this.usuarioService.findById(usuarioDaRedeDto.getUsuario());
		
		this.validarDadosExistentes(servidor, usuario, result);
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
		
		UsuarioDaRede usuarioDaRede = this.usuarioDaRedeService.save(this.converterParaUsuarioDaRede(usuarioDaRedeDto, servidor.get(), usuario.get()));
		response.setData(this.converterParaDTO(usuarioDaRede));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<UsuarioDaRedeDTO>> modificarUsuarioDaRede(@Valid @RequestBody UsuarioDaRedeDTO usuarioDaRedeDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		
		Optional<UsuarioDaRede> usuarioDaRede = Optional.empty();
		Optional<Servidor> servidor = this.servidorService.findById(usuarioDaRedeDto.getServidor());
		Optional<Usuario> usuario = this.usuarioService.findById(usuarioDaRedeDto.getUsuario());

		if(!usuarioDaRedeDto.getIdOpt().isPresent()) result.addError(new ObjectError("usuarioDaRede", String.valueOf(MensagemEnum.NENHUM_ID_DO_USUARIO_DA_REDE)));
		else usuarioDaRede = this.usuarioDaRedeService.findById(usuarioDaRedeDto.getId());
		if(usuarioDaRede.isPresent() ) {
			this.validarDadosExistentes(servidor, usuario, result);
		} else result.addError(new ObjectError("usuarioDaRede", String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO)));
		if(result.hasErrors()) return response.getResponseWithErrors(response, result);
			
			
		UsuarioDaRede u = this.usuarioDaRedeService.save(this.converterParaUsuarioDaRede(usuarioDaRedeDto, servidor.get(), usuario.get()));
		response.setData(this.converterParaDTO(u));
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
			
		response.setData(this.converterParaDTO(usuarioDaRede.get()));
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
		Page<UsuarioDaRedeDTO> usuariosDaRedeDTO = this.usuarioDaRedeService.findAll(pageRequest).map(usuario -> this.converterParaDTO(usuario));
		response.setData(usuariosDaRedeDTO);
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
		Page<UsuarioDaRedeDTO> usuariosDaRedeDTO = this.usuarioDaRedeService.findByUsuarioId(id, pageRequest).map(usuarioDaRede -> this.converterParaDTO(usuarioDaRede));
		response.setData(usuariosDaRedeDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/servidor/{id}")
	public ResponseEntity<Response<Page<UsuarioDaRedeDTO>>> procurarUsuarioDaRedePeloIdDoServidor(
			@PathVariable("id") Long id,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		Response<Page<UsuarioDaRedeDTO>> response = new Response<Page<UsuarioDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<UsuarioDaRedeDTO> usuariosDaRedeDTO = this.usuarioDaRedeService.findByServidorId(id, pageRequest).map(usuarioDaRede -> this.converterParaDTO(usuarioDaRede));
		response.setData(usuariosDaRedeDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/servidor/ip/{ip}")
	public ResponseEntity<Response<Page<UsuarioDaRedeDTO>>> procurarUsuarioDaRedePeloIpDoServidor(
			@PathVariable("ip") String ip,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina,
			@RequestParam(value = "qtdPagina", defaultValue = "10") int qtdPagina,
			@RequestParam(value = "ordem", defaultValue = "id") String ordem,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) throws NoSuchAlgorithmException {
		Response<Page<UsuarioDaRedeDTO>> response = new Response<Page<UsuarioDaRedeDTO>>();
		PageRequest pageRequest = new PageRequest(pagina, qtdPagina, Direction.valueOf(direcao), ordem);
		Page<UsuarioDaRedeDTO> usuariosDaRedeDTO = this.usuarioDaRedeService.findByServidorIp(ip, pageRequest).map(usuarioDaRede -> this.converterParaDTO(usuarioDaRede));
		response.setData(usuariosDaRedeDTO);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<Response<UsuarioDaRedeDTO>> deletarServidor(@RequestBody UsuarioDaRedeDTO usuarioDaRedeDto) throws NoSuchAlgorithmException {
		Response<UsuarioDaRedeDTO> response = new Response<UsuarioDaRedeDTO>();
		Optional<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findById(usuarioDaRedeDto.getId());
		
		if(!usuarioDaRede.isPresent()) {
			response.getErrors().add(String.valueOf(MensagemEnum.USUARIO_DA_REDE_NAO_ENCONTRADO));
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}

	private UsuarioDaRede converterParaUsuarioDaRede(UsuarioDaRedeDTO usuarioDaRedeDTO, Servidor servidor, Usuario usuario) {
		UsuarioDaRede usuarioDaRede = new UsuarioDaRede();
		usuarioDaRedeDTO.getIdOpt().ifPresent(id -> usuarioDaRede.setId(id));
		usuarioDaRede.setServidor(servidor);
		usuarioDaRede.setUsuario(usuario);
		usuarioDaRedeDTO.getDescricaoDaRedeOpt().ifPresent(desc -> usuarioDaRede.setDescricaoDaRede(desc));
		return usuarioDaRede;
	}

	private UsuarioDaRedeDTO converterParaDTO(UsuarioDaRede usuarioDaRede) {
		UsuarioDaRedeDTO usuarioDaRedeDto = new UsuarioDaRedeDTO();
		usuarioDaRede.getIdOpt().ifPresent(id -> usuarioDaRedeDto.setId(id));
		usuarioDaRedeDto.setServidor(usuarioDaRede.getServidor().getId());
		usuarioDaRedeDto.setUsuario(usuarioDaRede.getUsuario().getId());
		usuarioDaRede.getDescricaoDaRedeOpt().ifPresent(descricao -> usuarioDaRedeDto.setDescricaoDaRede(descricao));
		return usuarioDaRedeDto;
	}
	
	private void validarDadosExistentes(Optional<Servidor> servidor, Optional<Usuario> usuario, BindingResult result) {
		if(!servidor.isPresent()) result.addError(new ObjectError("servidor", String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
		if(!usuario.isPresent()) result.addError(new ObjectError("usuario", String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
	}

}
