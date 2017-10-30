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

import com.estrutura.desafio.api.dtos.ServidorDTO;
import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.enums.MensagemEnum;
import com.estrutura.desafio.api.enums.TipoServidorEnum;
import com.estrutura.desafio.api.services.ServidorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ServidorControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ServidorService servidorService;
	
	private static final String URL = "/api/servidor/";
	
	private static final Long ID = 1L;
	private static final String NOME = "Servidor";
	private static final String IP = "123.123.123.456";
	
	private static final Long SEGUNDO_ID = 2L;
	private static final String SEGUNDO_NOME = "Servidor 2";
	private static final String SEGUNDO_IP = "789.654.258.987";
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.servidorService.save(Mockito.any(Servidor.class))).willReturn(this.novoServidor(ID, NOME, IP));
		BDDMockito.given(this.servidorService.findById(Mockito.anyLong())).willReturn(Optional.of(this.novoServidor(ID, NOME, IP)));
		BDDMockito.given(this.servidorService.findByIp(Mockito.anyString())).willReturn(Optional.empty());
	}
	
	@Test
	public void testNovoServidorValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.servidorJSON(ID, NOME, IP))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.ip").value(IP))
				.andExpect(jsonPath("$.data.tipoServidor").value(String.valueOf(TipoServidorEnum.COMPONENTE)))
				.andExpect(jsonPath("$.data.nomeTipoServidor").value("Componente"));
	}

	@Test
	public void testNovoServidorInvalido() throws Exception {
		BDDMockito.given(this.servidorService.findByIp(Mockito.anyString())).willReturn(Optional.of(this.novoServidor(SEGUNDO_ID, SEGUNDO_NOME, SEGUNDO_IP)));
		this.mvc.perform(MockMvcRequestBuilders.post(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.servidorJSON(ID, NOME, SEGUNDO_IP))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.SERVIDOR_JA_EXISTE)));
	}
	
	@Test
	public void testBuscaServidorPeloIdValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(URL + ID)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.ip").value(IP))
				.andExpect(jsonPath("$.data.tipoServidor").value(String.valueOf(TipoServidorEnum.COMPONENTE)))
				.andExpect(jsonPath("$.data.nomeTipoServidor").value("Componente"));
	}
	
	@Test
	public void testBuscaServidorPeloIdInvalido() throws Exception {
		BDDMockito.given(this.servidorService.findById(Mockito.anyLong())).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.get(URL + ID)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
	}
	
	@Test
	public void testModificarServidorValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.put(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.servidorJSON(ID, NOME, IP))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nome").value(NOME))
				.andExpect(jsonPath("$.data.ip").value(IP))
				.andExpect(jsonPath("$.data.tipoServidor").value(String.valueOf(TipoServidorEnum.COMPONENTE)))
				.andExpect(jsonPath("$.data.nomeTipoServidor").value("Componente"));
	}
	
	@Test
	public void testModificarServidorInvalido() throws Exception {
		BDDMockito.given(this.servidorService.findByIp(Mockito.anyString())).willReturn(Optional.of(this.novoServidor(SEGUNDO_ID, SEGUNDO_NOME, SEGUNDO_IP)));
		this.mvc.perform(MockMvcRequestBuilders.put(URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.servidorJSON(ID, NOME, SEGUNDO_IP))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.SERVIDOR_JA_EXISTE)));
	}
	
	@Test
	public void testRemoverServidorInvalido() throws Exception {
		BDDMockito.given(this.servidorService.findById(Mockito.anyLong())).willReturn(Optional.empty());
		this.mvc.perform(MockMvcRequestBuilders.delete(URL)
			.content(this.servidorJSON(ID, NOME, SEGUNDO_IP))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(String.valueOf(MensagemEnum.SERVIDOR_NAO_ENCONTRADO)));
	}
	
	@Test
	public void testRemoverServidorValido() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.delete(URL)
			.content(this.servidorJSON(ID, NOME, IP))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private Servidor novoServidor(Long id, String nome, String ip) {
		Servidor servidor = new Servidor();
		servidor.setId(id);
		servidor.setNome(nome);
		servidor.setIp(ip);
		servidor.setTipoServidor(TipoServidorEnum.COMPONENTE);
		return servidor;
	}
	
	private String servidorJSON(Long id, String nome, String ip) throws JsonProcessingException {
		ServidorDTO servidorDto = new ServidorDTO();
		servidorDto.setId(id);
		servidorDto.setNome(nome);
		servidorDto.setIp(ip);
		servidorDto.setTipoServidor(TipoServidorEnum.COMPONENTE);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(servidorDto);
	}

}
