package com.estrutura.desafio.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.enums.TipoServidorEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ServidorServiceTest {

	@Autowired
	private ServidorService servidorService;
	
	private static final String NOME = "mysql-desenv";
	private static final String IP = "172.16.0.107";
	
	@Before
	public void setUp() throws Exception {
		this.servidorService.save(this.dadosServidor());
	}
	
	@Test
	public void testProcurarPorID() {
		Optional<Servidor> servidor = this.servidorService.findById(1L);
		assertTrue(servidor.isPresent());
	}
	
	@Test
	public void testProcurarPorIP() {
		List<Servidor> servidor = this.servidorService.findByLikeIp(IP);
		assertNotNull(servidor);
	}
	
	private Servidor dadosServidor() {
		Servidor servidor = new Servidor();
		servidor.setNome(NOME);
		servidor.setIp(IP);
		servidor.setTipoServidor(TipoServidorEnum.SERVIDOR_DE_BANCO);
		return servidor;
	}
}
