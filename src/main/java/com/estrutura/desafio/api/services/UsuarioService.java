package com.estrutura.desafio.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estrutura.desafio.api.entities.Usuario;

public interface UsuarioService extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findById(Long id);
	Optional<Usuario> findByNome(String nome);
	@Query("SELECT user FROM Usuario user WHERE user.nome LIKE CONCAT('%',:usuarioNome,'%')")
	List<Usuario> findByLikeNome(@Param("usuarioNome") String nome);
	
}
