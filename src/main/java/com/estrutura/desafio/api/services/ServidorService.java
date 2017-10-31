package com.estrutura.desafio.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estrutura.desafio.api.entities.Servidor;

public interface ServidorService extends JpaRepository<Servidor, Long> {
	
	@Query("SELECT server FROM Servidor server WHERE server.ip LIKE CONCAT('%',:servidorIp,'%')")
	List<Servidor> findByLikeIp(@Param("servidorIp") String ip);
	Optional<Servidor> findByIp(String ip);
	Optional<Servidor> findById(Long id);
	
}
