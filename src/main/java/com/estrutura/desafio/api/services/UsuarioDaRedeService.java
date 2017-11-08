package com.estrutura.desafio.api.services;

import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.estrutura.desafio.api.entities.UsuarioDaRede;

@NamedQueries({
	@NamedQuery(name = "UsuarioService.findById", query = "SELECT rede FROM UsuarioDaRede rede WHERE rede.usuario.id = :usuarioId"),
})
public interface UsuarioDaRedeService extends JpaRepository<UsuarioDaRede, Long> {

	Optional<UsuarioDaRede> findById(Long id);
	Page<UsuarioDaRede> findByUsuarioId(@Param("usuarioId") Long id, Pageable page);
	
}
