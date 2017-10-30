package com.estrutura.desafio.api.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estrutura.desafio.api.entities.Usuario;

public interface UsuarioService extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findById(Long id);
	Optional<Usuario> findByNome(String nome);
	
}
