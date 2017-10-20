package com.estrutura.desafio.api.services;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estrutura.desafio.api.entities.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;
	
	private static final String NOME = "Desenvolvedor PHP";
	
	@Before
	public void setUp() throws Exception {
		this.usuarioService.save(new Usuario(NOME));
	}
	
	@Test
	public void testProcurarPorID() {
		Optional<Usuario> usuario = this.usuarioService.findById(1L);
		assertTrue(usuario.isPresent());
	}
	
	@After
	public void tearDown() throws Exception {
		this.usuarioService.deleteAll();
	}
}
