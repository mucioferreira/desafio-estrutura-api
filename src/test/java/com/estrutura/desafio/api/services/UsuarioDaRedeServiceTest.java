package com.estrutura.desafio.api.services;

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

import com.estrutura.desafio.api.entities.Servidor;
import com.estrutura.desafio.api.entities.Usuario;
import com.estrutura.desafio.api.entities.UsuarioDaRede;
import com.estrutura.desafio.api.enums.TipoServidorEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioDaRedeServiceTest {
	
	@Autowired
	private UsuarioDaRedeService usuarioDaRedeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ServidorService servidorService;
	
	// Servidor
	private static final String NOME_SERVIDOR = "mysql-desenv";
	private static final String IP = "172.16.0.107";
	private static final Servidor SERVIDOR = new Servidor(NOME_SERVIDOR, IP, TipoServidorEnum.SERVIDOR_DE_APLICACAO);
	
	
	// Usuario
	private static final String NOME_USUARIO = "Desenvolvedor PHP";
	private static final Usuario USUARIO = new Usuario(NOME_USUARIO);
	
	
	// Rede
	private static final String DESCRICAO_DA_REDE = "Uma descricao qualquer.";
	
	@Before
	public void setUp() throws Exception {
		Servidor servidor = this.servidorService.save(SERVIDOR);
		Usuario usuario = this.usuarioService.save(USUARIO);
		this.usuarioDaRedeService.save(new UsuarioDaRede(servidor, usuario, DESCRICAO_DA_REDE));
	}
	
	@Test
	public void testProcurarPorID() {
		Optional<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findById(1L);
		assertTrue(usuarioDaRede.isPresent());
	}
	
	@Test
	public void testProcurarPorIDDoUsuario() {
		Page<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findByUsuarioId(1L, new PageRequest(0, 10));
		assertTrue(usuarioDaRede.getNumberOfElements() > 0);
	}
	
	@Test
	public void testProcurarPorIDDoServidor() {
		Page<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findByServidorId(1L, new PageRequest(0, 10));
		assertTrue(usuarioDaRede.getNumberOfElements() > 0);
	}
	
	@Test
	public void testProcurarPorIPDoServidor() {
		Page<UsuarioDaRede> usuarioDaRede = this.usuarioDaRedeService.findByServidorIp(IP, new PageRequest(0, 10));
		assertTrue(usuarioDaRede.getNumberOfElements() > 0);
	}
	
	@After
	public void tearDown() throws Exception {
		this.usuarioDaRedeService.deleteAll();
	}

}
