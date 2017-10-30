package com.estrutura.desafio.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
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
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(Usuario.class))).willReturn(this.novoUsuario(ID, NOME));
		BDDMockito.given(this.usuarioService.findById(Mockito.anyLong())).willReturn(Optional.of(this.novoUsuario(ID, NOME)));
		BDDMockito.given(this.usuarioService.findByNome(Mockito.anyString())).willReturn(Optional.empty());
	}
	
	@Test
	public void testNovoUsuarioValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.usuarioJSON(ID, NOME))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME));
	}
	
	@Test
	public void testNovoUsuarioInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.findByNome(Mockito.anyString())).willReturn(Optional.of(this.novoUsuario(SEGUNDO_ID, SEGUNDO_NOME)));
		this.mvc.perform(MockMvcRequestBuilders.post(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.usuarioJSON(ID, NOME))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_JA_EXISTE)));
	}
	
	@Test
	public void testBuscaUsuarioPeloIdValido() throws Exception {
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
	public void testModificarUsuarioValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.put(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.usuarioJSON(ID, NOME))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME));
	}
	
	@Test
	public void testModificarUsuarioInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.findByNome(Mockito.anyString())).willReturn(Optional.of(this.novoUsuario(SEGUNDO_ID, SEGUNDO_NOME)));
		this.mvc.perform(MockMvcRequestBuilders.put(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.usuarioJSON(ID, SEGUNDO_NOME))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_JA_EXISTE)));
	}
	
	@Test
	public void testRemoverUsuarioInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.findById(Mockito.anyLong())).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.delete(URL)
			.content(this.usuarioJSON(ID, NOME))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
	}
	
	@Test
	public void testRemoverUsuarioValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.delete(URL)
			.content(this.usuarioJSON(ID, NOME))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	private String usuarioJSON(Long id, String nome) throws JsonProcessingException {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setId(id);
		usuarioDto.setNome(nome);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioDto);
	}
	
	private Usuario novoUsuario(Long id, String nome) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		return usuario;
	}

}
