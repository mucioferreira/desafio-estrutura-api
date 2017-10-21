package com.estrutura.desafio.api.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estrutura.desafio.api.entities.UsuarioDaRede;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "UsuarioService.findById", query = "SELECT rede FROM UsuarioDaRede rede WHERE rede.usuario.id = :usuarioId"),
	@NamedQuery(name = "ServidorService.findById", query = "SELECT rede FROM UsuarioDaRede rede WHERE rede.servidor.id = :servidorId"),
	@NamedQuery(name = "ServidorService.findByIp", query = "SELECT rede FROM UsuarioDaRede rede WHERE rede.servidor.ip = :servidorIp")
})
public interface UsuarioDaRedeService extends JpaRepository<UsuarioDaRede, Long> {

	Optional<UsuarioDaRede> findById(Long id);
	List<UsuarioDaRede> findByUsuarioId(@Param("usuarioId") Long id);
	List<UsuarioDaRede> findByServidorId(@Param("servidorId") Long id);
	List<UsuarioDaRede> findByServidorIp(@Param("servidorIp") String ip);
	
}
