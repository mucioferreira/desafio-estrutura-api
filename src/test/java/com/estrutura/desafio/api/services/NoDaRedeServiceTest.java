package com.estrutura.desafio.api.services;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estrutura.desafio.api.entities.NoDaRede;
import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.enums.TipoServidorEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NoDaRedeServiceTest {
	
	@Autowired
	private NoDaRedeService noDaRedeService;
	
	@Autowired
	private ServidorService servidorService;
	
	private static final String NOME_SERVIDOR = "Servidor Teste";
	private static final String IP_SERVIDOR = "123456789";
	private static final String IP_SERVIDOR_2 = "987654321";
	private static final String DESCRICAO_REDE = "Teste descricao";
	
	@Before
	public void setUp() throws Exception {
		Servidor primeiroServidor = this.servidorService.save(new Servidor(NOME_SERVIDOR, IP_SERVIDOR, TipoServidorEnum.PROXY));
		Servidor segundoServidor = this.servidorService.save(new Servidor(NOME_SERVIDOR, IP_SERVIDOR_2, TipoServidorEnum.SERVIDOR_DE_BANCO));
		this.noDaRedeService.save(new NoDaRede(primeiroServidor, segundoServidor, DESCRICAO_REDE));
	}
	
	@Test
	public void testBuscarPorId() throws Exception {
		Optional<NoDaRede> noDaRede = this.noDaRedeService.findById(1L);
		assertTrue(noDaRede.isPresent());		
	}

	@Test
	public void testBuscarPorIdDoPrimeiroServidor() throws Exception {
		Page<NoDaRede> noDaRede = this.noDaRedeService.findByPrimeiroServidorId(1L, new PageRequest(0, 10));
		assertTrue(noDaRede.getNumberOfElements() > 0);	
	}
	
	@Test
	public void testBuscarPorIpDoPrimeiroServidor() throws Exception {
		Page<NoDaRede> noDaRede = this.noDaRedeService.findByPrimeiroServidorIp(IP_SERVIDOR, new PageRequest(0, 10));
		assertTrue(noDaRede.getNumberOfElements() > 0);	
	}
	
	@Test
	public void testBuscarPorIpDoSegundoServidor() throws Exception {
		Page<NoDaRede> noDaRede = this.noDaRedeService.findBySegundoServidorIp(IP_SERVIDOR_2, new PageRequest(0, 10));
		assertTrue(noDaRede.getNumberOfElements() > 0);	
	}
	
	@Test
	public void testBuscarPorIdDoSegundoServidor() throws Exception {
		Page<NoDaRede> noDaRede = this.noDaRedeService.findBySegundoServidorId(2L, new PageRequest(0, 10));
		assertTrue(noDaRede.getNumberOfElements() > 0);	
	}

}
