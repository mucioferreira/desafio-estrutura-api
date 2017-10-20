package com.estrutura.desafio.api.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.estrutura.desafio.api.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioService extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findById(Long id);
	
}
