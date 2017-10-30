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

import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AmbienteControllerTest {

	@Autowired
	private MockMvc mvc;

	private static final String URL = "/api/ambiente-servidor";

	@Test
	public void testBuscarTodosAmbientes() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.Local").value(String.valueOf(AmbienteDaRedeEnum.LOCAL)))
			.andExpect(jsonPath("$.data.Homologação").value(String.valueOf(AmbienteDaRedeEnum.HOMOLOGACAO)))
			.andExpect(jsonPath("$.data.Produção").value(String.valueOf(AmbienteDaRedeEnum.PRODUCAO)))
			.andExpect(jsonPath("$.data.Nenhum").value(String.valueOf(AmbienteDaRedeEnum.NENHUM)))
			.andExpect(jsonPath("$.data.Desenvolvimento").value(String.valueOf(AmbienteDaRedeEnum.DESENVOLVIMENTO)));
	}

}
