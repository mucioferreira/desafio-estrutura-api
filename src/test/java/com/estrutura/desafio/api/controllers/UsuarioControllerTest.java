package com.estrutura.desafio.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.estrutura.desafio.api.dtos.UsuarioDTO;
import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UsuarioService usuarioService;
	
	private static final String URL = "/api/usuario/";
	
	private static final Long ID = 1L;
	private static final String NOME = "Usuario";
	
	private static final Long SEGUNDO_ID = 2L;
	private static final String SEGUNDO_NOME = "Usuario 2";
	
	@Test
	public void testNovoUsuarioValido() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(Usuario.class))).willReturn(this.novoUsuario());
		BDDMockito.given(this.usuarioService.findByNome(Mockito.anyString())).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.post(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.usuarioJSON())
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME));
	}
	
	@Test
	public void testNovoUsuarioInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(Usuario.class))).willReturn(this.novoUsuario());
		BDDMockito.given(this.usuarioService.findByNome(Mockito.anyString())).willReturn(Optional.of(this.novoSegundoUsuario()));
		this.mvc.perform(MockMvcRequestBuilders.post(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.usuarioJSON())
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_JA_EXISTE)));
	}
	
	@Test
	public void testBuscaUsuarioPeloIdValido() throws Exception {
		BDDMockito.given(this.usuarioService.findById(Mockito.anyLong())).willReturn(Optional.of(this.novoUsuario()));
		this.mvc.perform(MockMvcRequestBuilders.get(URL + ID)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME));
	}
	
	@Test
	public void testBuscaUsuarioPeloIdInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.findById(Mockito.anyLong())).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.get(URL + ID)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
	}
	
	@Test
	public void testRemoverUsuarioInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.findById(Mockito.anyLong())).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.delete(URL)
			.content(this.usuarioJSON())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
	}
	
	@Test
	public void testRemoverUsuarioValido() throws Exception {
		BDDMockito.given(this.usuarioService.findById(Mockito.anyLong())).willReturn(Optional.of(this.novoUsuario()));
		this.mvc.perform(MockMvcRequestBuilders.delete(URL)
			.content(this.usuarioJSON())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	private String usuarioJSON() throws JsonProcessingException {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setId(ID);
		usuarioDto.setNome(NOME);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioDto);
	}
	
	private Usuario novoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		usuario.setNome(NOME);
		return usuario;
	}
	
	private Usuario novoSegundoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(SEGUNDO_ID);
		usuario.setNome(SEGUNDO_NOME);
		return usuario;
	}
}
