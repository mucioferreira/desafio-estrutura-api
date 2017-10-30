package com.estrutura.desafio.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
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
import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;
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
		this.servidorService.save(this.dadosServidorUm());
		this.servidorService.save(this.dadosServidorDois());
		this.noDaRedeService.save(this.dadosNoDaRedeUm());
		this.noDaRedeService.save(this.dadosNoDaRedeDois());
	}
	
	@Test
	public void testBuscarPorId() throws Exception {
		Optional<NoDaRede> noDaRede = this.noDaRedeService.findById(1L);
		assertTrue(noDaRede.isPresent());		
	}

	@Test
	public void testBuscarPorIdDoServidor() throws Exception {
		Page<NoDaRede> noDaRede = this.noDaRedeService.findByServidorId(1L, new PageRequest(0, 10));
		assertNotNull(noDaRede);
	}
	
	@Test
	public void testBuscarPorIpServidor() throws Exception {
		Page<NoDaRede> noDaRede = this.noDaRedeService.findByServidorIp(IP_SERVIDOR, new PageRequest(0, 10));
		assertNotNull(noDaRede);
	}

	@After
	public void tearDown() throws Exception {
		this.noDaRedeService.deleteAll();
	}
	
	private Servidor dadosServidorUm() {
		Servidor servidor = new Servidor();
		servidor.setNome(NOME_SERVIDOR);
		servidor.setIp(IP_SERVIDOR);
		servidor.setTipoServidor(TipoServidorEnum.PROXY);
		return servidor;
	}
	
	private Servidor dadosServidorDois() {
		Servidor servidor = new Servidor();
		servidor.setNome(NOME_SERVIDOR);
		servidor.setIp(IP_SERVIDOR_2);
		servidor.setTipoServidor(TipoServidorEnum.PROXY);
		return servidor;
	}
	
	private NoDaRede dadosNoDaRedeUm() {
		NoDaRede noDaRede = new NoDaRede();
		noDaRede.setServidor(this.servidorService.findOne(1L));
		noDaRede.setDescricaoDaRede(DESCRICAO_REDE);
		noDaRede.setAmbienteDaRede(AmbienteDaRedeEnum.DESENVOLVIMENTO);
		return noDaRede;
	}
	
	private NoDaRede dadosNoDaRedeDois() {
		NoDaRede noDaRede = new NoDaRede();
		noDaRede.setServidor(this.servidorService.findOne(1L));
		noDaRede.setProximoNo(this.noDaRedeService.findOne(1L));
		noDaRede.setDescricaoDaRede(DESCRICAO_REDE);
		noDaRede.setAmbienteDaRede(AmbienteDaRedeEnum.DESENVOLVIMENTO);
		return noDaRede;
	}
}
