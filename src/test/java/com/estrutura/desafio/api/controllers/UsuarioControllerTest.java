package com.estrutura.desafio.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.services.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UsuarioService usuarioService;
	
	private static final String USUARIO_URL = "/api/usuario/";
	private static final Long ID = 1L;
	private static final String NOME = "Usuario Teste";
	private static final Usuario USUARIO = new Usuario(NOME);
	private static final Usuario USUARIO_COM_ID = new Usuario(ID, NOME);
	
	@Test
	public void testBuscarUsuario() throws Exception {
		BDDMockito.given(this.usuarioService.findById(ID))
			.willReturn(Optional.of(USUARIO_COM_ID));
		this.mvc.perform(MockMvcRequestBuilders.get(USUARIO_URL + ID)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME));
	}
	
	@Test
	public void testBuscarUsuarioInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.findById(ID)).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.get(USUARIO_URL + ID).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.USUARIO_NAO_ENCONTRADO)));
	}
	
//	@Test
//	public void testInserirUsuario() throws Exception {
//		BDDMockito.given(this.usuarioService.save(USUARIO)).willReturn(USUARIO_COM_ID);
//		this.mvc.perform(MockMvcRequestBuilders.post(USUARIO_URL).param("nome", NOME))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.data.id").value(ID))
//			.andExpect(jsonPath("$.data.nome").value(NOME));
//	}


}
