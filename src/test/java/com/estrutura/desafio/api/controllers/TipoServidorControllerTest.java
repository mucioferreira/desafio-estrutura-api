package com.estrutura.desafio.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.estrutura.desafio.api.enums.TipoServidorEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TipoServidorControllerTest {

	@Autowired
	private MockMvc mvc;

	private static final String URL = "/api/tipo-servidor";

	@Test
	public void testBuscarTodosTiposDeServidor() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.['Banco de Dados']").value(String.valueOf(TipoServidorEnum.SERVIDOR_DE_BANCO)))
			.andExpect(jsonPath("$.data.Proxy").value(String.valueOf(TipoServidorEnum.PROXY)))
			.andExpect(jsonPath("$.data.Aplicação").value(String.valueOf(TipoServidorEnum.SERVIDOR_DE_APLICACAO)))
			.andExpect(jsonPath("$.data.Componente").value(String.valueOf(TipoServidorEnum.COMPONENTE)));
	}
	
}
